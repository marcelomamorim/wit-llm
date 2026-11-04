/*
 * @test
 * @summary Tests for Comparator.comparingInt observable behavior
 * @run main ComparatorWitupTest
 */

import java.util.Comparator;

public class ComparatorWitupTest {

    static class Item {
        final int value;
        Item(int value) { this.value = value; }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullKeyExtractorThrowsNPE() {
        boolean thrown = false;
        try {
            Comparator.comparingInt(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "comparingInt(null) must throw NullPointerException");
    }

    static void testOrdersByExtractedKeyAscending() {
        Comparator<Item> c = Comparator.comparingInt(i -> i.value);
        check(c.compare(new Item(1), new Item(2)) < 0, "1 should be less than 2");
        check(c.compare(new Item(5), new Item(2)) > 0, "5 should be greater than 2");
        check(c.compare(new Item(3), new Item(3)) == 0, "3 should be equal to 3");
    }

    static void testHandlesNegativeAndExtremeValues() {
        Comparator<Integer> c = Comparator.comparingInt(i -> i);
        check(c.compare(Integer.MIN_VALUE, Integer.MAX_VALUE) < 0, "MIN_VALUE should be less than MAX_VALUE");
        check(c.compare(-1, 0) < 0, "-1 should be less than 0");
        check(c.compare(Integer.MAX_VALUE, Integer.MIN_VALUE) > 0, "MAX_VALUE should be greater than MIN_VALUE");
    }

    static void testSortsList() {
        Item[] items = { new Item(3), new Item(1), new Item(2) };
        java.util.Arrays.sort(items, Comparator.comparingInt(i -> i.value));
        check(items[0].value == 1 && items[1].value == 2 && items[2].value == 3,
              "array should be sorted by key");
    }

    static void testKeyExtractorCalledOnEachArgument() {
        int[] calls = {0};
        Comparator<String> c = Comparator.comparingInt(s -> { calls[0]++; return s.length(); });
        c.compare("a", "bb");
        check(calls[0] == 2, "key extractor should be applied to both arguments");
    }

    public static void main(String[] args) {
        testNullKeyExtractorThrowsNPE();
        testOrdersByExtractedKeyAscending();
        testHandlesNegativeAndExtremeValues();
        testSortsList();
        testKeyExtractorCalledOnEachArgument();
    }
}
