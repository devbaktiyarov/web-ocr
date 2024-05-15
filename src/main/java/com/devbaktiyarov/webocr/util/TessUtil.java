package com.devbaktiyarov.webocr.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TessUtil {

    public static BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
