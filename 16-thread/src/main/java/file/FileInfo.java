/**
 * 
 */
package file;

/**
 * @author yangsai
 *
 */
public class FileInfo {
	private String name;
	private String fullPath;
	private long length;
	
	
	/**
	 * @param name
	 * @param fullPath
	 * @param length
	 */
	public FileInfo(String name, String fullPath, long length) {
		super();
		this.name = name;
		this.fullPath = fullPath;
		this.length = length;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}
	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileInfo [name=" + name + ", fullPath=" + fullPath + ", length=" + length + "]";
	}
}
