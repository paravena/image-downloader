package com.mtt.image_downloader;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageDownloader {
    protected ContentReader contentReader;
    protected File outputFolder;
    protected int[] resizeWidths = new int[] {100, 220, 320};

    public ImageDownloader(File outputFolder) {
        this.contentReader = ContentReader.getInstance();
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
            String imageName = extractImageNameFromUrl(imageUrl);
            BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
            for (int newWidth : resizeWidths) {
                int newHeight = calculateHeightAccordingToWidthRatio(originalImage.getWidth(),
                        originalImage.getHeight(),
                        newWidth);
                BufferedImage resizeImage = resizeImage(originalImage, newWidth, newHeight);
                saveImageInOutputFolder(resizeImage, imageName + "_" + newWidth, "jpg");
                saveImageInOutputFolder(resizeImage, imageName + "_" + newWidth, "png");
            }
        } catch (IOException ioe) {
            throw new ImageDownloaderException(ioe);
        }
    }

    private String extractImageNameFromUrl(String imageUrl) {
        String imageName = imageUrl;
        String regex = "\\/?(\\w+?)\\.\\w{3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(imageUrl);
        if (matcher.find()) {
            imageName = matcher.group(1);
        }
        return imageName;
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

    private int calculateHeightAccordingToWidthRatio(int width, int height, int newWidth) {
        double ratio = width / newWidth;
        return new Double(height * ratio).intValue();
    }

}
