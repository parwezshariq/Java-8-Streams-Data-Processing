package com.shariqparwez.spliterator;

import com.shariqparwez.spliterator.model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CreatingSpliterator {
    public static void main(String[] args) {
        // ## 1 -  Setting up the Application
        dataProcessingSpliteratorOperationOne();
    }

    private static void dataProcessingSpliteratorOperationOne() {
        // Build path object to load sample people.txt file
        Path path = Paths.get("src/files/people.txt");

        // Fetch lines of file as steam of string
        try (Stream<String> lines = Files.lines(path);) {
            // Get Spliterator from stream of lines read from file
            Spliterator<String> lineSpliterator = lines.spliterator();

            // Create own custom spliteratot of type Person using spliterator of type string fetched from steam of lines
            // read through file
            Spliterator<Person> peopleSpliterator = new PersonSpliterator(lineSpliterator);

            // Create a stream of custom class, i.e, people by using StreamSupport, steam method
            // Argument used - peopleSpliterator, and false for no usage of parallel stream
            Stream<Person> people = StreamSupport.stream(peopleSpliterator, false);

            // Pipeline a consumer to print all person object built
            people.forEach(System.out::println);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
