package com.mtt.image_downloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    private static Utility instance;

    public static Utility getInstance() {
        if (instance == null) {
            instance = new Utility();
        }
        return instance;
    }

    private Utility() {

    }

    public String extractImageNameFromUrl(String imageUrl) {
        String imageName = imageUrl;
        String regex = "\\/?(\\w+?)(\\.\\w{3})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(imageUrl);
        if (matcher.find()) {
            imageName = matcher.group(1);
        }
        return imageName;
    }

    public int calculateHeightAccordingToWidthRatio(double width, double height, int newWidth) {
        double ratio = newWidth / width;
        return new Double(height * ratio).intValue();
    }

    public String normalizeImageUrl(URL pageUrl, String imageUrlPath) {
        if (imageUrlPath.startsWith("http")) {
            return imageUrlPath;
        } else if (imageUrlPath.startsWith("/")) {
            return pageUrl.getProtocol()
                    + "://"
                    + pageUrl.getHost()
                    + ((pageUrl.getPort() > 0) ? ":" + pageUrl.getPort() : "")
                    + imageUrlPath;
        } else {
            String path = pageUrl.getPath();
            String regex = "\\/([0-9a-zA-Z\\.]+)?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                int length = matcher.group(1).length();
                String sub = path.substring(0, path.length() - length);
                return pageUrl.getProtocol()
                        + "://"
                        + pageUrl.getHost()
                        + ((pageUrl.getPort() > 0) ? ":" + pageUrl.getPort() : "")
                        + sub +
                        imageUrlPath;
            }
        }
        return null;
    }
}
