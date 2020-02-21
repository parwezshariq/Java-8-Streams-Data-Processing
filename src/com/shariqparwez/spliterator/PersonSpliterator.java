package com.shariqparwez.spliterator;

import com.shariqparwez.spliterator.model.Person;

import java.util.Spliterator;
import java.util.function.Consumer;

public class PersonSpliterator implements Spliterator<Person> {
    // PersonSpliterator is built upon Spliterator of type String fetched by Stream<Sting> retrieved by reading
    // people.txt file
    private final Spliterator<String> lineSpliterator;

    // Person class fields
    private String name;
    private int age;
    private String city;

    // Constructor to initialize spliterator of type String on which custom spliterator of type People,
    // is being built
    public PersonSpliterator(Spliterator<String> lineSpliterator) {
        this.lineSpliterator = lineSpliterator;
    }

    // This method provides implementation of mapping from stream of lines, to custom object, i.e., people
    @Override
    public boolean tryAdvance(Consumer<? super Person> action) {
        // Check all condition, if for a person object all 3 fields has been set
        if (this.lineSpliterator.tryAdvance(line -> this.name = line) &&
            this.lineSpliterator.tryAdvance(line -> this.age = Integer.parseInt(line)) &&
            this.lineSpliterator.tryAdvance(line -> this.city = line)) {

            // Once all fields has been set, build person object
            Person p = new Person(name, age, city);

            // Accept the completion of Person object by invoking accept method of the consumer
            action.accept(p);

            // On successful creation, return true
            return true;
        } else {
            // If any fields are yet to be set, false should be returned back
            return false;
        }
    }

    // This is used, when we want our stream to be compatible with parallel stream
    @Override
    public Spliterator<Person> trySplit() {
        return null;
    }

    // Size of the object, the stream will be creating.
    // In people.txt file, we have 18 lines, where every 3 line constitute a person info
    @Override
    public long estimateSize() {
        return lineSpliterator.estimateSize() / 3;
    }

    // Characteristic of spliterator
    // Using default implementation of Stream<Sting> spliterator
    @Override
    public int characteristics() {
        return lineSpliterator.characteristics();
    }
}
