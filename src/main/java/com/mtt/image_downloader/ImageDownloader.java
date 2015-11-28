package com.mtt.image_downloader;

import java.io.File;
import java.net.URI;

public class ImageDownloader {
    protected ContentReader contentReader;

    public ImageDownloader() {
        contentReader = ContentReader.getInstance();
    }

    public void downloadImages(URI url, File outputFolder) throws ImageDownloaderException {
        contentReader.readContent(url);
    }
}
