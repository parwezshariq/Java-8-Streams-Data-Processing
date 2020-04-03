package com.shariqparwez.optionals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainUsingOptional {
    public static void main(String[] args) {
        // ## 1 - How Not to Process Doubles with NewMath
        //buildingPipelinesWithOptionalsOperationOne();

        // ## 2 - Failing to Go Parallel with the Wrong Pattern
        //buildingPipelinesWithOptionalsOperationTwo();

        // ## 3 - Building an Optional flatMapper for NewMath
        //buildingPipelinesWithOptionalsOperationThree();

        // ## 4 - Building an Optional flatMapper for NewMath with reduced data
        buildingPipelinesWithOptionalsOperationFour();
    }

    private static void buildingPipelinesWithOptionalsOperationOne() {
        // List to hold result
        List<Double> result = new ArrayList<>();

        // Build a stream of doubles (random values),
        // Process the stream to find out inverse
        // Further process to find the square root
        ThreadLocalRandom.current()
                .doubles(10_000)
                .boxed()
                .forEach(d -> NewMath.inv(d)
                .ifPresent(inv -> NewMath.sqrt(inv)
                        .ifPresent(sqrt -> result.add(sqrt))));

        // Print number of results retrieved
        System.out.println("# result = " + result.size());
    }

    private static void buildingPipelinesWithOptionalsOperationTwo() {
        // List to hold result
        List<Double> result = new ArrayList<>();

        // Build a stream of doubles (random values),
        // Process the stream in parallel to find out inverse
        // Further process to find the square root
        ThreadLocalRandom.current()
                .doubles(10_000)
                .boxed()
                .parallel()
                .forEach(d -> NewMath.inv(d)
                        .ifPresent(inv -> NewMath.sqrt(inv)
                                .ifPresent(sqrt -> result.add(sqrt))));

        // Print number of results retrieved
        System.out.println("# result = " + result.size());
    }

    private static void buildingPipelinesWithOptionalsOperationThree() {
        // Build a function to get a stream of double having result of inverse and square root operation
        Function<Double, Stream<Double>> flatMapper = d -> NewMath.inv(d)
                .flatMap(inv -> NewMath.sqrt(inv))
                .map(sqrt -> Stream.of(sqrt))
                .orElseGet(() -> Stream.empty());

        // Get the result using flat map
        List<Double> rightResult = ThreadLocalRandom.current()
                .doubles(10_000)
                .parallel()
                .boxed()
                .flatMap(flatMapper)
                .collect(Collectors.toList());

        // Print the size of the result list
        System.out.println("# rightResult = " + rightResult.size());
    }

    private static void buildingPipelinesWithOptionalsOperationFour() {
        // Build a function to get a stream of double having result of inverse and square root operation
        Function<Double, Stream<Double>> flatMapper = d -> NewMath.inv(d)
                .flatMap(inv -> NewMath.sqrt(inv))
                .map(sqrt -> Stream.of(sqrt))
                .orElseGet(() -> Stream.empty());

        // Get the result using flat map
        // Use map to reduce number of doubles
        List<Double> rightResult = ThreadLocalRandom.current()
                .doubles(10_000)
                .parallel()
                .map(d -> d * 20 - 10)
                .boxed()
                .flatMap(flatMapper)
                .collect(Collectors.toList());

        // Print the size of the result list
        System.out.println("# rightResult = " + rightResult.size());
    }
}
