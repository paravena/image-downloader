package com.mtt.image_downloader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
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

    public String readContent(URI url) throws ImageDownloaderException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response;

        StringWriter sw = new StringWriter();
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                sw.append(line);
            }
        } catch (IOException ex) {
            throw new ImageDownloaderException(ex);
        }
        return sw.toString();
    }
}
