/* @test    @summary Test for Collections.forEachRemaining method to ensure it invokes the Consumer on each element.    @run main CollectionsWitupTest */

import java.util.*;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachRemainingInvokesConsumer();
        testForEachRemainingNullConsumer();
    }

    static void testForEachRemainingInvokesConsumer() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        Iterator<Integer> iterator = list.iterator();
        iterator.forEachRemaining(consumer);
        if (!sb.toString().equals("123")) {
            throw new AssertionError("Expected '123', but got: " + sb.toString());
        }
    }

    static void testForEachRemainingNullConsumer() {
        List<Integer> list = Arrays.asList(4, 5);
        Iterator<Integer> iterator = list.iterator();
        try {
            iterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
