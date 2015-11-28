package com.mtt.image_downloader;

import java.io.*;
import java.net.URI;

public class ImageDownloader {
    protected ContentReader contentReader;

    public ImageDownloader() {
        contentReader = ContentReader.getInstance();
    }

    public void downloadImages(URI url, File outputFolder) throws ImageDownloaderException {
        String content = contentReader.readContent(url);
    }
}
