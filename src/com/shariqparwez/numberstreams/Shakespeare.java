package com.shariqparwez.numberstreams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Shakespeare {
    public static void main(String[] args) throws IOException {
        // ## 1 -  Introduction of the Scrabble Example
        //dataProcessingStreamOfNumbersOperationOne();

        // ## 2 -  Computing the Score of a Word in Scrabble
        //dataProcessingStreamOfNumbersOperationTwo();

        // ## 3 -  Getting the Word with the Best Score
        //dataProcessingStreamOfNumbersOperationThree();

        // ## 3 -  Computing Statistics on the Words of Shakespeare
        dataProcessingStreamOfNumbersOperationFour();
    }

    private static void dataProcessingStreamOfNumbersOperationOne() throws IOException {
        // Fetch all words from shakespeare file as a set of string
        Set<String> shakespeareWords = Files.lines(Paths.get("src/files/words.shakespeare.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        // Fetch all words allowed in the game of scrabble from ospd file as a set of string
        Set<String> scrabbleWords = Files.lines(Paths.get("src/files/ospd.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        // Display number of words in both the files
        System.out.println("# words of Shakespeare : " + shakespeareWords.size());
        System.out.println("# words of Scrabble : " + scrabbleWords.size());
    }

    private static void dataProcessingStreamOfNumbersOperationTwo() throws IOException {
        // Fetch all words from shakespeare file as a set of string
        Set<String> shakespeareWords = Files.lines(Paths.get("src/files/words.shakespeare.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        // Fetch all words allowed in the game of scrabble from ospd file as a set of string
        Set<String> scrabbleWords = Files.lines(Paths.get("src/files/ospd.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        // Display number of words in both the files
        System.out.println("# words of Shakespeare : " + shakespeareWords.size());
        System.out.println("# words of Scrabble : " + scrabbleWords.size());

        // Create an array which consists of point or scores corresponding to each character of alphabets
        final int[] scrabbleENScore = {
                //  a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y, z
                    1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10} ;

        // Build a function to get score for a word as per scrabbleENScore array
        Function<String, Integer> score =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        // Another way to write function above, this will not require to return the output as boxed into Integer
        ToIntFunction<String> intScore =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        // Test the function by computing the score of word 'hello'
        System.out.println("Score of hello: " + intScore.applyAsInt("hello"));
    }

    private static void dataProcessingStreamOfNumbersOperationThree() throws IOException {
        // Fetch all words from shakespeare file as a set of string
        Set<String> shakespeareWords = Files.lines(Paths.get("src/files/words.shakespeare.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        // Fetch all words allowed in the game of scrabble from ospd file as a set of string
        Set<String> scrabbleWords = Files.lines(Paths.get("src/files/ospd.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        // Display number of words in both the files
        System.out.println("# words of Shakespeare : " + shakespeareWords.size());
        System.out.println("# words of Scrabble : " + scrabbleWords.size());

        // Create an array which consists of point or scores corresponding to each character of alphabets
        final int[] scrabbleENScore = {
                //  a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y, z
                1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10} ;

        // Build a function to get score for a word as per scrabbleENScore array
        Function<String, Integer> score =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        // Another way to write function above, this will not require to return the output as boxed into Integer
        ToIntFunction<String> intScore =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        // Test the function by computing the score of word 'hello'
        System.out.println("Score of hello: " + intScore.applyAsInt("hello"));

        // Fetch the word in shakespeare file with the highest score
        // Filter applied to fetch word which exists in scrabble game
        String bestWord = shakespeareWords.stream()
                .filter(word -> scrabbleWords.contains(word))
                .max(Comparator.comparing(score)).get();

        // Print the highest scoring word from shakespeare file
        System.out.println("Best word: " + bestWord);
    }

    private static void dataProcessingStreamOfNumbersOperationFour() throws IOException {
        // Fetch all words from shakespeare file as a set of string
        Set<String> shakespeareWords = Files.lines(Paths.get("src/files/words.shakespeare.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        // Fetch all words allowed in the game of scrabble from ospd file as a set of string
        Set<String> scrabbleWords = Files.lines(Paths.get("src/files/ospd.txt"))
                .map(word -> word.toLowerCase())
                .collect(Collectors.toSet());

        // Display number of words in both the files
        System.out.println("# words of Shakespeare : " + shakespeareWords.size());
        System.out.println("# words of Scrabble : " + scrabbleWords.size());

        // Create an array which consists of point or scores corresponding to each character of alphabets
        final int[] scrabbleENScore = {
                //  a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y, z
                1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10} ;

        // Build a function to get score for a word as per scrabbleENScore array
        Function<String, Integer> score =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        // Another way to write function above, this will not require to return the output as boxed into Integer
        ToIntFunction<String> intScore =
                word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

        // Test the function by computing the score of word 'hello'
        System.out.println("Score of hello: " + intScore.applyAsInt("hello"));

        // Fetch the word in shakespeare file with the highest score
        // Filter applied to fetch word which exists in scrabble game
        String bestWord = shakespeareWords.stream()
                .filter(word -> scrabbleWords.contains(word))
                .max(Comparator.comparing(score)).get();

        // Print the highest scoring word from shakespeare file
        System.out.println("Best word: " + bestWord);

        // Get intSummaryStatistics on shakespeare words file, by using ToIntFunction
        IntSummaryStatistics summaryStatistics = shakespeareWords.stream()
                .filter(scrabbleWords::contains)
                .mapToInt(intScore)
                .summaryStatistics();

        // Print summary statistics
        System.out.println("Stats: " + summaryStatistics);
    }
}
