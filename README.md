# Bounded Producer-Consumer

Welcome to my demo Producer-Consumer Queue implementation!

## Getting Started

The BoundedQueue class captured here was designed to be both efficient and thread-safe, capable of handling many different Producer and Consumer Threads all acting upon it concurrently.  It was also designed to be blocking, only resolving element insertions/deletions when queue capacity allows for it. 

This README is intended to serve as a simple step-by-step guide to quickly run and test the lightweight application I've built around this implementation.

### Prerequisites

Before moving any further, it should be noted that this project is based exclusively on [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).  If you've already installed the Java 8 JDK, feel free to skip this step.
Otherwise, installation instructions based on operating system can be found [here](https://java.com/en/download/help/download_options.xml).

Confirmation of the correct Java version can be determined with the following command:

```
java -version
```

If all has gone successfully, you should see something like this:

```
java version "1.8.0_60"
Java(TM) SE Runtime Environment (build 1.8.0_60-b27)
Java HotSpot(TM) 64-Bit Server VM (build 25.60-b23, mixed mode)
```
Easy!  Now we're ready to run the application.


## Executing the Program

Building a basic Java application typically involves:
1. Compiling all of the application's Java files into corresponding `.class` files.
2. Collapsing all those generated `.class` files into a single executable `JAR`, complete with a corresponding MANIFEST.  

For the sake of simplicity and expediency, I've preemptively done all that for you.

In order to execute this program, you'll first need to download and unzip this repo.  Then, from the root directory of the project:

```
cd out/artifacts/Producer_Consumer_jar/
java -jar Producer-Consumer.jar
```


Upon running the java command, you should begin noticing insightful console output.  If so, congratulations, you've successfully exercised my ProducerConsumerQueue implementation!

## Running the Tests

In addition to the application itself, there are also a handful of helpful [JUnit](https://junit.org/junit4/) tests associated with this implementation.  In order to run those tests manually from the command line, we'll need to: 
1. Add the directories containing [JUnit](https://junit.org/junit4/), application code and test code to the `CLASSPATH` environment variable.
2. Specify the actual unit test class to execute.  

For those on Unix machines, the commands for this will look something like:

```
cd out/artifacts/Producer_Consumer_jar/
java -cp "../../../lib/*:../../production/Producer-Consumer:../../test/Producer-Consumer" org.junit.runner.JUnitCore com.github.JordanGuinn.QueueDemo.concurrent.QueueThreadFactoryTest
java -cp "../../../lib/*:../../production/Producer-Consumer:../../test/Producer-Consumer" org.junit.runner.JUnitCore com.github.JordanGuinn.QueueDemo.model.BoundedQueueTest
```

And for those on Windows machines, they're going to look more like:

```
cd out/artifacts/Producer_Consumer_jar/
java -cp "../../../lib/*;../../production/Producer-Consumer;../../test/Producer-Consumer" org.junit.runner.JUnitCore com.github.JordanGuinn.QueueDemo.concurrent.QueueThreadFactoryTest
java -cp "../../../lib/*;../../production/Producer-Consumer;../../test/Producer-Consumer" org.junit.runner.JUnitCore com.github.JordanGuinn.QueueDemo.model.BoundedQueueTest
```

If successful, you should see informative console output for each unit test executed.  

And just like that, you've comprehensively tested my ProducerConsumerQueue implementation!

## Author

* **Jordan Guinn**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details