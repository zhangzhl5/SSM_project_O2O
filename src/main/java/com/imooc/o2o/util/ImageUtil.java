package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddHHmmss");

	private static Random r = new Random();

	public static String generateThumbnail(InputStream shopImgInputStream, String targetAddr,String fileName) {

		String realFileName = getRandomFileName();
		String extension = getFileExtension(fileName);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
//		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		File dest = new File(relativeAddr);
		try {
			Thumbnails.of(shopImgInputStream).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "11.jpg")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}

	/**
	 * 创建目标路径所涉及的目录
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的扩展名
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {

//		String originalFileName = thumbnail.getName();
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名，当前年月日时分秒
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取随机的五位数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	
	/**
	 * storePath是文件的路径还是目录的路径
	 * 如果storePath是文件路径则删除该文件
	 * 如果storePath是目录的路径则删除该目录下的所有文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for(int i = 0;i < files.length;i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

	public static void main(String[] args) throws IOException {

		Thumbnails.of(new File("/Users/zhangzhl/Downloads/图片/11.jpg")).size(300, 300)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "324099.jpg")), 0.5f)
				.outputQuality(0.8f).toFile("/Users/zhangzhl/Downloads/图片/dfg.jpg");
	}

}
