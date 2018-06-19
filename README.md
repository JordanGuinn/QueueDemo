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

### Executing the Program

Building and executing your standard Java application generally involves compiling all of the program's Java files into corresponding ".class" files, and then bringing all those generated class files into a single executable JAR.  However, for the sake of simplicity, I've gone ahead and done all that for you.

As a result, once you've successfully cloned/downloaded the contents of this repository, running the program is simply a matter of locating and executing said jarfile.  From the root directory of the project:

```
cd out/artifacts/Producer_Consumer_jar/
java -jar Producer-Consumer.jar
```


And there you have it!

## Running the Tests

There are a handful of helpful unit tests associated with this implementation.  In order to run those tests manually from the command line, we'll need to add the directories containing JUnit (as well as our application/test code) to our classpath.  Only then can we specify the unit tests classes to execute.  

For those on a Unix machine, the corresponding commands will look something like this:

```
cd out/artifacts/Producer_Consumer_jar/
java -cp "../../../lib/*:../../production/Producer-Consumer:../../test/Producer-Consumer" org.junit.runner.JUnitCore com.github.JordanGuinn.QueueDemo.concurrent.QueueThreadFactoryTest
java -cp "../../../lib/*:../../production/Producer-Consumer:../../test/Producer-Consumer" org.junit.runner.JUnitCore com.github.JordanGuinn.QueueDemo.model.BoundedQueueTest
```

For those on a Windows machine, they're going to look more like this:

```
cd out/artifacts/Producer_Consumer_jar/
java -cp "../../../lib/*;../../production/Producer-Consumer;../../test/Producer-Consumer" org.junit.runner.JUnitCore com.github.JordanGuinn.QueueDemo.concurrent.QueueThreadFactoryTest
java -cp "../../../lib/*;../../production/Producer-Consumer;../../test/Producer-Consumer" org.junit.runner.JUnitCore com.github.JordanGuinn.QueueDemo.model.BoundedQueueTest
```

## Author

* **Jordan Guinn**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details