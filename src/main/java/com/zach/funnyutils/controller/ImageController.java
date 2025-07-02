package com.zach.funnyutils.controller;

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

    private static final String ASCII_CHARS = "@%#*+=-:. ";

    @PostMapping("/image-to-ascii")
    public String convertImageToAscii(
            @RequestParam("image") MultipartFile file,
            @RequestParam(value = "width", defaultValue = "80") int width,
            @RequestParam(value = "height", defaultValue = "40") int height
    ) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        // 调整图片大小
        BufferedImage resizedImage = Thumbnails.of(image).size(width, height).asBufferedImage();

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
}