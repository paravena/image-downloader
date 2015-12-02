package com.mtt.image_downloader;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URL;

public class ImageDownloader {
    protected ContentReader contentReader;
    protected ImageProcessor imageProcessor;
    protected File outputFolder;
    protected Utility utility;

    public ImageDownloader(File outputFolder) {
        this.contentReader = ContentReader.getInstance();
        this.imageProcessor = ImageProcessor.getInstance();
        this.utility = Utility.getInstance();
        this.outputFolder = outputFolder;
    }

    public void downloadImages(URL url) throws ImageDownloaderException {
        boolean proceed = true;
        if (!outputFolder.exists()) {
            proceed = outputFolder.mkdir();
        }
        if (!proceed) return;

        Elements elements = contentReader.readContent(url);
        for (Element element : elements) {
            String imageUrl = utility.normalizeImageUrl(url, element.attr("src"));
            System.out.println("Downloading " + imageUrl);
            imageProcessor.processImage(imageUrl, outputFolder);
        }
    }
}
