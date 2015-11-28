package com.mtt.image_downloader;

public class ImageDownloaderException extends Exception {
    public ImageDownloaderException() {
    }

    public ImageDownloaderException(String message) {
        super(message);
    }

    public ImageDownloaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageDownloaderException(Throwable cause) {
        super(cause);
    }

    public ImageDownloaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
