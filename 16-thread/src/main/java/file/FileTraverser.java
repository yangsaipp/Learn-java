/**
 * 
 */
package file;

import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 
 * 遍历C盘，找出前50个最大的文件
 * @author yangsai
 *
 */
public class FileTraverser {
	
	PriorityQueue<FileInfo> topQueue = new PriorityQueue<>(50, new Comparator<FileInfo>() {
		@Override
		public int compare(FileInfo o1, FileInfo o2) {
			return (int) (o2.getLength() - o1.getLength());
		}
		
	});
	
	int fileNum = 0;
	int folderNum = 0;
	
	
	/**
	 * 递归遍历
	 * @param path
	 */
	public void traverseWithRecursion(String path) {
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files == null || files.length == 0) {
//				System.out.println("文件夹是空的!");
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						folderNum++;
						traverseWithRecursion(file2.getAbsolutePath());
					} else {
						fileNum++;
						topQueue.offer(new FileInfo(file2.getName(), file2.getAbsolutePath(), file2.length()));
					}
				}
			}
		} else {
//			System.out.println("文件不存在!");
		}
		
	}
	
	public void print() {
		while(true) {
			FileInfo info = topQueue.poll();
			if(info == null) break;
			System.out.println(info);
		}
		System.out.println("folderNum:" + folderNum + "  fileNum:" + fileNum);
	}
	
	
	
	public static void main(String[] args) {
		
		FileTraverser t = new FileTraverser();
		long startTime = System.currentTimeMillis();
		t.traverseWithRecursion("c:\\");
		t.print();
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
//		PriorityQueue<FileInfo> topQueue2 = new PriorityQueue<>(3, new Comparator<FileInfo>() {
//			@Override
//			public int compare(FileInfo o1, FileInfo o2) {
//				return (int) (o2.getLength() - o1.getLength());
//			}
//		});
//		topQueue2.offer(new FileInfo("a","a", 1));
//		topQueue2.offer(new FileInfo("b","b", 5));
//		topQueue2.offer(new FileInfo("c","c", 2));
//		topQueue2.offer(new FileInfo("d","d", 4));
//
//		while(true) {
//			FileInfo info = topQueue2.poll();
//			if(info == null) break;
//			System.out.println(info);
//		}
	}
}
