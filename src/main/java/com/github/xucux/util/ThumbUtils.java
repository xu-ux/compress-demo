package com.github.xucux.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @descriptions: 图片处理工具
 * @author: xucl
 * @date: 2021/11/1 10:52
 * @version: 1.0
 */
@Slf4j
public class ThumbUtils {


    /**
     * 按图片质量压缩
     * @param oldPath
     * @param newPath
     */
    public static void compressLocal(String oldPath,String newPath,float quality)  {
        try {
            Thumbnails.of(new File(oldPath))// "D:/showqrcode.jpg"
                    .scale(1f) //1f 图片大小（长宽）压缩比例 从0-1，1表示原图
                    .outputQuality(quality) // 0.5f 图片质量压缩比例 从0-1，越接近1质量越好
                    .toOutputStream(new FileOutputStream(newPath));
        } catch (IOException e) {
            log.error("压缩图片失败",e);
        }
    }

    /**
     * 等比例缩放图片
     * @param oldPath
     * @param newPath
     * @param scale
     */
    public static void scale(String oldPath,String newPath,float scale){
        try {
            Thumbnails.of(new File(oldPath))
                    .scale(0.5f) //图片大小（长宽）压缩 从0按照
                    .outputQuality(1f) //图片质量压缩比例 从0-1，越接近1质量越好
                    .toOutputStream(new FileOutputStream(newPath));
        } catch (IOException e) {
            log.error("缩放图片失败",e);
        }
    }

    /**
     * 按指定大小缩放图片,保持原来图片比例
     * @param oldPath
     * @param newPath
     * @param width
     * @param height
     */
    public static void size(String oldPath,String newPath,int width, int height){
        try {
            Thumbnails.of(new File(oldPath))
                    .size(width, height) // 图片比例不变
                    .toOutputStream(new FileOutputStream(newPath));
        } catch (IOException e) {
            log.error("缩放图片失败",e);
        }
    }

    /**
     * 按指定大小缩放图片,不保持图片比例
     * @param oldPath
     * @param newPath
     * @param width
     * @param height
     */
    public static void forceSize(String oldPath,String newPath,int width, int height){
        try {
            Thumbnails.of(new File(oldPath))
                    .forceSize(width, height) //不保持图片比例
                    .toOutputStream(new FileOutputStream(newPath));
        } catch (IOException e) {
            log.error("缩放图片失败",e);
        }
    }

    /**
     * 旋转图片
     * @param oldPath
     * @param newPath
     * @param rotate
     */
    public static void rotate(String oldPath,String newPath,float rotate){
        try {
            Thumbnails.of(new File(oldPath))
                    .rotate(rotate) // 90f 向右旋转
                    .toOutputStream(new FileOutputStream(newPath));
        } catch (IOException e) {
            log.error("缩放图片失败",e);
        }
    }

    /**
     * 图片添加水印
     *
     * @param oldPath
     * @param newPath
     * @param watermarkPath
     * @param opacity
     */
    public static void watermark(String oldPath,String newPath,String watermarkPath,float opacity){
        try {
            Thumbnails.of(oldPath)
//                    .size(1280, 1024)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(watermarkPath)),// "D:/watermark.jpg"
                            opacity) //0.5f 位置，水印来源，透明度
                    .outputQuality(1f)
                    .toFile(newPath);
        } catch (IOException e) {
            log.error("图片添加水印失败",e);
        }
    }

    /**
     * 裁剪
     * @param oldPath
     * @param newPath
     */
    public static void tailor(String oldPath,String newPath,int width, int height){
        try {
            Thumbnails.of(oldPath)
                    .sourceRegion(Positions.CENTER, width, height) //裁剪位置，宽，高
                    .size(height, height)
                    .keepAspectRatio(false)
                    .toFile(newPath);
        } catch (IOException e) {
            log.error("裁剪图片失败",e);
        }
    }

    /**
     * 将多张图片拼接成一张
     *
     * @param horizontal 是否为水平拼接
     * @param files 文件列表
     * @return 拼接后的文件字节数组
     */
    public static byte[] joinImages(boolean horizontal, File... files) throws IOException {
        if (Objects.isNull(files) || files.length == 0) {
            return null;
        }
        List<BufferedImage> imageList = new ArrayList<>();
        for (File file : files) {
            BufferedImage image = ImageIO.read(file);
            imageList.add(image);
        }
        int height = imageList.get(0).getHeight();
        int width = imageList.get(0).getWidth();
        if (horizontal) {
            width = imageList.stream().mapToInt(BufferedImage::getWidth).sum();
        } else {
            height = imageList.stream().mapToInt(BufferedImage::getHeight).sum();
        }
        //创建拼接后的图片画布，参数分别为宽，高，类型，这里我们使用RGB3元色类型
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = resultImage.getGraphics();
        int previousWidth = 0;
        int previousHeight = 0;
        for (BufferedImage image : imageList) {
            //向画布上画图片
            graphics.drawImage(image, previousWidth, previousHeight, null);
            if (horizontal) {
                previousWidth += image.getWidth();
            } else {
                previousHeight += image.getHeight();
            }
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(resultImage, "jpg", output);
        return output.toByteArray();
    }


}
