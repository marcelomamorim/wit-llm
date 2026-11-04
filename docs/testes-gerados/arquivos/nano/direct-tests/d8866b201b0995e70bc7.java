/*
 * @test
 * @summary Test Collections.forEach method for correct invocation of Consumer
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachInvokesConsumer();
        testForEachWithEmptyList();
        testForEachWithNullConsumer();
    }

    static void testForEachInvokesConsumer() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        Collections.forEach(list, consumer);
        assert sb.toString().equals("123") : "Expected '123', but got '" + sb.toString() + "'";
    }

    static void testForEachWithEmptyList() {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = (String s) -> sb.append(s);
        Collections.forEach(list, consumer);
        assert sb.toString().isEmpty() : "Expected empty string, but got '" + sb.toString() + "'";
    }

    static void testForEachWithNullConsumer() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        try {
            Collections.forEach(list, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
