/*
 * Copyright (c) 2019. 唐江华 保留所有权。
 */

package com.google.webp.util;

import com.luciad.imageio.webp.WebPReadParam;
import com.luciad.imageio.webp.WebPWriteParam;

import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author tangjianghua
 * @data @data
 */
public class WebpUtils {

    public static void main(String args[]) {
        String srcFile = "D:\\file\\imgs\\order/b121502f8ed9346f9ee1236e5a1f7ab4.png";
        String webpFile = "D:\\file\\imgs\\order/b121502f8ed9346f9ee1236e5a1f7ab4.png.webp";
        String toFile1 = "D:\\file\\imgs\\order/abc.png";
        String toFile2 = "D:\\file\\imgs\\order/adbc.jpg";
        String toFile3 = "D:\\file\\imgs\\order/asd.jpeg";
//        encodingToWebp(srcFile, webpFile);
        decodingFromWebp(webpFile, toFile1);
        decodingFromWebp(webpFile, toFile2);
        decodingFromWebp(webpFile, toFile3);
    }


    /**
     * @param: srcFile
     * @param: webpFile
     * @description: 将文件编码为WEBP格式
     * @author: tangjianghua
     */
    public static void encodingToWebp(String srcFile, String webpFile) {
        encodingToWebp(new File(srcFile), new File(webpFile));
    }

    /**
     * @param: srcFile
     * @param: webpFile
     * @description: 将文件编码为WEBP格式
     * @author: tangjianghua
     */
    public static void encodingToWebp(File srcFile, File webpFile) {

        try {

            // Obtain an image to encode from somewhere
            BufferedImage image = ImageIO.read(srcFile);

            // Obtain a WebP ImageWriter instance
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

            // Configure encoding parameters
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);

            // Configure the output on the ImageWriter
            FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(webpFile);
            writer.setOutput(fileImageOutputStream);

            // Encode
            writer.write(null, new IIOImage(image, null, null), writeParam);

            //释放reader
            writer.dispose();

            //关闭文件流
            fileImageOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param: webpFile
     * @param: toFile
     * @param: fileType 文件格式 png,jpg,jpeg
     * @description: 将WEBP格式解码为其他文件
     * @author: tangjianghua
     */
    public static void decodingFromWebp(String webpFile, String toFile) {
        decodingFromWebp(new File(webpFile), new File(toFile), toFile.substring(toFile.lastIndexOf('.') + 1, toFile.length()));
    }

    /**
     * @param: webpFile
     * @param: toFile
     * @param: fileType 文件格式 png,jpg,jpeg
     * @description: 将WEBP格式解码为其他文件
     * @author: tangjianghua
     */
    public static void decodingFromWebp(File webpFile, File toFile, String fileType) {

        try {
            // Obtain a WebP ImageReader instance
            ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();

            // Configure decoding parameters
            WebPReadParam readParam = new WebPReadParam();
            readParam.setBypassFiltering(true);

            // Configure the input on the ImageReader
            FileImageInputStream fileImageInputStream = new FileImageInputStream(webpFile);
            reader.setInput(fileImageInputStream);

            // Decode the image
            BufferedImage image = reader.read(0, readParam);

            ImageIO.write(image, fileType, toFile);

            //释放reader
            reader.dispose();

            //关闭文件流
            fileImageInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
