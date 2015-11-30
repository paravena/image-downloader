package com.mtt.image_downloader;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class ContentReaderTest {
    private ContentReader contentReader;

    @Before
    public void initialize() {
        contentReader = ContentReader.getInstance();
        mockStatic(Jsoup.class);

    }

    @Test
    public void testReadContent() throws IOException, URISyntaxException, ImageDownloaderException {
        Connection connection = mock(Connection.class);
        when(Jsoup.connect(anyString())).thenReturn(connection);
        String html = "<html><body>" +
                "<img src=\"http://some-server.com/image_1.jpg\">" +
                "<img src=\"http://some-server.com/image_2.jpg\">" +
                "<img src=\"http://some-server.com/image_3.jpg\">" +
                "</body></html>";
        Document document = Parser.parse(html, "http://some-server.com");
        when(connection.get()).thenReturn(document);
        Elements elements = contentReader.readContent(new URI("http://some-server.com"));
        assertNotNull(elements);
        assertEquals(3, elements.size());
    }

    @Test(expected = ImageDownloaderException.class)
    public void testReadContentFromWrongServer() throws URISyntaxException, ImageDownloaderException, IOException {
        Connection connection = mock(Connection.class);
        when(Jsoup.connect(anyString())).thenReturn(connection);
        when(connection.get()).thenThrow(new UnknownHostException("Server not found!!"));
        contentReader.readContent(new URI("http://wrong-server.com"));
    }
}
