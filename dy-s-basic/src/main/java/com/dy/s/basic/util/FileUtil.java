package com.dy.s.basic.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @description 文件操作类
 * @author dxy
 * @date 2019-12-27
 * 
 */
public class FileUtil {
	
	private FileUtil(){}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 删除指定目录下的所有文件
	 * 
	 * @param folderPath
	 *            目录路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delAllFile(String folderPath) {
		boolean flag = false;
		File file = new File(folderPath);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (folderPath.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(folderPath + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(folderPath + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 根据文件名部分匹配
	 * 删除指定目录下的所有文件
	 * 
	 * @param folderPath
	 *            目录路径
	 * @param fileName
	 *            文件名
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delAllFileByFileNm(String folderPath,String fileName) throws Exception{
		boolean flag = false;
		File file = new File(folderPath);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		for (int i = 0; i < tempList.length; i++) {
			File temp = null;
			
			if (folderPath.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			
			if (temp.isFile() && temp.getName().contains(fileName)) {
				temp.delete();
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param filePath
	 *            指定文件的路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			return flag;
		}
		flag = (new File(filePath)).delete();
		return flag;
	}

	/**
	 * 删除指定文件夹(包括文件夹下的所有文件)
	 * 
	 * @param folderPath
	 *            指定文件夹路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
	        //2017-06-15 3.42.0 HXS 根据提示，修正代码规范 str
			LOGGER.error(e.getMessage(),e);
	        //2017-06-15 3.42.0 HXS 根据提示，修正代码规范 end
			return false;
		}
		return true;
	}

	/**
	 * 读取文本文件的内容
	 * 
	 * @param curfile
	 *            文本文件路径
	 * @return 返回文件内容
	 */
	public static String readFile(String curfile) {
		File f = new File(curfile);
		try {
			if (!f.exists()){
				throw new Exception();
			}
				
			FileReader cf = new FileReader(curfile);
			BufferedReader is = new BufferedReader(cf);
			String filecontent = "";
			String str = is.readLine();
			while (str != null) {
				filecontent += str;
				str = is.readLine();
				if (str != null){
					filecontent += "\n";
				}
					
			}
			is.close();
			cf.close();
			return filecontent;
		} catch (Exception e) {
			System.err.println("不能读属性文件: " + curfile + " \n" + e.getMessage());
			return "";
		}

	}

	/**
	 * 取指定文件的扩展名
	 * 
	 * @param filePathName
	 *            文件路径
	 * @return 扩展名
	 */
	public static String getFileExt(String filePathName) {
		int pos = 0;
		pos = filePathName.lastIndexOf('.');
		if (pos != -1){
			return filePathName.substring(pos + 1, filePathName.length());
		}else{
			return "";
		}
			

	}

	/**
	 * 读取文件大小
	 * 
	 * @param filename
	 *            指定文件路径
	 * @return 文件大小
	 */
	public static int getFileSize(String filename) {
		try {
			File fl = new File(filename);
			int length = (int) fl.length();
			return length;
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * 拷贝文件到指定目录
	 * 
	 * @param srcPath
	 *            源文件路径
	 * @param destPath
	 *            目标文件路径
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyFile(String srcPath, String destPath) {
		try {
			File fl = new File(srcPath);
			int length = (int) fl.length();
			FileInputStream is = new FileInputStream(srcPath);
			FileOutputStream os = new FileOutputStream(destPath);
			byte[] b = new byte[length];
			is.read(b);
			os.write(b);
			is.close();
			os.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Function Name: scanDisk
	 * 
	 * @return description:获取指定目录下的所有文件列表 Modification History:
	 */
	public static List<File> scanDisk(File basedir) {
		List<File> ret = new ArrayList<File>();
		File[] tmp = basedir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile()) {
				ret.add(tmp[i]);
			}
		}
		return ret;
	}

	/**
	 * 使用系统默认的应用程序打开指定路径的文件
	 * 
	 * @param path
	 *            文件的绝对路径
	 */
	public static void openFileByPath(String path) {
		String openCmd = "cmd /c " + path;
		try {
			Runtime.getRuntime().exec(openCmd);
		} catch (IOException e) {
	        //2017-06-15 3.42.0 HXS 根据提示，修正代码规范 str
			LOGGER.error(e.getMessage(),e);
	        //2017-06-15 3.42.0 HXS 根据提示，修正代码规范 end
		}
	}

	/**
	 * 根据文件路径获取文件名
	 * 
	 * @param path
	 *            文件的绝对路径名
	 * @return 文件名
	 */
	public static String getFileNameByPath(String path) {
		int index1 = path.lastIndexOf('/');
		int index2 = path.lastIndexOf('\\');

		// 考虑了稳健路径中/符号和\\符号混合的情况
		if (index1 == -1 && index2 == -1) {
			return "";
		}

		return path.substring(Math.max(index1, index2) + 1);
	}

	/**
	 * 根据文件路径获取文件名,不含后缀名
	 * 
	 * @param path
	 *            文件的绝对路径名
	 * @return 文件名
	 */
	public static String getFileNameWithoutExtensionByPath(String path) {
		int index1 = path.lastIndexOf('/');
		int index2 = path.lastIndexOf('\\');

		// 考虑了稳健路径中/符号和\\符号混合的情况
		if (index1 == -1 && index2 == -1) {
			return "";
		}

		String fileNameString = path.substring(Math.max(index1, index2) + 1);

		int indexOfDot = fileNameString.lastIndexOf('.');
		return indexOfDot < 0 ? fileNameString : fileNameString.substring(0,
				indexOfDot);
	}

	/**
	 * 根据文件路径获取文件所在的目录
	 * 
	 * @param path
	 *            文件的绝对路径
	 * @return 文件所在 的目录
	 */
	public static String getFileDirectoryByPath(String path) {
		int index1 = path.lastIndexOf('/');
		int index2 = path.lastIndexOf('\\');

		// 考虑了文件路径中/符号和\\符号混合的情况
		if (index1 == -1 && index2 == -1) {
			return "";
		}

		return path.substring(0, Math.max(index1, index2));
	}

	/***
	 * 根据路径名创建目录，会创建所有不存在的路径
	 * 
	 * @param dirPath
	 *            路径名
	 * @return
	 */
	public static boolean mkDirs(String dirPath) {
		File dir = new File(dirPath);

		// 已经存在了
		if (dir.exists()) {
			return false;
		}

		return dir.mkdirs();
	}


	/**
	 *  判断指定路径下是否包含fileName命名的文件。 待完善，现在就算已经找到了，还会搜索所有的子目录
	 * @param baseDir
	 * @param fileName
	 * @return
	 */
	public static boolean isContainFile(String baseDir, String fileName) {
		File baseFile = new File(baseDir);

		if (!baseFile.exists()) {
			return false;
		}

		boolean isExist = false;
		File[] tempFiles = baseFile.listFiles();

		for (int i = 0; i < tempFiles.length; i++) {
			File file = tempFiles[i];
			if (file.isFile()) {
				if (file.getName().contains(fileName)) {
					return true;
				}
			} else {
				isExist = isExist
						|| isContainFile(file.getAbsolutePath(), fileName);
			}
		}

		return isExist;
	}

	/**
	 * 获取指定目录下所有的文件，递归
	 * 
	 * @param baseDir
	 * @return
	 */
	public static List<File> getAllFiles(String baseDir) {
		File baseFile = new File(baseDir);

		if (!baseFile.exists()) {
			return null;
		}

		if (baseFile.isFile()) {
			return null;
		}

		List<File> fileList = new ArrayList<File>();

		File[] srcFiles = baseFile.listFiles();

		for (File file : srcFiles) {
			if (file.isDirectory()) {
				fileList.addAll(getAllFiles(file.getAbsolutePath()));
			} else {
				fileList.add(file);
			}
		}

		return fileList;
	}

	/**
	 * 获取指定目录下所有的文件的文件名，递归
	 * 
	 * @param baseDir
	 * @return
	 */
	public static List<String> getAllFileNames(String baseDir) {
		File baseFile = new File(baseDir);

		if (!baseFile.exists()) {
			return null;
		}

		if (baseFile.isFile()) {
			return null;
		}

		List<String> fileNameList = new ArrayList<String>();

		File[] srcFiles = baseFile.listFiles();

		for (File file : srcFiles) {
			if (file.isDirectory()) {
				fileNameList.addAll(getAllFileNames(file.getAbsolutePath()));
			} else {
				fileNameList.add(file.getAbsolutePath());
			}
		}

		return fileNameList;
	}

}
