/*
 * @test
 * @summary Test for java.util.Collections.forEachRemaining method
 * @run main CollectionsWitupTest
 */

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNonEmptyCollection();
        testForEachRemainingWithEmptyCollection();
    }

    static void testForEachRemainingWithNonEmptyCollection() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        Iterator<String> iterator = list.iterator();
        List<String> collected = new ArrayList<>();
        Consumer<String> consumer = collected::add;
        // Advance iterator once
        if (iterator.hasNext()) {
            iterator.next();
        }
        // Call forEachRemaining
        Collections.forEachRemaining(iterator, consumer);
        // Verify that remaining elements are collected
        assert collected.size() == 2 : "Expected 2 elements, got " + collected.size();
        assert collected.contains("two") : "Missing 'two'";
        assert collected.contains("three") : "Missing 'three'";
    }

    static void testForEachRemainingWithEmptyCollection() {
        List<String> list = new ArrayList<>();
        Iterator<String> iterator = list.iterator();
        List<String> collected = new ArrayList<>();
        Consumer<String> consumer = collected::add;
        // Call forEachRemaining on empty iterator
        Collections.forEachRemaining(iterator, consumer);
        // Verify no elements collected
        assert collected.isEmpty() : "Expected empty collection";
    }
}
