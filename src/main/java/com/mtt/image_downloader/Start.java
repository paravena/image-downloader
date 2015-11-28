package com.mtt.image_downloader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Start {

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("u", "url", true, "Url web page");
        options.addOption("o", "out", true, "Output directory");
        options.addOption("h", "help", false, "Help");
        options.addOption("v", "version", false, "Version");
        CommandLineParser parser = new DefaultParser();
        URI url = null;
        File outputFolder = new File("output");
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                help(options);
                return;
            } else if (cmd.hasOption("version")) {
                version();
                return;
            } else {
                if (cmd.hasOption("url")) {
                    String webPageUrl = cmd.getOptionValue("url");
                    url = new URI(webPageUrl);
                }

                if (cmd.hasOption("out")) {
                    String output = cmd.getOptionValue("out");
                    outputFolder = new File(output);
                }
            }

            new ImageDownloader().downloadImages(url, outputFolder);
        } catch (ParseException | URISyntaxException | ImageDownloaderException e) {
            e.printStackTrace(System.out);
        }


    }

    public static void help(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        String header = "When using this ImageDownloader you have following options\n\n";
        String footer = "\nPlease report any issue at http://github.com/paravena\n";
        formatter.printHelp("ImageDownloader", header, options, footer, true);
    }

    public static void version() {
        System.out.println("ImageDownloader version 0.1");
    }
}