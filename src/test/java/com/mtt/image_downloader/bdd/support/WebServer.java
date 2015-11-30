package com.mtt.image_downloader.bdd.support;

import fi.iki.elonen.NanoHTTPD;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebServer extends NanoHTTPD {
    private String content;

    public WebServer(String content, int port) {
        super(port);
        this.content = content;
    }

    @Override
    public Response serve(IHTTPSession session) {
        System.out.println(session.getUri());
        if (session.getUri().endsWith("content")) {
            return newFixedLengthResponse(content);
        } else {
            String regex = "\\/(\\w+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(session.getUri());
            if (matcher.find()) {
                String fileName = matcher.group(1) + ".jpg";
                System.out.println("fileName = " + fileName);
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("images/" + fileName);
                return newFixedLengthResponse(Response.Status.OK, "image/jpeg", is, 10000000);
            }
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/html", "Resource not found");
    }

    public static void main(String[] args) {
        try {
            new WebServer("Hello", 9090).start(5000, false);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
