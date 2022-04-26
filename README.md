## UPlag

UPlag is an open-source software for detecting plagiarism amongst programming assignments. Currently, it supports only C programming language files. 

## :computer: Requirements

In order to run UPlag in your local machine, it is required to have the following system properties:
- Java 11;
- Maven 3.8 or higher;
- Any OS.

## :floppy_disk: Installation

When it comes to installation you have two options: download the .jar from the releases or build it locally by executing: `mvn clean install`.

## :page_with_curl:	Commands

UPlag provides customizable commands with the intention to increase the precision of the results. 

### Required commands

To run UPlag in the default settings a few commands are required:
- `-d`: Represents the **absolute** for the code files;
- `-p`: Defines the programs to be analyzed. To get all files in the directory specified earlier, use: .X; where X represents the file extension.

The default properties for defining plagiarism between pairs is:
- `-tfidf`: **TF-IDF**, or sublinear TF, for term weighting;
- `-dice`: **Dice Algorithm** for similarity calculation among pairs;
-  **Otsu Algorithm** for thresholding.

### Alternative arguments

As mentioned before, UPlag provides some extra properties to refine the similarity results:
- `-wfidf`: TF term frequency weighting;
- `-cosine`: Cosine similarity;
- `-t N`: Threshold value, where N is an integer between 0 and 100.


#### :vertical_traffic_light: Example

Running UPlag with the **default commands** to detect similar pairs in C language:

`java -jar UPlag 1.0-SNAPSHOT.jar -d /home/uplag/codes/ -p .c`

Running UPlag with **customized arguments** to detect similar pairs in C language:

`java -jar UPlag 1.0-SNAPSHOT.jar -d /home/uplag/codes/ -wf-idf -cosine -t 60 -p .c`

:rotating_light: The order of some parameters, e.g. -d, -t, and -p, does matter.


## Disclaimer

This project is passing through some refactorings and performance improvements. If you find any bug or fatal error, please do not hesitate to open an **Issue**.
