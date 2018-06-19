# Bounded Producer-Consumer

Welcome to my implementation of a bounded Producer-Consumer Queue!

## Getting Started

Something about what this README is supposed to do.

### Prerequisites

This project is based on [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), so you'll need to get that version installed if you haven't already.
Instructions for Java 8 installations based on operating system can be found [here](https://java.com/en/download/help/download_options.xml).

Once installed, feel free to confirm your version of Java with the following command:

```
java -version
```
The output should look something similar to this:

```
java version "1.8.0_60"
Java(TM) SE Runtime Environment (build 1.8.0_60-b27)
Java HotSpot(TM) 64-Bit Server VM (build 25.60-b23, mixed mode)
```

### Running the Program

Building and executing the application is a very straightforward process.  Naturally, we first need to compile all of our java classes, which can be done as follows:

```
javac -sourcepath src/com/github/JordanGuinn/queue -d out BoundedQueueValidation.java
```

This should result in the generation of a new "out" directory, containing all the of compiled classes.  Once this happens, simply run the main jar:

```
cd out
java -jar BoundedQueueValidation.jar
```


And there you have it!

## Running the tests

There are a handful of helpful unit tests associated with this implementation.  Running them all can be done with the following:


```
However you run these tests
```

## Author

* **Jordan Guinn**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details