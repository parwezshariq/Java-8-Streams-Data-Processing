package com.shariqparwez.customcollectors;

import com.shariqparwez.customcollectors.model.Actor;
import com.shariqparwez.customcollectors.model.Movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainMoviesActors {
    public static void main(String[] args) throws IOException {
        // ## 1 -  Presenting the Actors and Movies Use Case
        //customCollectorsOperationOne();

        // ## 2 -  Finding the Actor That Played in the Most Movies
        //customCollectorsOperationTwo();

        // ## 3 -  Adding the Year Constraint, Introduction
        customCollectorsOperationThree();
    }

    private static void customCollectorsOperationOne() throws IOException {
        // Initialize set to hold movies
        Set<Movie> movies = new HashSet<>();

        // Read the movies-mpaa.txt file and get the content as Stream of string
        Stream<String> lines = Files.lines(Paths.get("src/files", "movies-mpaa.txt"));

        // Process the stream and build movies and actors data
        lines.forEach((String line) -> {
            String[] elements = line.split("/");
            String title = elements[0].substring(0, elements[0].lastIndexOf("(")).trim();
            String releaseYear = elements[0].substring(elements[0].lastIndexOf("(") + 1, elements[0].lastIndexOf(")"));

            if (releaseYear.contains(",")) {
                // with skip movies with a comma in their title
                return;
            }

            Movie movie = new Movie(title, Integer.valueOf(releaseYear));

            for (int i=1; i <elements.length; i++) {
                String[] name = elements[i].split(", ");
                String lastName = name[0].trim();
                String firstName = "";
                if (name.length > 1) {
                    firstName = name[1].trim();
                }

                Actor actor = new Actor(lastName, firstName);
                movie.addActor(actor);
            }
            movies.add(movie);
        });

        // Print number of movies
        System.out.println("# movies = " + movies.size());

        // Extract number of actors and print it
        long numberOfActors = movies.stream().flatMap(movie -> movie.actors().stream())
                .distinct().count();
        System.out.println("# of actors : " + numberOfActors);

    }

    private static void customCollectorsOperationTwo() throws IOException {
        // Initialize set to hold movies
        Set<Movie> movies = new HashSet<>();

        // Read the movies-mpaa.txt file and get the content as Stream of string
        Stream<String> lines = Files.lines(Paths.get("src/files", "movies-mpaa.txt"));

        // Process the stream and build movies and actors data
        lines.forEach((String line) -> {
            String[] elements = line.split("/");
            String title = elements[0].substring(0, elements[0].lastIndexOf("(")).trim();
            String releaseYear = elements[0].substring(elements[0].lastIndexOf("(") + 1, elements[0].lastIndexOf(")"));

            if (releaseYear.contains(",")) {
                // with skip movies with a comma in their title
                return;
            }

            Movie movie = new Movie(title, Integer.valueOf(releaseYear));

            for (int i=1; i <elements.length; i++) {
                String[] name = elements[i].split(", ");
                String lastName = name[0].trim();
                String firstName = "";
                if (name.length > 1) {
                    firstName = name[1].trim();
                }

                Actor actor = new Actor(lastName, firstName);
                movie.addActor(actor);
            }
            movies.add(movie);
        });

        // Print number of movies
        System.out.println("# movies = " + movies.size());

        // Extract number of actors and print it
        long numberOfActors = movies.stream().flatMap(movie -> movie.actors().stream())
                .distinct().count();
        System.out.println("# of actors : " + numberOfActors);

        // Extract number of actors that played in the greatest number of movies
        Map.Entry<Actor, Long> mostViewedActor = movies.stream().flatMap(movie -> movie.actors().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()).get();
        System.out.println("Most viewed actor : " + mostViewedActor);
    }

    private static void customCollectorsOperationThree() throws IOException {
        // Initialize set to hold movies
        Set<Movie> movies = new HashSet<>();

        // Read the movies-mpaa.txt file and get the content as Stream of string
        Stream<String> lines = Files.lines(Paths.get("src/files", "movies-mpaa.txt"));

        // Process the stream and build movies and actors data
        lines.forEach((String line) -> {
            String[] elements = line.split("/");
            String title = elements[0].substring(0, elements[0].lastIndexOf("(")).trim();
            String releaseYear = elements[0].substring(elements[0].lastIndexOf("(") + 1, elements[0].lastIndexOf(")"));

            if (releaseYear.contains(",")) {
                // with skip movies with a comma in their title
                return;
            }

            Movie movie = new Movie(title, Integer.valueOf(releaseYear));

            for (int i=1; i <elements.length; i++) {
                String[] name = elements[i].split(", ");
                String lastName = name[0].trim();
                String firstName = "";
                if (name.length > 1) {
                    firstName = name[1].trim();
                }

                Actor actor = new Actor(lastName, firstName);
                movie.addActor(actor);
            }
            movies.add(movie);
        });

        // Print number of movies
        System.out.println("# movies = " + movies.size());

        // Extract number of actors and print it
        long numberOfActors = movies.stream().flatMap(movie -> movie.actors().stream())
                .distinct().count();
        System.out.println("# of actors : " + numberOfActors);

        // Extract number of actors that played in the greatest number of movies
        Map.Entry<Actor, Long> mostViewedActor = movies.stream().flatMap(movie -> movie.actors().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()).get();
        System.out.println("Most viewed actor : " + mostViewedActor);
        
        // Find the actor that played in the greatest number of movies during a year
        // Map<release years, Map<Actor, # of movies during that year>>
        Map.Entry<Integer, Map.Entry<Actor, AtomicLong>> get =
                movies.stream()
                        .collect(
                                Collectors.groupingBy(
                                        movie -> movie.releaseYear(),
                                        Collector.of(
                                                () -> new HashMap<Actor, AtomicLong>(),// supplier
                                                (map, movie) -> {
                                                    movie.actors().forEach(
                                                            actor -> map.computeIfAbsent(actor, a -> new AtomicLong()).incrementAndGet()
                                                    );
                                                }, // accumulator
                                                (map1, map2) -> {
                                                    map2.entrySet().forEach(
                                                            entry2 -> map1.merge(
                                                                    entry2.getKey(), entry2.getValue(),
                                                                    (al1, al2) -> {
                                                                        al1.addAndGet(al2.get());
                                                                        return al1;
                                                                    }
                                                            )
                                                    );
                                                    return map1;
                                                }, // combiner
                                                Collector.Characteristics.IDENTITY_FINISH
                                        )
                                )
                        ) // Map<Integer, HashMap<Actor, AtomicLong>>
                        .entrySet().stream()
                        .collect(
                                Collectors.toMap(
                                        entry -> entry.getKey(),
                                        entry -> entry.getValue().entrySet().stream()
                                                .max(
                                                        Map.Entry.comparingByValue(Comparator.comparing(l -> l.get()))
                                                )
                                                .get()
                                )
                        ) // Map<Integer, Map.Entry<Actor, AtomicLong>>
                        .entrySet().stream()
                        .max(
                                Map.Entry.comparingByValue(
                                        Comparator.comparing(
                                                entry -> entry.getValue().get()
                                        )
                                )
                        )
                        .get();
        // Map.Entry<Integer, Map.Entry<Actor, AtomicLong>>
        System.out.println(get);
    }
}
