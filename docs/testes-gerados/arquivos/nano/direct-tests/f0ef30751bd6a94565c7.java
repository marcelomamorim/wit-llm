/*
 * @test
 * @summary Test Collections.forEach with a simple Consumer implementation
 * @run main CollectionsWitupTest
 */

import java.util.*;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachWithEmptyList();
        testForEachWithSingleElement();
        testForEachWithMultipleElements();
    }

    static void testForEachWithEmptyList() {
        List<Integer> list = Collections.emptyList();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        list.forEach(consumer);
        assert sb.length() == 0 : "Expected empty StringBuilder for empty list";
    }

    static void testForEachWithSingleElement() {
        List<String> list = Collections.singletonList("a");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = (String s) -> sb.append(s);
        list.forEach(consumer);
        assert sb.toString().equals("a") : "Expected 'a', got '" + sb.toString() + "'";
    }

    static void testForEachWithMultipleElements() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        list.forEach(consumer);
        assert sb.toString().equals("123") : "Expected '123', got '" + sb.toString() + "'";
    }
}
