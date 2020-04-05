package com.shariqparwez.collectors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainUsingCollectors {
    public static void main(String[] args) {
        // ## 1 - Introducing the Shakespeare Plays Scrabble Use Case
        //dataProcessingUsingCollectorsOperationOne();

        // ## 2 - Building Histograms to Extract the Best Words
        //dataProcessingUsingCollectorsOperationTwo();

        /* Writing Whizzing with a Blank Letter */

        // ## 3 - Computing the Number of Blanks Need for Whizzing
        //dataProcessingUsingCollectorsOperationThree();

        // ## 4 - Computing the Score of Words with Blank Letters
        //dataProcessingUsingCollectorsOperationFour();

        // ## 5 - Computing the Best Words with Blanks
        dataProcessingUsingCollectorsOperationFive();
    }

    private static void dataProcessingUsingCollectorsOperationOne() {

        // Build path object for shakespeare.txt and ospd.txt files
        Path shakespearePath = Paths.get("src/files/words.shakespeare.txt");
        Path ospdPath = Paths.get("src/files/ospd.txt");

        // Open try-with resources to read files content and get it as stream of string
        try (Stream<String> ospd = Files.lines(ospdPath);
             Stream<String> shakespeare = Files.lines(shakespearePath);) {
            // Save the content of files as set of strings
            Set<String> scrabbleWords = ospd.collect(Collectors.toSet());
            Set<String> shakespeareWords = shakespeare.collect(Collectors.toSet());

            // Print the number of words fetched from file
            System.out.println("Scrabble:  " + scrabbleWords.size());
            System.out.println("Shakespeare : " + shakespeareWords.size());

            // Score array
            int[] letterScores = {
                    //  a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y, z
                        1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
            };

            // Build function to calculate the score of word
            Function<String, Integer> score = word -> word.toLowerCase().chars()
                    .map(letter -> letterScores[letter - 'a'])
                    .sum();

            // Build map of shakespeare words grouped by score
            Map<Integer, List<String>> histoWordsByScore = shakespeareWords.stream()
                    .collect(Collectors.groupingBy(score));

            // Display the number of words collected
            System.out.println("# histoWordsByScore = " + histoWordsByScore.size());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void dataProcessingUsingCollectorsOperationTwo() {

        // Build path object for shakespeare.txt and ospd.txt files
        Path shakespearePath = Paths.get("src/files/words.shakespeare.txt");
        Path ospdPath = Paths.get("src/files/ospd.txt");

        // Open try-with resources to read files content and get it as stream of string
        try (Stream<String> ospd = Files.lines(ospdPath);
             Stream<String> shakespeare = Files.lines(shakespearePath);) {
            // Save the content of files as set of strings
            Set<String> scrabbleWords = ospd.collect(Collectors.toSet());
            Set<String> shakespeareWords = shakespeare.collect(Collectors.toSet());

            // Print the number of words fetched from file
            System.out.println("Scrabble:  " + scrabbleWords.size());
            System.out.println("Shakespeare : " + shakespeareWords.size());

            // Score array
            int[] letterScores = {
                    //  a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y, z
                        1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
            };

            // Build function to calculate the score of word
            Function<String, Integer> score = word -> word.toLowerCase().chars()
                    .map(letter -> letterScores[letter - 'a'])
                    .sum();

            // Build map of shakespeare words grouped by score
            // Filter appiled to get words present in scrabble words list
            Map<Integer, List<String>> histoWordsByScore = shakespeareWords.stream()
                    .filter(scrabbleWords::contains)
                    .collect(Collectors.groupingBy(score));

            // Display the number of words collected
            System.out.println("# histoWordsByScore = " + histoWordsByScore.size());

            // Build histogram of scores
            // Sorted descending order in terms of scores
            // Return the 3 highest scores
            histoWordsByScore.entrySet().stream()
                    .sorted(Comparator.comparing(entry -> -entry.getKey()))
                    .limit(3)
                    .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void dataProcessingUsingCollectorsOperationThree() {

        // Build path object for shakespeare.txt and ospd.txt files
        Path shakespearePath = Paths.get("src/files/words.shakespeare.txt");
        Path ospdPath = Paths.get("src/files/ospd.txt");

        // Open try-with resources to read files content and get it as stream of string
        try (Stream<String> ospd = Files.lines(ospdPath);
             Stream<String> shakespeare = Files.lines(shakespearePath);) {
            // Save the content of files as set of strings
            Set<String> scrabbleWords = ospd.collect(Collectors.toSet());
            Set<String> shakespeareWords = shakespeare.collect(Collectors.toSet());

            // Print the number of words fetched from file
            System.out.println("Scrabble:  " + scrabbleWords.size());
            System.out.println("Shakespeare : " + shakespeareWords.size());

            // Score array
            int[] letterScores = {
                    //  a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y, z
                        1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
            };

            // Build function to calculate the score of word
            Function<String, Integer> score = word -> word.toLowerCase().chars()
                    .map(letter -> letterScores[letter - 'a'])
                    .sum();

            // Build map of shakespeare words grouped by score
            // Filter appiled to get words present in scrabble words list
            Map<Integer, List<String>> histoWordsByScore = shakespeareWords.stream()
                    .filter(scrabbleWords::contains)
                    .collect(Collectors.groupingBy(score));

            // Display the number of words collected
            System.out.println("# histoWordsByScore = " + histoWordsByScore.size());

            // Build histogram of scores
            // Sorted descending order in terms of scores
            // Return the 3 highest scores
            histoWordsByScore.entrySet().stream()
                    .sorted(Comparator.comparing(entry -> -entry.getKey()))
                    .limit(3)
                    .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));

            // Scrabble ENDistribution array
            int[] scrabbleENDistribution = {
                    //  a, b, c, d, e,  f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
                        9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
            };

            // Build a function to generate histogram of words by character
            Function<String, Map<Integer, Long>> histoWord = word -> word.chars().boxed()
                    .collect(Collectors.groupingBy(letter -> letter, Collectors.counting()));

            // Build a function to compute number of blanks required for a particular word
            Function<String, Long> nBlanks =
                    word -> histoWord.apply(word)
                            .entrySet()
                            .stream()
                            .mapToLong(entry -> Long.max(
                                    entry.getValue() - (long)scrabbleENDistribution[entry.getKey() - 'a'], 0L))
                            .sum();

            // Apply the function and print the number of blanks
            System.out.println("# of blanks for whizzing : " +  nBlanks.apply("whizzing"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void dataProcessingUsingCollectorsOperationFour() {

        // Build path object for shakespeare.txt and ospd.txt files
        Path shakespearePath = Paths.get("src/files/words.shakespeare.txt");
        Path ospdPath = Paths.get("src/files/ospd.txt");

        // Open try-with resources to read files content and get it as stream of string
        try (Stream<String> ospd = Files.lines(ospdPath);
             Stream<String> shakespeare = Files.lines(shakespearePath);) {
            // Save the content of files as set of strings
            Set<String> scrabbleWords = ospd.collect(Collectors.toSet());
            Set<String> shakespeareWords = shakespeare.collect(Collectors.toSet());

            // Print the number of words fetched from file
            System.out.println("Scrabble:  " + scrabbleWords.size());
            System.out.println("Shakespeare : " + shakespeareWords.size());

            // Score array
            int[] letterScores = {
                    //  a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y, z
                    1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
            };

            // Build function to calculate the score of word
            Function<String, Integer> score = word -> word.toLowerCase().chars()
                    .map(letter -> letterScores[letter - 'a'])
                    .sum();

            // Build map of shakespeare words grouped by score
            // Filter appiled to get words present in scrabble words list
            Map<Integer, List<String>> histoWordsByScore = shakespeareWords.stream()
                    .filter(scrabbleWords::contains)
                    .collect(Collectors.groupingBy(score));

            // Display the number of words collected
            System.out.println("# histoWordsByScore = " + histoWordsByScore.size());

            // Build histogram of scores
            // Sorted descending order in terms of scores
            // Return the 3 highest scores
            histoWordsByScore.entrySet().stream()
                    .sorted(Comparator.comparing(entry -> -entry.getKey()))
                    .limit(3)
                    .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));

            // Scrabble ENDistribution array
            int[] scrabbleENDistribution = {
                    //  a, b, c, d, e,  f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
                    9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
            };

            // Build a function to generate histogram of words by character
            Function<String, Map<Integer, Long>> histoWord = word -> word.chars().boxed()
                    .collect(Collectors.groupingBy(letter -> letter, Collectors.counting()));

            // Build a function to compute number of blanks required for a particular word
            Function<String, Long> nBlanks =
                    word -> histoWord.apply(word)
                            .entrySet()
                            .stream()
                            .mapToLong(entry -> Long.max(
                                    entry.getValue() - (long)scrabbleENDistribution[entry.getKey() - 'a'], 0L))
                            .sum();

            // Apply the function and print the number of blanks
            System.out.println("# of blanks for whizzing : " +  nBlanks.apply("whizzing"));

            // Build the function to get the minimum value computed
            Function<String, Integer> scoreTwo = word -> histoWord.apply(word)
                    .entrySet()
                    .stream()
                    .mapToInt(entry -> letterScores[entry.getKey() - 'a'] *
                            Integer.min(entry.getValue().intValue(), scrabbleENDistribution[entry.getKey() - 'a']))
                    .sum();

            // Print the output of score and scoreTwo function
            System.out.println("# score for whizzing : " + score.apply("whizzing"));
            System.out.println("# scoreTwo for whizzing : " + scoreTwo.apply("whizzing"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void dataProcessingUsingCollectorsOperationFive() {

        // Build path object for shakespeare.txt and ospd.txt files
        Path shakespearePath = Paths.get("src/files/words.shakespeare.txt");
        Path ospdPath = Paths.get("src/files/ospd.txt");

        // Open try-with resources to read files content and get it as stream of string
        try (Stream<String> ospd = Files.lines(ospdPath);
             Stream<String> shakespeare = Files.lines(shakespearePath);) {
            // Save the content of files as set of strings
            Set<String> scrabbleWords = ospd.collect(Collectors.toSet());
            Set<String> shakespeareWords = shakespeare.collect(Collectors.toSet());

            // Print the number of words fetched from file
            System.out.println("Scrabble:  " + scrabbleWords.size());
            System.out.println("Shakespeare : " + shakespeareWords.size());

            // Score array
            int[] letterScores = {
                    //  a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y, z
                    1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
            };

            // Build function to calculate the score of word
            Function<String, Integer> score = word -> word.toLowerCase().chars()
                    .map(letter -> letterScores[letter - 'a'])
                    .sum();

            // Build map of shakespeare words grouped by score
            // Filter appiled to get words present in scrabble words list
            Map<Integer, List<String>> histoWordsByScore = shakespeareWords.stream()
                    .filter(scrabbleWords::contains)
                    .collect(Collectors.groupingBy(score));

            // Display the number of words collected
            System.out.println("# histoWordsByScore = " + histoWordsByScore.size());

            // Build histogram of scores
            // Sorted descending order in terms of scores
            // Return the 3 highest scores
            histoWordsByScore.entrySet().stream()
                    .sorted(Comparator.comparing(entry -> -entry.getKey()))
                    .limit(3)
                    .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));

            // Scrabble ENDistribution array
            int[] scrabbleENDistribution = {
                    //  a, b, c, d, e,  f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
                    9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
            };

            // Build a function to generate histogram of words by character
            Function<String, Map<Integer, Long>> histoWord = word -> word.chars().boxed()
                    .collect(Collectors.groupingBy(letter -> letter, Collectors.counting()));

            // Build a function to compute number of blanks required for a particular word
            Function<String, Long> nBlanks =
                    word -> histoWord.apply(word)
                            .entrySet()
                            .stream()
                            .mapToLong(entry -> Long.max(
                                    entry.getValue() - (long)scrabbleENDistribution[entry.getKey() - 'a'], 0L))
                            .sum();

            // Apply the function and print the number of blanks
            System.out.println("# of blanks for whizzing : " +  nBlanks.apply("whizzing"));

            // Build the function to get the minimum value computed
            Function<String, Integer> scoreTwo = word -> histoWord.apply(word)
                    .entrySet()
                    .stream()
                    .mapToInt(entry -> letterScores[entry.getKey() - 'a'] *
                            Integer.min(entry.getValue().intValue(), scrabbleENDistribution[entry.getKey() - 'a']))
                    .sum();

            // Print the output of score and scoreTwo function
            System.out.println("# score for whizzing : " + score.apply("whizzing"));
            System.out.println("# scoreTwo for whizzing : " + scoreTwo.apply("whizzing"));

            // Compute the best words with blanks
            shakespeareWords.stream()
                    .filter(scrabbleWords::contains)
                    .filter(word -> nBlanks.apply(word) <= 2)
                    .collect(Collectors.groupingBy(scoreTwo))
                    .entrySet().stream()
                    .sorted(Comparator.comparing(entry -> -entry.getKey()))
                    .limit(3)
                    .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
