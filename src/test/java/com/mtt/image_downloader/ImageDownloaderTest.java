package com.mtt.image_downloader;

import org.junit.Before;

import java.io.File;

public class ImageDownloaderTest {
    private ImageDownloader imageDownloader;

    @Before
    public void initialize() {
        imageDownloader = new ImageDownloader(new File("/tmp"));
    }


}
