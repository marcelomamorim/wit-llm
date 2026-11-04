/*
 * @test
 * @summary Verify Comparator.comparingInt null key extractor handling and key-based ordering
 * @run main ComparatorWitupTest
 */

import java.util.Comparator;

public class ComparatorWitupTest {

    static class Item {
        final int value;
        Item(int value) { this.value = value; }
    }

    public static void main(String[] args) {
        testNullKeyExtractorThrowsNPE();
        testOrdersByExtractedKey();
        testEqualKeysCompareZero();
        testReversedOrdering();
    }

    static void testNullKeyExtractorThrowsNPE() {
        try {
            Comparator.comparingInt(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testOrdersByExtractedKey() {
        Comparator<Item> c = Comparator.comparingInt(i -> i.value);
        check(c.compare(new Item(1), new Item(2)) < 0, "1 should be before 2");
        check(c.compare(new Item(5), new Item(2)) > 0, "5 should be after 2");
        check(c.compare(new Item(-3), new Item(-1)) < 0, "-3 should be before -1");
    }

    static void testEqualKeysCompareZero() {
        Comparator<Item> c = Comparator.comparingInt(i -> i.value);
        check(c.compare(new Item(7), new Item(7)) == 0, "equal keys should compare to 0");
    }

    static void testReversedOrdering() {
        Comparator<Item> c = Comparator.comparingInt((Item i) -> i.value).reversed();
        check(c.compare(new Item(1), new Item(2)) > 0, "reversed: 1 should be after 2");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
