package com.mtt.image_downloader.bdd.steps;

import com.mtt.image_downloader.Start;
import com.mtt.image_downloader.bdd.support.WebServer;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.List;

public class ImageDownloaderSteps {
    private WebServer webServer;

    @Given("^I have a url for a web page with following images:$")
    public void createWebPage(DataTable dataTable) throws IOException {
        List<String> imageUrls = dataTable.asList(String.class);
        StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("<p>following images must be downloaded:</b>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<br>");
        for (String imageUrl : imageUrls) {
            sb.append("<img src=\"").append(imageUrl).append("\">");
        }
        sb.append("</body></html>");
        webServer = new WebServer(sb.toString(), 9090);
        webServer.start();
    }

    @When("^I start the image downloader$")
    public void startImageDownloader() {
        Start.main(new String[] {"-url", "http://localhost:9090"});
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.out);
        }
    }

    @Then("^All images defined in the web page are downloaded$")
    public void shouldDownloadAllImages() {
        throw new PendingException();
    }

    @Then("^For each image downloaded I have three different dimensions$")
    public void shouldBeThreeDifferentDimensionsForEachImageDownloaded() {
        throw new PendingException();
    }

    @Then("^For each image downloaded I have two different formats$")
    public void shouldBeTwoFormatsForEachImageDownloaded() {
        throw new PendingException();
    }
}
