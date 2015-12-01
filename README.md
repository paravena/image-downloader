# Image Downloader

## How to Build the Project

> The build tool I chose for building the project is Maven, the reason is because I'm more familiar
> with this tool than Gradle. I know Gradle offers more flexibility and capabilities those are not
> required for this small project.

> For building the project, execute following command:

mvn assembly:assembly

> The previous command will compile, run tests and package the application including all the dependency
> libraries. After doing this go to the *target* folder and you will find a file with a long name.

image-downloader-1.0-SNAPSHOT-jar-with-dependencies.jar

> You can change the name of the file to simply image-downloader.jar


## Usage

> The way you use the program is as follow:

java -jar image-downloader.jar -url http://www.google.com -out /tmp

> Also you can see the usage of the program with -help option.

## Design Choices

> Most of the design choices were regarding to which libraries I can use for parsing html pages, and also how
> to resize and save images in different formats.

> For parsing html pages at the beginning  I thought about using a simple http client and then use regular expressions.
> Finally I found a good library called [Jsoup](http://jsoup.org) which do both things request a html page and then use
> simple CSS selectors to get elements.

> About resizing and image or saving it with different formats I used ImageIO standard class. Not sure if this choices was
> a good one, since it looks like the performance is poor.

## Design Patterns

### Service Facade

> ImageDownloader class is a Service Facade component behind this component there are two services:

* ContentReader, this component service do everything related to download a web page and parse its content being able to
collect elements defined on it, like image elements.

* ImageProcessor, this component service is the one that is in charge of processing images, firsly resizing them and also
save them into disc with two different formats (png and jpeg).

### Singleton

> Service components like ContentReader and ImageProcessor are singleton components.


## Testing

