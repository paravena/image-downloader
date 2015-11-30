package com.mtt.image_downloader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageIO.class)
public class ImageDownloaderTest {
    private ImageDownloader imageDownloader;

    @Before
    public void initialize() {
        imageDownloader = new ImageDownloader(new File("/tmp"));
        mockStatic(ImageIO.class);
    }

    @Test
    public void testProcessImage() throws IOException, ImageDownloaderException {
        BufferedImage bufferedImage = new BufferedImage(600, 600, BufferedImage.TYPE_4BYTE_ABGR);
        String imageUrl = "http://some-server.com/image.jpg";
        when(ImageIO.read(eq(new URL(imageUrl)))).thenReturn(bufferedImage);
        when(ImageIO.write(any(BufferedImage.class), anyString(), any(File.class))).thenReturn(true);
        imageDownloader.processImage(imageUrl);
        assertTrue(imageDownloader.isValidImage(bufferedImage));
        verifyStatic(times(1));
        ImageIO.write(eq(bufferedImage), eq("jpg"), eq(new File("/tmp/image.jpg")));
        verifyStatic(times(1));
        ImageIO.write(eq(bufferedImage), eq("png"), eq(new File("/tmp/image.png")));
        verifyStatic(times(1));
        ImageIO.write(any(BufferedImage.class), eq("png"), eq(new File("/tmp/image_100.png")));
        verifyStatic(times(1));
        ImageIO.write(any(BufferedImage.class), eq("jpg"), eq(new File("/tmp/image_100.jpg")));
        verifyStatic(times(1));
        ImageIO.write(any(BufferedImage.class), eq("png"), eq(new File("/tmp/image_220.png")));
        verifyStatic(times(1));
        ImageIO.write(any(BufferedImage.class), eq("jpg"), eq(new File("/tmp/image_220.jpg")));
        verifyStatic(times(1));
        ImageIO.write(any(BufferedImage.class), eq("png"), eq(new File("/tmp/image_320.png")));
        verifyStatic(times(1));
        ImageIO.write(any(BufferedImage.class), eq("jpg"), eq(new File("/tmp/image_320.jpg")));
    }
}
