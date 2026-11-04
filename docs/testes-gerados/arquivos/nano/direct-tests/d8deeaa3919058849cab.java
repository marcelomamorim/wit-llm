/* @test    @summary Test Collections.forEach method for null and normal behavior    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachWithNull();
        testForEachWithEmptyList();
        testForEachWithSingleElement();
        testForEachWithMultipleElements();
    }

    static void testForEachWithNull() {
        try {
            Collections.forEach(null);
            System.err.println("Failed: Expected NullPointerException for null Consumer");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testForEachWithEmptyList() {
        List<Integer> emptyList = Arrays.asList();
        final boolean[] called = {false};
        Consumer<Integer> consumer = (Integer e) -> { called[0] = true; };
        Collections.forEach(emptyList, consumer);
        if (called[0]) {
            System.out.println("Passed: forEach on empty list does not call consumer");
        } else {
            System.err.println("Failed: Consumer was not called on empty list");
        }
    }

    static void testForEachWithSingleElement() {
        List<String> list = Arrays.asList("test");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = (String e) -> sb.append(e);
        Collections.forEach(list, consumer);
        if ("test".equals(sb.toString())) {
            System.out.println("Passed: forEach on single element list");
        } else {
            System.err.println("Failed: Unexpected result for single element list");
        }
    }

    static void testForEachWithMultipleElements() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        final StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        Collections.forEach(list, consumer);
        if ("123".equals(sb.toString())) {
            System.out.println("Passed: forEach on multiple elements list");
        } else {
            System.err.println("Failed: Unexpected result for multiple elements list");
        }
    }
}
