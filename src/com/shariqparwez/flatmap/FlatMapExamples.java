package com.shariqparwez.flatmap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FlatMapExamples {
    public static void main(String[] args) throws IOException {
        // ## 1 -  Presentation of the Example
        //dataProcessingFlatMapOperationOne();

        // ## 2 -  Building Stream of streams Using Stream.of
        //dataProcessingFlatMapOperationTwo();

        // ## 3 -  Merging Streams Using Stream.of and flatMap
        //dataProcessingFlatMapOperationThree();

        // ## 4 -  Merging Streams and converting the result into stream of words (from stream of lines)
        //dataProcessingFlatMapOperationFour();

        // ## 5 -  Modify above created stream of words to consist of only distinct words
        //dataProcessingFlatMapOperationFive();

        // ## 6 -  Modify above created stream of words to consist of only four letter words
        dataProcessingFlatMapOperationSix();
    }

    private static void dataProcessingFlatMapOperationOne() throws IOException {
        // Build stream of files to be processed
        Stream<String> stream1 = Files.lines(Paths.get("src/files/TomSawyer_01.txt"));
        Stream<String> stream2 = Files.lines(Paths.get("src/files/TomSawyer_02.txt"));
        Stream<String> stream3 = Files.lines(Paths.get("src/files/TomSawyer_03.txt"));
        Stream<String> stream4 = Files.lines(Paths.get("src/files/TomSawyer_04.txt"));

        // Print number of lines in each file
        System.out.println("Stream 1 : " + stream1.count());
        System.out.println("Stream 2 : " + stream2.count());
        System.out.println("Stream 3 : " + stream3.count());
        System.out.println("Stream 4 : " + stream4.count());
    }

    private static void dataProcessingFlatMapOperationTwo() throws IOException {
        // Build stream of files to be processed
        Stream<String> stream1 = Files.lines(Paths.get("src/files/TomSawyer_01.txt"));
        Stream<String> stream2 = Files.lines(Paths.get("src/files/TomSawyer_02.txt"));
        Stream<String> stream3 = Files.lines(Paths.get("src/files/TomSawyer_03.txt"));
        Stream<String> stream4 = Files.lines(Paths.get("src/files/TomSawyer_04.txt"));

        // Print number of lines in each file
        System.out.println("Stream 1 : " + stream1.count());
        System.out.println("Stream 2 : " + stream2.count());
        System.out.println("Stream 3 : " + stream3.count());
        System.out.println("Stream 4 : " + stream4.count());

        // Build streamOfStreams using above streams
        Stream<Stream<String>> streamOfStreams = Stream.of(stream1, stream2, stream3, stream4);

        // Display number of streams in the streamOfStreams
        System.out.println("# streams: " + streamOfStreams.count());
    }

    private static void dataProcessingFlatMapOperationThree() throws IOException {
        // Build stream of files to be processed
        Stream<String> stream1 = Files.lines(Paths.get("src/files/TomSawyer_01.txt"));
        Stream<String> stream2 = Files.lines(Paths.get("src/files/TomSawyer_02.txt"));
        Stream<String> stream3 = Files.lines(Paths.get("src/files/TomSawyer_03.txt"));
        Stream<String> stream4 = Files.lines(Paths.get("src/files/TomSawyer_04.txt"));

        // Build streamOfStreams using above streams
        Stream<Stream<String>> streamOfStreams = Stream.of(stream1, stream2, stream3, stream4);

        // Merge and build stream of string consisting of lines of all streams
        //Stream<String> streamOfLines = streamOfStreams.flatMap(stream -> stream);
        // Can also be written as Function.identity
        Stream<String> streamOfLines = streamOfStreams.flatMap(Function.identity());

        // Display total number of lines
        System.out.println("# lines " + streamOfLines.count());
    }

    private static void dataProcessingFlatMapOperationFour() throws IOException {
        // Build stream of files to be processed
        Stream<String> stream1 = Files.lines(Paths.get("src/files/TomSawyer_01.txt"));
        Stream<String> stream2 = Files.lines(Paths.get("src/files/TomSawyer_02.txt"));
        Stream<String> stream3 = Files.lines(Paths.get("src/files/TomSawyer_03.txt"));
        Stream<String> stream4 = Files.lines(Paths.get("src/files/TomSawyer_04.txt"));

        // Build streamOfStreams using above streams
        Stream<Stream<String>> streamOfStreams = Stream.of(stream1, stream2, stream3, stream4);

        // Merge and build stream of string consisting of lines of all streams
        Stream<String> streamOfLines = streamOfStreams.flatMap(Function.identity());

        // Build a function to return stream of words (line split with space character)
        Function<String, Stream<String>> lineSplitter = line -> Pattern.compile(" ").splitAsStream(line);

        // Merge all the stream of words into stream of string using flatmap, and linesplitter function
        Stream<String> streamOfWords = streamOfLines.flatMap(lineSplitter);

        // Display number of words
        System.out.println("# words :" + streamOfWords.count());

    }

    private static void dataProcessingFlatMapOperationFive() throws IOException {
        // Build stream of files to be processed
        Stream<String> stream1 = Files.lines(Paths.get("src/files/TomSawyer_01.txt"));
        Stream<String> stream2 = Files.lines(Paths.get("src/files/TomSawyer_02.txt"));
        Stream<String> stream3 = Files.lines(Paths.get("src/files/TomSawyer_03.txt"));
        Stream<String> stream4 = Files.lines(Paths.get("src/files/TomSawyer_04.txt"));

        // Build streamOfStreams using above streams
        Stream<Stream<String>> streamOfStreams = Stream.of(stream1, stream2, stream3, stream4);

        // Merge and build stream of string consisting of lines of all streams
        Stream<String> streamOfLines = streamOfStreams.flatMap(Function.identity());

        // Build a function to return stream of words (line split with space character)
        Function<String, Stream<String>> lineSplitter = line -> Pattern.compile(" ").splitAsStream(line);

        // Merge all the stream of words into stream of string using flatmap, and linesplitter function
        // Get stream of distinct words only (no duplicates)
        Stream<String> streamOfWords = streamOfLines.flatMap(lineSplitter)
                .map(word -> word.toLowerCase())
                .distinct();

        // Display number of words
        System.out.println("# words :" + streamOfWords.count());

    }

    private static void dataProcessingFlatMapOperationSix() throws IOException {
        // Build stream of files to be processed
        Stream<String> stream1 = Files.lines(Paths.get("src/files/TomSawyer_01.txt"));
        Stream<String> stream2 = Files.lines(Paths.get("src/files/TomSawyer_02.txt"));
        Stream<String> stream3 = Files.lines(Paths.get("src/files/TomSawyer_03.txt"));
        Stream<String> stream4 = Files.lines(Paths.get("src/files/TomSawyer_04.txt"));

        // Build streamOfStreams using above streams
        Stream<Stream<String>> streamOfStreams = Stream.of(stream1, stream2, stream3, stream4);

        // Merge and build stream of string consisting of lines of all streams
        Stream<String> streamOfLines = streamOfStreams.flatMap(Function.identity());

        // Build a function to return stream of words (line split with space character)
        Function<String, Stream<String>> lineSplitter = line -> Pattern.compile(" ").splitAsStream(line);

        // Merge all the stream of words into stream of string using flatmap, and linesplitter function
        // Get stream of distinct words only (no duplicates)
        Stream<String> streamOfWords = streamOfLines.flatMap(lineSplitter)
                .map(word -> word.toLowerCase())
                .filter(word -> word.length() == 4)
                .distinct();

        // Display number of words
        System.out.println("# words :" + streamOfWords.count());

    }
}
