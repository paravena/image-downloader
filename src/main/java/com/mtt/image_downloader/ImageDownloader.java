package com.mtt.image_downloader;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URI;

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
        boolean proceed = true;
        if (!outputFolder.exists()) {
            proceed = outputFolder.mkdir();
        }
        if (!proceed) return;

        Elements elements = contentReader.readContent(url);
        for (Element element : elements) {
            String imageUrl = element.attr("src");
            imageProcessor.processImage(imageUrl, outputFolder);
        }
    }
}
