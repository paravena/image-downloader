package com.mtt.image_downloader.bdd.steps;

import com.mtt.image_downloader.Start;
import com.mtt.image_downloader.bdd.support.WebServer;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ImageDownloaderSteps {
    private WebServer webServer;
    private String outputFolder;

    @Before
    public void initialize() throws IOException {
        webServer = new WebServer(9090);
        webServer.start(50000, false);
    }

    @After
    public void tearDown() {
        webServer.stop();
    }

    @Given("^I have a url for a web page with following images:$")
    public void createWebPage(DataTable dataTable) throws IOException {
        List<String> imageUrls = dataTable.asList(String.class);
        StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("<p>following images must be downloaded:</b>");
        sb.append("<br/>");
        for (String imageUrl : imageUrls) {
            sb.append("<img src=\"").append(imageUrl).append("\">");
            sb.append("<br/>");
        }
        sb.append("</body></html>");
        webServer.setContent(sb.toString());
    }

    @When("^I start the image downloader saving images in folder (.+)$")
    public void startImageDownloader(String outputFolder) {
        this.outputFolder = outputFolder;
        Start.main(new String[] {"-url", "http://localhost:9090/content", "-out", outputFolder});
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.out);
        }
    }

    @Then("^Following images (should|should not) be downloaded:$")
    public void shouldDownloadAllImages(String should, DataTable dataTable) {
        boolean shouldBeSaved = "should".equals(should);
        List<String> imageFiles = dataTable.asList(String.class);
        if (shouldBeSaved) {
            for (String imageFile : imageFiles) {
                assertTrue(new File(outputFolder + File.separator + imageFile).exists());
                assertTrue(new File(outputFolder + File.separator + imageFile).exists());
            }
        } else {
            for (String imageFile : imageFiles) {
                assertFalse(new File(outputFolder + File.separator + imageFile).exists());
                assertFalse(new File(outputFolder + File.separator + imageFile).exists());
            }
        }
    }
}
