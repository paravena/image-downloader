Feature: Image Downloader
  As a user I want to use an Image Downloader when I need to download images from a given url

  Scenario: Downloading images
    Given I have a url for a web page with following images:
      |http://localhost:9090/coyote|
      |http://localhost:9090/sylvester|
      |http://localhost:9090/lukasduck|
    When I start the image downloader saving images in folder /tmp
    Then Following images should be downloaded:
      |coyote.jpg|
      |coyote.png|
      |sylvester.jpg|
      |sylvester.png|
      |lukasduck.png|
      |lukasduck.jpg|
    And Following images should be downloaded:
      |coyote_100.jpg|
      |coyote_100.png|
      |coyote_220.jpg|
      |coyote_220.png|
      |coyote_320.jpg|
      |coyote_320.png|
      |sylvester_100.jpg|
      |sylvester_100.png|
      |sylvester_220.jpg|
      |sylvester_220.png|
      |sylvester_320.jpg|
      |sylvester_320.png|
      |lukasduck_100.jpg|
      |lukasduck_100.png|
      |lukasduck_220.jpg|
      |lukasduck_220.png|
      |lukasduck_320.jpg|
      |lukasduck_320.png|

  Scenario: Downloading images but not all are saved
    Given I have a url for a web page with following images:
      |http://localhost:9090/sylvester|
      |http://localhost:9090/tiny|
    When I start the image downloader saving images in folder /tmp
    Then Following images should be downloaded:
      |sylvester.jpg|
      |sylvester.png|
    Then Following images should not be downloaded:
      |tiny.jpg|
      |tiny.png|
    And Following images should be downloaded:
      |sylvester_100.jpg|
      |sylvester_100.png|
      |sylvester_220.jpg|
      |sylvester_220.png|
      |sylvester_320.jpg|
      |sylvester_320.png|

