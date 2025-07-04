package com.zach.funnyutils.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zach.funnyutils.model.ImageParam;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageController {

    private static final String ASCII_CHARS = "█▓▒░* ";

    @PostMapping("/image-to-ascii")
    public String convertImageToAscii(
            @RequestParam("image") MultipartFile file,
            @RequestParam("imageParam") String paramJson
    ) throws IOException {
        // 解析JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        ImageParam param = mapper.readValue(paramJson, ImageParam.class);

        BufferedImage image = ImageIO.read(file.getInputStream());
        BufferedImage resizedImage;
        if (checkParam(param)) {
            // 按照比例调整图片大小
            resizedImage = Thumbnails.of(image)
                    .scale((param.getScale()/20))
                    .asBufferedImage();
        } else {
            resizedImage = Thumbnails.of(image)
                    .width(param.getWidth())
                    .height(param.getHeight())
                    .asBufferedImage();
        }


        StringBuilder asciiImage = new StringBuilder();
        for (int y = 0; y < resizedImage.getHeight(); y++) {
            for (int x = 0; x < resizedImage.getWidth(); x++) {
                int rgb = resizedImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int gray = (r + g + b) / 3;
                int index = (gray * (ASCII_CHARS.length() - 1)) / 255;
                asciiImage.append(ASCII_CHARS.charAt(index));
            }
            asciiImage.append("\n");
        }
        return asciiImage.toString();
    }

    private boolean checkParam(ImageParam param) {
        int height = param.getHeight();
        int width = param.getWidth();
        return height <= 0 || width <= 0;
    }

}