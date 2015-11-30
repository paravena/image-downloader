package com.mtt.image_downloader;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class ImageDownloader {
    public static int[] RESIZE_WIDTHS = new int[] {100, 220, 320};
    protected ContentReader contentReader;
    protected Utility utility;
    protected File outputFolder;

    public ImageDownloader(File outputFolder) {
        this.contentReader = ContentReader.getInstance();
        this.utility = Utility.getInstance();
        this.outputFolder = outputFolder;
    }

    public void downloadImages(URI url) throws ImageDownloaderException {
        Elements elements = contentReader.readContent(url);
        for (Element element : elements) {
            String imageUrl = element.attr("src");
            processImage(imageUrl);
        }
    }

    private void processImage(String imageUrl) throws ImageDownloaderException {
        try {
            String imageName = utility.extractImageNameFromUrl(imageUrl);
            BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
            if (!isValidImage(originalImage)) return;
            // saving  original image
            saveImageInOutputFolder(originalImage, imageName, "jpg");
            saveImageInOutputFolder(originalImage, imageName, "png");
            for (int newWidth : RESIZE_WIDTHS) {
                int newHeight = utility.calculateHeightAccordingToWidthRatio(originalImage.getWidth(),
                        originalImage.getHeight(),
                        newWidth);
                BufferedImage resizeImage = resizeImage(originalImage, newWidth, newHeight);
                // saving resize image
                saveImageInOutputFolder(resizeImage, imageName + "_" + newWidth, "jpg");
                saveImageInOutputFolder(resizeImage, imageName + "_" + newWidth, "png");
            }
        } catch (IOException ioe) {
            throw new ImageDownloaderException(ioe);
        }
    }

    private boolean isValidImage(BufferedImage image) {
        return image != null && image.getWidth() > 10 && image.getHeight() > 10;
    }

    private void saveImageInOutputFolder(BufferedImage resizeImage, String imageName, String format) throws IOException {
        File outputFile = new File(outputFolder + File.separator + imageName + "." + format);
        ImageIO.write(resizeImage, format, outputFile);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
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
