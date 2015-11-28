package com.mtt.image_downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;

public class ContentReader {
    private static ContentReader instance;
    private ContentReader() {
    }

    public static ContentReader getInstance() {
        if (instance == null) {
            instance = new ContentReader();
        }
        return instance;
    }

    public Elements readContent(URI url) throws ImageDownloaderException {
        try {
            Document document = Jsoup.connect(url.toString()).get();
            return document.select("img");
        } catch (IOException ex) {
            throw new ImageDownloaderException(ex);
        }
    }
}
