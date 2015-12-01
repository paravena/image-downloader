package com.mtt.image_downloader;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class ImageDownloader {
    protected ContentReader contentReader;
    protected ImageProcessor imageProcessor;
    protected File outputFolder;

    public ImageDownloader(File outputFolder) {
        this.contentReader = ContentReader.getInstance();
        this.imageProcessor = ImageProcessor.getInstance();
        this.outputFolder = outputFolder;
    }

    public void downloadImages(URI url) throws ImageDownloaderException {
        Elements elements = contentReader.readContent(url);

        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }

        for (Element element : elements) {
            String imageUrl = element.attr("src");
            imageProcessor.processImage(imageUrl, outputFolder);
        }
    }
}
