package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import com.imooc.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/*
 * 图片处理工具类
 */
public class ImageUtil {

	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddHHmmss");

	private static Random r = new Random();

	/**
	 * 方法描述 ： 给图片添加水印
	 * @param shopImgInputStream
	 * @param targetAddr
	 * @param fileName
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        // 新文件的名字
		String realFileName = getRandomFileName();
		// 获取文件的扩展名
		String extension = getFileExtension(thumbnail.getImageName());
		// 创建目标目录
		makeDirPath(targetAddr);
		// 获取文件存储的相对路径(带文件名)
		String relativeAddr = targetAddr + realFileName + extension;
		// 获取文件要保存到的目标路径
		File dest = new File(relativeAddr);
		// 调用Thumbnails生成带有水印的图片
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "11.jpg")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		// 返回图片相对地址路径
		return relativeAddr;
	}

	/**
	 * 创建目标路径所涉及的目录
	 * 例如：如果目标路径为Users/zhangzhl/Downloads/图片/xxx.jpg
	 * 则Users、zhangzhl、Downloads、图片。这四个文件应都存在如果不存在则都应创建出来
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
