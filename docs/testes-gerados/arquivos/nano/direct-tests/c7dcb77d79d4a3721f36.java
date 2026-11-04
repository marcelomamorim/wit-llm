/* @test    @summary Test for ArrayList's forEachRemaining method to ensure it correctly applies the Consumer to remaining elements.    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyList();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithElements() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = e -> result.append(e);
        list.forEachRemaining(consumer);
        assert result.toString().equals("123") : "Expected '123', got '" + result.toString() + "'";
    }

    static void testForEachRemainingWithEmptyList() {
        ArrayList<Integer> list = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = e -> result.append(e);
        list.forEachRemaining(consumer);
        assert result.toString().equals("") : "Expected empty string, got '" + result.toString() + "'";
    }

    static void testForEachRemainingWithNullConsumer() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        boolean exceptionThrown = false;
        try {
            list.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException was expected when passing null consumer";
    }
}
