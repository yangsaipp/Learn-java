/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lang.pathMatcher;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link PathMatcher} implementation for Ant-style path patterns.
 *
 * <p>Part of this mapping code has been kindly borrowed from <a href="http://ant.apache.org">Apache Ant</a>.
 *
 * <p>The mapping matches URLs using the following rules:<br>
 * <ul>
 * <li>{@code ?} matches one character</li>
 * <li>{@code *} matches zero or more characters</li>
 * <li>{@code **} matches zero or more <em>directories</em> in a path</li>
 * </ul>
 *
 * <h3>Examples</h3>
 * <ul>
 * <li>{@code com/t?st.jsp} &mdash; matches {@code com/test.jsp} but also
 * {@code com/tast.jsp} or {@code com/txst.jsp}</li>
 * <li>{@code com/*.jsp} &mdash; matches all {@code .jsp} files in the
 * {@code com} directory</li>
 * <li><code>com/&#42;&#42;/test.jsp</code> &mdash; matches all {@code test.jsp}
 * files underneath the {@code com} path</li>
 * <li><code>org/springframework/&#42;&#42;/*.jsp</code> &mdash; matches all
 * {@code .jsp} files underneath the {@code org/springframework} path</li>
 * <li><code>org/&#42;&#42;/servlet/bla.jsp</code> &mdash; matches
 * {@code org/springframework/servlet/bla.jsp} but also
 * {@code org/springframework/testing/servlet/bla.jsp} and {@code org/servlet/bla.jsp}</li>
 * to the {@code filename} variable</li>
 * </ul>
 *
 * <p><strong>Note:</strong> a pattern and a path must both be absolute or must
 * both be relative in order for the two to match. Therefore it is recommended
 * that users of this implementation to sanitize patterns in order to prefix
 * them with "/" as it makes sense in the context in which they're used.
 *
 * @author Alef Arendsen
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @author Sam Brannen
 * @since 16.07.2003
 */
public class AntPathMatcher implements PathMatcher {

	/** Default path separator: "/" */
	public static final String DEFAULT_PATH_SEPARATOR = "/";

	/** CACHE_TURNOFF_THRESHOLD */
	private static final int CACHE_TURNOFF_THRESHOLD = 65536;

	/** VARIABLE_PATTERN */
	private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{[^/]+?\\}");

	/** WILDCARD_CHARS*/
	private static final char[] WILDCARD_CHARS = { '*', '?', '{' };

	/** pathSeparator*/
	private String pathSeparator;

	/** pathSeparatorPatternCache*/
//	private PathSeparatorPatternCache pathSeparatorPatternCache;

	/** caseSensitive*/
	private boolean caseSensitive = true;

	/** trimTokens*/
	private boolean trimTokens = false;

	/** cachePatterns*/
	private volatile Boolean cachePatterns;

	/** tokenizedPatternCache */
	private final Map<String, String[]> tokenizedPatternCache = new ConcurrentHashMap<String, String[]>(256);

	/** stringMatcherCache */
	final Map<String, AntPathStringMatcher> stringMatcherCache = new ConcurrentHashMap<String, AntPathStringMatcher>(256);


	/**
	 * Create a new instance with the {@link #DEFAULT_PATH_SEPARATOR}.
	 */
	public AntPathMatcher() {
		this.pathSeparator = DEFAULT_PATH_SEPARATOR;
//		this.pathSeparatorPatternCache = new PathSeparatorPatternCache(DEFAULT_PATH_SEPARATOR);
	}

	/**
	 * A convenient, alternative constructor to use with a custom path separator.
	 * @param pathSeparator the path separator to use, must not be {@code null}.
	 * @since 4.1
	 */
	public AntPathMatcher(String pathSeparator) {
		this.pathSeparator = pathSeparator;
//		this.pathSeparatorPatternCache = new PathSeparatorPatternCache(pathSeparator);
	}


	/**
	 * Set the path separator to use for pattern parsing.
	 * <p>Default is "/", as in Ant.
	 * @param pathSeparator pathSeparator
	 */
	public void setPathSeparator(String pathSeparator) {
		this.pathSeparator = (pathSeparator != null ? pathSeparator : DEFAULT_PATH_SEPARATOR);
//		this.pathSeparatorPatternCache = new PathSeparatorPatternCache(this.pathSeparator);
	}

	/**
	 * Specify whether to perform pattern matching in a case-sensitive fashion.
	 * <p>Default is {@code true}. Switch this to {@code false} for case-insensitive matching.
	 * @param caseSensitive caseSensitive
	 * @since 4.2
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * Specify whether to trim tokenized paths and patterns.
	 * <p>Default is {@code false}.
	 * @param trimTokens trimTokens
	 */
	public void setTrimTokens(boolean trimTokens) {
		this.trimTokens = trimTokens;
	}

	/**
	 * Specify whether to cache parsed pattern metadata for patterns passed
	 * into this matcher's {@link #match} method. A value of {@code true}
	 * activates an unlimited pattern cache; a value of {@code false} turns
	 * the pattern cache off completely.
	 * <p>Default is for the cache to be on, but with the variant to automatically
	 * turn it off when encountering too many patterns to cache at runtime
	 * (the threshold is 65536), assuming that arbitrary permutations of patterns
	 * are coming in, with little chance for encountering a recurring pattern.
	 * @param cachePatterns cachePatterns
	 * @since 4.0.1
	 * @see #getStringMatcher(String)
	 */
	public void setCachePatterns(boolean cachePatterns) {
		this.cachePatterns = cachePatterns;
	}

	/**
	 * deactivatePatternCache 
	 */
	private void deactivatePatternCache() {
		this.cachePatterns = false;
		this.tokenizedPatternCache.clear();
		this.stringMatcherCache.clear();
	}


	@Override
	public boolean isPattern(String path) {
		return (path.indexOf('*') != -1 || path.indexOf('?') != -1);
	}

	@Override
	public boolean match(String pattern, String path) {
		return doMatch(pattern, path, true, null);
	}

	@Override
	public boolean matchStart(String pattern, String path) {
		return doMatch(pattern, path, false, null);
	}

	/**
	 * Actually match the given {@code path} against the given {@code pattern}.
	 * @param pattern the pattern to match against
	 * @param path the path String to test
	 * @param fullMatch whether a full pattern match is required (else a pattern match
	 * as far as the given base path goes is sufficient)
	 * @param uriTemplateVariables uriTemplateVariables
	 * @return {@code true} if the supplied {@code path} matched, {@code false} if it didn't
	 */
	protected boolean doMatch(String pattern, String path, boolean fullMatch, Map<String, String> uriTemplateVariables) {
		if (path.startsWith(this.pathSeparator) != pattern.startsWith(this.pathSeparator)) {
			return false;
		}

		String[] pattDirs = tokenizePattern(pattern);
		if (fullMatch && this.caseSensitive && !isPotentialMatch(path, pattDirs)) {
			return false;
		}

		String[] pathDirs = tokenizePath(path);

		int pattIdxStart = 0;
		int pattIdxEnd = pattDirs.length - 1;
		int pathIdxStart = 0;
		int pathIdxEnd = pathDirs.length - 1;

		// Match all elements up to the first **
		while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
			String pattDir = pattDirs[pattIdxStart];
			if ("**".equals(pattDir)) {
				break;
			}
			if (!matchStrings(pattDir, pathDirs[pathIdxStart], uriTemplateVariables)) {
				return false;
			}
			pattIdxStart++;
			pathIdxStart++;
		}

		if (pathIdxStart > pathIdxEnd) {
			// Path is exhausted, only match if rest of pattern is * or **'s
			if (pattIdxStart > pattIdxEnd) {
				return (pattern.endsWith(this.pathSeparator) ? path.endsWith(this.pathSeparator) :
						!path.endsWith(this.pathSeparator));
			}
			if (!fullMatch) {
				return true;
			}
			if (pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart].equals("*") && path.endsWith(this.pathSeparator)) {
				return true;
			}
			for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
				if (!pattDirs[i].equals("**")) {
					return false;
				}
			}
			return true;
		}
		else if (pattIdxStart > pattIdxEnd) {
			// String not exhausted, but pattern is. Failure.
			return false;
		}
		else if (!fullMatch && "**".equals(pattDirs[pattIdxStart])) {
			// Path start definitely matches due to "**" part in pattern.
			return true;
		}

		// up to last '**'
		while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
			String pattDir = pattDirs[pattIdxEnd];
			if (pattDir.equals("**")) {
				break;
			}
			if (!matchStrings(pattDir, pathDirs[pathIdxEnd], uriTemplateVariables)) {
				return false;
			}
			pattIdxEnd--;
			pathIdxEnd--;
		}
		if (pathIdxStart > pathIdxEnd) {
			// String is exhausted
			for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
				if (!pattDirs[i].equals("**")) {
					return false;
				}
			}
			return true;
		}

		while (pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
			int patIdxTmp = -1;
			for (int i = pattIdxStart + 1; i <= pattIdxEnd; i++) {
				if (pattDirs[i].equals("**")) {
					patIdxTmp = i;
					break;
				}
			}
			if (patIdxTmp == pattIdxStart + 1) {
				// '**/**' situation, so skip one
				pattIdxStart++;
				continue;
			}
			// Find the pattern between padIdxStart & padIdxTmp in str between
			// strIdxStart & strIdxEnd
			int patLength = (patIdxTmp - pattIdxStart - 1);
			int strLength = (pathIdxEnd - pathIdxStart + 1);
			int foundIdx = -1;

			strLoop:
			for (int i = 0; i <= strLength - patLength; i++) {
				for (int j = 0; j < patLength; j++) {
					String subPat = pattDirs[pattIdxStart + j + 1];
					String subStr = pathDirs[pathIdxStart + i + j];
					if (!matchStrings(subPat, subStr, uriTemplateVariables)) {
						continue strLoop;
					}
				}
				foundIdx = pathIdxStart + i;
				break;
			}

			if (foundIdx == -1) {
				return false;
			}

			pattIdxStart = patIdxTmp;
			pathIdxStart = foundIdx + patLength;
		}

		for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
			if (!pattDirs[i].equals("**")) {
				return false;
			}
		}

		return true;
	}

	/**
	 * isPotentialMatch 
	 * @param path path
	 * @param pattDirs pattDirs
	 * @return boolean
	 */
	private boolean isPotentialMatch(String path, String[] pattDirs) {
		if (!this.trimTokens) {
			char[] pathChars = path.toCharArray();
			int pos = 0;
			for (String pattDir : pattDirs) {
				int skipped = skipSeparator(path, pos, this.pathSeparator);
				pos += skipped;
				skipped = skipSegment(pathChars, pos, pattDir);
				if (skipped < pattDir.length()) {
					if (skipped > 0) {
						return true;
					}
					return (pattDir.length() > 0) && isWildcardChar(pattDir.charAt(0));
				}
				pos += skipped;
			}
		}
		return true;
	}

	/**
	 * @param chars chars
	 * @param pos pos
	 * @param prefix prefix
	 * @return int
	 */
	private int skipSegment(char[] chars, int pos, String prefix) {
		int skipped = 0;
		for (char c : prefix.toCharArray()) {
			if (isWildcardChar(c)) {
				return skipped;
			}
			else if (pos + skipped >= chars.length) {
				return 0;
			}
			else if (chars[pos + skipped] == c) {
				skipped++;
			}
		}
		return skipped;
	}

	/**
	 * @param path path
	 * @param pos pos
	 * @param separator separator
	 * @return int
	 */
	private int skipSeparator(String path, int pos, String separator) {
		int skipped = 0;
		while (path.startsWith(separator, pos + skipped)) {
			skipped += separator.length();
		}
		return skipped;
	}

	/**
	 * isWildcardChar 
	 * @param c c
	 * @return boolean
	 */
	private boolean isWildcardChar(char c) {
		for (char candidate : WILDCARD_CHARS) {
			if (c == candidate) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tokenize the given path pattern into parts, based on this matcher's settings.
	 * <p>Performs caching based on {@link #setCachePatterns}, delegating to
	 * {@link #tokenizePath(String)} for the actual tokenization algorithm.
	 * @param pattern the pattern to tokenize
	 * @return the tokenized pattern parts
	 */
	protected String[] tokenizePattern(String pattern) {
		String[] tokenized = null;
		Boolean bCachePatterns = this.cachePatterns;
		if (bCachePatterns == null || bCachePatterns.booleanValue()) {
			tokenized = this.tokenizedPatternCache.get(pattern);
		}
		if (tokenized == null) {
			tokenized = tokenizePath(pattern);
			if (bCachePatterns == null && this.tokenizedPatternCache.size() >= CACHE_TURNOFF_THRESHOLD) {
				// Try to adapt to the runtime situation that we're encountering:
				// There are obviously too many different patterns coming in here...
				// So let's turn off the cache since the patterns are unlikely to be reoccurring.
				deactivatePatternCache();
				return tokenized;
			}
			if (bCachePatterns == null || bCachePatterns.booleanValue()) {
				this.tokenizedPatternCache.put(pattern, tokenized);
			}
		}
		return tokenized;
	}

	/**
	 * Tokenize the given path String into parts, based on this matcher's settings.
	 * @param path the path to tokenize
	 * @return the tokenized path parts
	 */
	protected String[] tokenizePath(String path) {
		return StringUtils.tokenizeToStringArray(path, this.pathSeparator, this.trimTokens, true);
	}

	/**
	 * Test whether or not a string matches against a pattern.
	 * @param pattern the pattern to match against (never {@code null})
	 * @param str the String which must be matched against the pattern (never {@code null})
	 * @param uriTemplateVariables uriTemplateVariables
	 * @return {@code true} if the string matches against the pattern, or {@code false} otherwise
	 */
	private boolean matchStrings(String pattern, String str, Map<String, String> uriTemplateVariables) {
		return getStringMatcher(pattern).matchStrings(str, uriTemplateVariables);
	}

	/**
	 * Build or retrieve an {@link AntPathStringMatcher} for the given pattern.
	 * <p>The default implementation checks this AntPathMatcher's internal cache
	 * (see {@link #setCachePatterns}), creating a new AntPathStringMatcher instance
	 * if no cached copy is found.
	 * <p>When encountering too many patterns to cache at runtime (the threshold is 65536),
	 * it turns the default cache off, assuming that arbitrary permutations of patterns
	 * are coming in, with little chance for encountering a recurring pattern.
	 * <p>This method may be overridden to implement a custom cache strategy.
	 * @param pattern the pattern to match against (never {@code null})
	 * @return a corresponding AntPathStringMatcher (never {@code null})
	 * @see #setCachePatterns
	 */
	protected AntPathStringMatcher getStringMatcher(String pattern) {
		AntPathStringMatcher matcher = null;
		Boolean bCachePatterns = this.cachePatterns;
		if (bCachePatterns == null || bCachePatterns.booleanValue()) {
			matcher = this.stringMatcherCache.get(pattern);
		}
		if (matcher == null) {
			matcher = new AntPathStringMatcher(pattern, this.caseSensitive);
			if (bCachePatterns == null && this.stringMatcherCache.size() >= CACHE_TURNOFF_THRESHOLD) {
				// Try to adapt to the runtime situation that we're encountering:
				// There are obviously too many different patterns coming in here...
				// So let's turn off the cache since the patterns are unlikely to be reoccurring.
				deactivatePatternCache();
				return matcher;
			}
			if (bCachePatterns == null || bCachePatterns.booleanValue()) {
				this.stringMatcherCache.put(pattern, matcher);
			}
		}
		return matcher;
	}

	/**
	 * Given a pattern and a full path, determine the pattern-mapped part. <p>For example: <ul>
	 * <li>'{@code /docs/cvs/commit.html}' and '{@code /docs/cvs/commit.html} -> ''</li>
	 * <li>'{@code /docs/*}' and '{@code /docs/cvs/commit} -> '{@code cvs/commit}'</li>
	 * <li>'{@code /docs/cvs/*.html}' and '{@code /docs/cvs/commit.html} -> '{@code commit.html}'</li>
	 * <li>'{@code /docs/**}' and '{@code /docs/cvs/commit} -> '{@code cvs/commit}'</li>
	 * <li>'{@code /docs/**\/*.html}' and '{@code /docs/cvs/commit.html} -> '{@code cvs/commit.html}'</li>
	 * <li>'{@code /*.html}' and '{@code /docs/cvs/commit.html} -> '{@code docs/cvs/commit.html}'</li>
	 * <li>'{@code *.html}' and '{@code /docs/cvs/commit.html} -> '{@code /docs/cvs/commit.html}'</li>
	 * <li>'{@code *}' and '{@code /docs/cvs/commit.html} -> '{@code /docs/cvs/commit.html}'</li> </ul>
	 * <p>Assumes that {@link #match} returns {@code true} for '{@code pattern}' and '{@code path}', but
	 * does <strong>not</strong> enforce this.
	 */
	@Override
	public String extractPathWithinPattern(String pattern, String path) {
		String[] patternParts = StringUtils.tokenizeToStringArray(pattern, this.pathSeparator, this.trimTokens, true);
		String[] pathParts = StringUtils.tokenizeToStringArray(path, this.pathSeparator, this.trimTokens, true);
		StringBuilder builder = new StringBuilder();
		boolean pathStarted = false;

		for (int segment = 0; segment < patternParts.length; segment++) {
			String patternPart = patternParts[segment];
			if (patternPart.indexOf('*') > -1 || patternPart.indexOf('?') > -1) {
				for (; segment < pathParts.length; segment++) {
					if (pathStarted || (segment == 0 && !pattern.startsWith(this.pathSeparator))) {
						builder.append(this.pathSeparator);
					}
					builder.append(pathParts[segment]);
					pathStarted = true;
				}
			}
		}

		return builder.toString();
	}

	@Override
	public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
		Map<String, String> variables = new LinkedHashMap<String, String>();
		boolean result = doMatch(pattern, path, true, variables);
		if (!result) {
			throw new IllegalStateException("Pattern \"" + pattern + "\" is not a match for \"" + path + "\"");
		}
		return variables;
	}

	/**
	 * Given a full path, returns a {@link Comparator} suitable for sorting patterns in order of
	 * explicitness.
	 * <p>This{@code Comparator} will {@linkplain java.util.Collections#sort(List, Comparator) sort}
	 * a list so that more specific patterns (without uri templates or wild cards) come before
	 * generic patterns. So given a list with the following patterns:
	 * <ol>
	 * <li>{@code /hotels/new}</li>
	 * </ol>
	 * the returned comparator will sort this list so that the order will be as indicated.
	 * <p>The full path given as parameter is used to test for exact matches. So when the given path
	 * is {@code /hotels/2}, the pattern {@code /hotels/2} will be sorted before {@code /hotels/1}.
	 * @param path the full path to use for comparison
	 * @return a comparator capable of sorting patterns in order of explicitness
	 */
	@Override
	public Comparator<String> getPatternComparator(String path) {
		return new AntPatternComparator(path);
	}


	/**
	 * Tests whether or not a string matches against a pattern via a {@link Pattern}.
	 * <p>The pattern may contain special characters: '*' means zero or more characters; '?' means one and
	 * only one character; '{' and '}' indicate a URI template pattern. For example <tt>/users/{user}</tt>.
	 */
	protected static class AntPathStringMatcher {

		/** GLOB_PATTERN */
		private static final Pattern GLOB_PATTERN = Pattern.compile("\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}");

		/** DEFAULT_VARIABLE_PATTERN */
		private static final String DEFAULT_VARIABLE_PATTERN = "(.*)";

		/** pattern*/
		private final Pattern pattern;

		/** variableNames*/
		private final List<String> variableNames = new LinkedList<String>();

		/**
		 * 构造函数
		 * @param pattern pattern
		 */
		public AntPathStringMatcher(String pattern) {
			this(pattern, true);
		}

		/**
		 * 
		 * 构造函数
		 * @param pattern pattern
		 * @param caseSensitive caseSensitive
		 */
		public AntPathStringMatcher(String pattern, boolean caseSensitive) {
			StringBuilder patternBuilder = new StringBuilder();
			Matcher matcher = GLOB_PATTERN.matcher(pattern);
			int end = 0;
			while (matcher.find()) {
				patternBuilder.append(quote(pattern, end, matcher.start()));
				String match = matcher.group();
				if ("?".equals(match)) {
					patternBuilder.append('.');
				}
				else if ("*".equals(match)) {
					patternBuilder.append(".*");
				}
				else if (match.startsWith("{") && match.endsWith("}")) {
					int colonIdx = match.indexOf(':');
					if (colonIdx == -1) {
						patternBuilder.append(DEFAULT_VARIABLE_PATTERN);
						this.variableNames.add(matcher.group(1));
					}
					else {
						String variablePattern = match.substring(colonIdx + 1, match.length() - 1);
						patternBuilder.append('(');
						patternBuilder.append(variablePattern);
						patternBuilder.append(')');
						String variableName = match.substring(1, colonIdx);
						this.variableNames.add(variableName);
					}
				}
				end = matcher.end();
			}
			patternBuilder.append(quote(pattern, end, pattern.length()));
			this.pattern = (caseSensitive ? Pattern.compile(patternBuilder.toString()) :
					Pattern.compile(patternBuilder.toString(), Pattern.CASE_INSENSITIVE));
		}

		/**
		 * quote
		 * @param s s
		 * @param start start
		 * @param end end
		 * @return String
		 */
		private String quote(String s, int start, int end) {
			if (start == end) {
				return "";
			}
			return Pattern.quote(s.substring(start, end));
		}

		/**
		 * Main entry point.
		 * @param str  str
		 * @param uriTemplateVariables uriTemplateVariables
		 * @return {@code true} if the string matches against the pattern, or {@code false} otherwise.
		 */
		public boolean matchStrings(String str, Map<String, String> uriTemplateVariables) {
			Matcher matcher = this.pattern.matcher(str);
			if (matcher.matches()) {
				if (uriTemplateVariables != null) {
					// SPR-8455
					if (this.variableNames.size() != matcher.groupCount()) {
						throw new IllegalArgumentException("The number of capturing groups in the pattern segment " +
								this.pattern + " does not match the number of URI template variables it defines, " +
								"which can occur if capturing groups are used in a URI template regex. " +
								"Use non-capturing groups instead.");
					}
					for (int i = 1; i <= matcher.groupCount(); i++) {
						String name = this.variableNames.get(i - 1);
						String value = matcher.group(i);
						uriTemplateVariables.put(name, value);
					}
				}
				return true;
			}
			return false;
		}
	}


	/**
	 * The default {@link Comparator} implementation returned by
	 * {@link #getPatternComparator(String)}.
	 * <p>In order, the most "generic" pattern is determined by the following:
	 * <ul>
	 * <li>if it's null or a capture all pattern (i.e. it is equal to "/**")</li>
	 * <li>if the other pattern is an actual match</li>
	 * <li>if it's a catch-all pattern (i.e. it ends with "**"</li>
	 * <li>if it's got more "*" than the other pattern</li>
	 * <li>if it's got more "{foo}" than the other pattern</li>
	 * <li>if it's shorter than the other pattern</li>
	 * </ul>
	 */
	protected static class AntPatternComparator implements Comparator<String> {

		/** path*/
		private final String path;

		/**
		 * 
		 * 构造函数
		 * @param path path
		 */
		public AntPatternComparator(String path) {
			this.path = path;
		}

		/**
		 * Compare two patterns to determine which should match first, i.e. which
		 * is the most specific regarding the current path.
		 * @return a negative integer, zero, or a positive integer as pattern1 is
		 * more specific, equally specific, or less specific than pattern2.
		 */
		@Override
		public int compare(String pattern1, String pattern2) {
			PatternInfo info1 = new PatternInfo(pattern1);
			PatternInfo info2 = new PatternInfo(pattern2);

			if (info1.isLeastSpecific() && info2.isLeastSpecific()) {
				return 0;
			}
			else if (info1.isLeastSpecific()) {
				return 1;
			}
			else if (info2.isLeastSpecific()) {
				return -1;
			}

			boolean pattern1EqualsPath = pattern1.equals(path);
			boolean pattern2EqualsPath = pattern2.equals(path);
			if (pattern1EqualsPath && pattern2EqualsPath) {
				return 0;
			}
			else if (pattern1EqualsPath) {
				return -1;
			}
			else if (pattern2EqualsPath) {
				return 1;
			}

			if (info1.isPrefixPattern() && info2.getDoubleWildcards() == 0) {
				return 1;
			}
			else if (info2.isPrefixPattern() && info1.getDoubleWildcards() == 0) {
				return -1;
			}

			if (info1.getTotalCount() != info2.getTotalCount()) {
				return info1.getTotalCount() - info2.getTotalCount();
			}

			if (info1.getLength() != info2.getLength()) {
				return info2.getLength() - info1.getLength();
			}

			if (info1.getSingleWildcards() < info2.getSingleWildcards()) {
				return -1;
			}
			else if (info2.getSingleWildcards() < info1.getSingleWildcards()) {
				return 1;
			}

			if (info1.getUriVars() < info2.getUriVars()) {
				return -1;
			}
			else if (info2.getUriVars() < info1.getUriVars()) {
				return 1;
			}

			return 0;
		}


		/**
		 * Value class that holds information about the pattern, e.g. number of
		 * occurrences of "*", "**", and "{" pattern elements.
		 */
		private static class PatternInfo {

			/** pattern*/
			private final String pattern;

			/** uriVars*/
			private int uriVars;

			/** singleWildcards*/
			private int singleWildcards;
			
			/** doubleWildcards*/
			private int doubleWildcards;

			/** catchAllPattern*/
			private boolean catchAllPattern;

			/** prefixPattern*/
			private boolean prefixPattern;

			/** length */
			private Integer length;

			/**
			 * 
			 * 构造函数
			 * @param pattern pattern
			 */
			public PatternInfo(String pattern) {
				this.pattern = pattern;
				if (this.pattern != null) {
					initCounters();
					this.catchAllPattern = this.pattern.equals("/**");
					this.prefixPattern = !this.catchAllPattern && this.pattern.endsWith("/**");
				}
				if (this.uriVars == 0) {
					this.length = (this.pattern != null ? this.pattern.length() : 0);
				}
			}

			/**
			 * 
			 * initCounters
			 */
			protected void initCounters() {
				int pos = 0;
				while (pos < this.pattern.length()) {
					if (this.pattern.charAt(pos) == '{') {
						this.uriVars++;
						pos++;
					}
					else if (this.pattern.charAt(pos) == '*') {
						if (pos + 1 < this.pattern.length() && this.pattern.charAt(pos + 1) == '*') {
							this.doubleWildcards++;
							pos += 2;
						}
						else if (pos > 0 && !this.pattern.substring(pos - 1).equals(".*")) {
							this.singleWildcards++;
							pos++;
						}
						else {
							pos++;
						}
					}
					else {
						pos++;
					}
				}
			}

			/**
			 * 
			 * @return uriVars
			 */
			public int getUriVars() {
				return this.uriVars;
			}

			/**
			 * 
			 * @return singleWildcards
			 */
			public int getSingleWildcards() {
				return this.singleWildcards;
			}

			/**
			 * 
			 * @return doubleWildcards
			 */
			public int getDoubleWildcards() {
				return this.doubleWildcards;
			}

			/**
			 * 
			 * @return isLeastSpecific
			 */
			public boolean isLeastSpecific() {
				return (this.pattern == null || this.catchAllPattern);
			}

			/**
			 * 
			 * @return prefixPattern
			 */
			public boolean isPrefixPattern() {
				return this.prefixPattern;
			}

			/**
			 * 
			 * @return TotalCount
			 */
			public int getTotalCount() {
				return this.uriVars + this.singleWildcards + (2 * this.doubleWildcards);
			}

			/**
			 * Returns the length of the given pattern, where template variables are considered to be 1 long.
			 * @return int
			 */
			public int getLength() {
				if (this.length == null) {
					this.length = VARIABLE_PATTERN.matcher(this.pattern).replaceAll("#").length();
				}
				return this.length;
			}
		}
	}


	/**
	 * A simple cache for patterns that depend on the configured path separator.
	 */
//	private static class PathSeparatorPatternCache {
//
//		/** endsOnWildCard */
//		private final String endsOnWildCard;
//
//		/** endsOnDoubleWildCard */
//		private final String endsOnDoubleWildCard;
//
//		/**
//		 * 
//		 * 构造函数
//		 * @param pathSeparator pathSeparator
//		 */
//		public PathSeparatorPatternCache(String pathSeparator) {
//			this.endsOnWildCard = pathSeparator + "*";
//			this.endsOnDoubleWildCard = pathSeparator + "**";
//		}
//
//		/**
//		 * @return EndsOnWildCard
//		 */
//		public String getEndsOnWildCard() {
//			return this.endsOnWildCard;
//		}
//
//		/**
//		 * 
//		 * @return endsOnDoubleWildCard
//		 */
//		public String getEndsOnDoubleWildCard() {
//			return this.endsOnDoubleWildCard;
//		}
//	}

}
