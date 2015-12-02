package com.mtt.image_downloader;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.TestCase.assertEquals;

public class UtilityTest {
    private Utility utility;

    @Before
    public void initialize() {
        utility = Utility.getInstance();
    }

    @Test
    public void testExtractImageNameFromUrl() {
        String imageName = utility.extractImageNameFromUrl("http://localhost/pablo.jpg");
        assertEquals("pablo", imageName);
        imageName = utility.extractImageNameFromUrl("pablo.jpg");
        assertEquals("pablo", imageName);
    }

    @Test
    public void testCalculateHeightAccordingToWidthRatio() {
        int height = utility.calculateHeightAccordingToWidthRatio(630, 630, 100);
        assertEquals(100, height);
        height = utility.calculateHeightAccordingToWidthRatio(300, 150, 150);
        assertEquals(75, height);
        height = utility.calculateHeightAccordingToWidthRatio(300, 150, 500);
        assertEquals(250, height);
    }

    @Test
    public void testNormalizeImageUrl() throws MalformedURLException {
        String imageUrl = utility.normalizeImageUrl(new URL("http://www.some-server.com/page/sub/about.html"), "about_me.jpg");
        assertEquals("http://www.some-server.com/page/sub/about_me.jpg", imageUrl);

        imageUrl = utility.normalizeImageUrl(new URL("http://www.some-server.com/page/about.html"), "/images/footer.jpg");
        assertEquals("http://www.some-server.com/images/footer.jpg", imageUrl);

        imageUrl = utility.normalizeImageUrl(new URL("http://www.some-server.com/page/about.html"),
                "http://images-server.net:8080/images/another-image.jpg");
        assertEquals("http://images-server.net:8080/images/another-image.jpg", imageUrl);
    }
}
