package com.mtt.image_downloader;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageProcessor {
    public static int[] RESIZE_WIDTHS = new int[] {100, 220, 320};
    private static ImageProcessor instance;
    private Utility utility;
    private ImageProcessor() {
        utility = Utility.getInstance();
    }

    public static ImageProcessor getInstance() {
        if (instance == null) {
            instance = new ImageProcessor();
        }
        return instance;
    }

    public void processImage(String imageUrl, File outputFolder) throws ImageDownloaderException {
        try {
            String imageName = utility.extractImageNameFromUrl(imageUrl);
            BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
            if (!isValidImage(originalImage)) return;
            // saving  original image
            saveImageInOutputFolder(originalImage, imageName, "jpg", outputFolder);
            saveImageInOutputFolder(originalImage, imageName, "png", outputFolder);
            for (int newWidth : RESIZE_WIDTHS) {
                int newHeight = utility.calculateHeightAccordingToWidthRatio(originalImage.getWidth(),
                        originalImage.getHeight(),
                        newWidth);
                BufferedImage resizeImage = resizeImage(originalImage, newWidth, newHeight);
                // saving resize image
                saveImageInOutputFolder(resizeImage, imageName + "_" + newWidth, "jpg", outputFolder);
                saveImageInOutputFolder(resizeImage, imageName + "_" + newWidth, "png", outputFolder);
            }
        } catch (IOException ioe) {
            throw new ImageDownloaderException(ioe);
        }
    }

    public boolean isValidImage(BufferedImage image) {
        return image != null && image.getWidth() > 10 && image.getHeight() > 10;
    }

    public void saveImageInOutputFolder(BufferedImage bufferedImage,
                                        String imageName,
                                        String format, File outputFolder) throws IOException {
        File outputFile = new File(outputFolder + File.separator + imageName + "." + format);
        ImageIO.write(bufferedImage, format, outputFile);
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizeImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizeImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return resizeImage;
    }
}
