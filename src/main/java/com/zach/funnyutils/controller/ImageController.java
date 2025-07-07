package com.zach.funnyutils.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zach.funnyutils.annotation.RateLimit;
import com.zach.funnyutils.enums.LimitType;
import com.zach.funnyutils.model.ImageParam;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class ImageController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    @PostMapping("/image-to-ascii")
    @RateLimit(key = "image-to-ascii:", period = 60, count = 5, limitType = LimitType.IP)
    public String convertImageToAscii(
            @RequestParam("image") MultipartFile file,
            @RequestParam("imageParam") String paramJson
    ) {
        StringBuilder asciiImage = new StringBuilder();
        try(InputStream is = file.getInputStream()) {
            // 解析JSON字符串
            ObjectMapper mapper = new ObjectMapper();
            ImageParam param = mapper.readValue(paramJson, ImageParam.class);

            String ASCII_CHARS = param.getCharInput();
            if (!StringUtils.hasText(ASCII_CHARS)) {
                throw new RuntimeException("输入字符为空！");
            }

            BufferedImage image = ImageIO.read(is);
            BufferedImage resizedImage;
            // 按照比例调整图片大小
            resizedImage = Thumbnails.of(image)
                    .scale((param.getScale()/50))
                    .asBufferedImage();

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
        } catch (IOException e) {
            log.error("转换异常", e);
        }

        return asciiImage.toString();
    }


}