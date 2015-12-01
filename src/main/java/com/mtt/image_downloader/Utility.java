package com.mtt.image_downloader;

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
}
