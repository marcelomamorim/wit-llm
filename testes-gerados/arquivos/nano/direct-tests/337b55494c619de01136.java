/* @test    @summary Test Collections.forEach with a simple Consumer implementation    @run main CollectionsWitupTest */

import java.util.Arrays;
import java.util.List;
import java.util.Collections;
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
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        list.forEach(consumer);
        assert sb.length() == 0 : "Expected empty StringBuilder for empty list";
    }

    static void testForEachWithSingleElement() {
        List<String> list = Arrays.asList("test");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = (String e) -> sb.append(e);
        list.forEach(consumer);
        assert sb.toString().equals("test") : "Expected 'test', got '" + sb.toString() + "'";
    }

    static void testForEachWithMultipleElements() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        list.forEach(consumer);
        assert sb.toString().equals("123") : "Expected '123', got '" + sb.toString() + "'";
    }
}
