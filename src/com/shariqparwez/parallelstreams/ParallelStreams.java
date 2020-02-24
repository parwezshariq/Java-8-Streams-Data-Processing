package com.shariqparwez.parallelstreams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreams {
    public static void main(String[] args) throws IOException {
        // ## 1 -  Understanding the Multithreaded Computation
        //dataProcessingParallelStreamsOperationOne();

        // ## 2 -  Parallel Reduction, How Can It Go Wrong?
        //dataProcessingParallelStreamsOperationTwo();

        // ## 3 - Parallel Reduction, Hint at the Collector Pattern
        dataProcessingParallelStreamsOperationThree();
    }

    private static void dataProcessingParallelStreamsOperationOne() {
        // Set number of threads that should be used by stream to process the given operation
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");

        // Create a stream to process an operation which simply creates a string by appending '+'
        // This stream is parallel stream, order of execution is not maintained.
        Stream.iterate("+", s -> s + "+")
                .parallel()
                .limit(6)
                .peek(s -> System.out.println(s + " processed in the thread " + Thread.currentThread().getName()))
                .forEach(System.out::println);
    }

    private static void dataProcessingParallelStreamsOperationTwo() {
        // Set number of threads that should be used by stream to process the given operation
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");

        // Simple ArrayList
        //List<String> strings = new ArrayList<>();

        // Use of concurrent aware array list
        List<String> strings = new CopyOnWriteArrayList<>();

        // Create a stream to process an operation which simply creates a string by appending '+'
        // This stream is parallel stream, order of execution is not maintained.
        // Appended strings are added into arrayList
        Stream.iterate("+", s -> s + "+")
                .parallel()
                .limit(1000)
                .forEach(s -> strings.add(s));

        // When using arrayList size is any random number but not 1000
        // When using CopyOnWriteArrayList, size is correct i.e. 1000
        System.out.println("# " + strings.size());
    }

    private static void dataProcessingParallelStreamsOperationThree() {
        // Set number of threads that should be used by stream to process the given operation
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");

        // Create a stream to process an operation which simply creates a string by appending '+'
        // This stream is parallel stream
        // Appended strings are added into arrayList
        List<String> collect = Stream.iterate("+", s -> s + "+")
                .parallel()
                .limit(1000)
                .collect(Collectors.toList());

        // Display size of list
        System.out.println("# " + collect.size());
    }
}
