package com.mtt.image_downloader.bdd.support;

import fi.iki.elonen.NanoHTTPD;

public class WebServer extends NanoHTTPD {
    private String content;

    public WebServer(String content, int port) {
        super(port);
        this.content = content;
    }

    @Override
    public Response serve(IHTTPSession session) {
        return newFixedLengthResponse(content);
    }


}
