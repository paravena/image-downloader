package com.mtt.image_downloader;

import org.junit.Before;
import org.junit.Test;

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
}
