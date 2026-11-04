/* @test    @summary Test for Collections.forEachRemaining method to ensure it executes consumer on the remaining element.    @run main CollectionsWitupTest */

import java.util.*;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachRemainingExecutesConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingExecutesConsumer() {
        List<String> list = Arrays.asList("a", "b", "c");
        Iterator<String> iterator = list.iterator();
        StringBuilder result = new StringBuilder();
        if (iterator.hasNext()) {
            iterator.forEachRemaining(s -> result.append(s));
        }
        assert "abc".equals(result.toString()) : "Expected 'abc', got '" + result.toString() + "'";
    }

    static void testForEachRemainingWithNullConsumer() {
        List<String> list = Arrays.asList("x");
        Iterator<String> iterator = list.iterator();
        boolean exceptionThrown = false;
        try {
            iterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException was expected when passing null";
    }
}
