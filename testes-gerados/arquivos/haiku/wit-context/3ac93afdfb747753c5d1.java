/*
 * @test
 * @summary Verify Comparator.comparingDouble null-key check, double ordering, NaN and -0.0 semantics, and Serializable result
 * @run main ComparatorWitupTest
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorWitupTest {

    static final class Item {
        final double value;
        final String name;

        Item(double value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    public static void main(String[] args) {
        testNullKeyExtractorThrowsNpe();
        testOrdersByDoubleKey();
        testEqualKeysCompareZero();
        testNegativeZeroAndNaN();
        testResultIsSerializable();
    }

    static void testNullKeyExtractorThrowsNpe() {
        boolean thrown = false;
        try {
            Comparator.<Item>comparingDouble(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null keyExtractor");
        }
    }

    static void testOrdersByDoubleKey() {
        Comparator<Item> c = Comparator.<Item>comparingDouble(i -> i.value);
        List<Item> items = new ArrayList<>();
        items.add(new Item(3.5, "c"));
        items.add(new Item(-1.0, "a"));
        items.add(new Item(2.25, "b"));
        Collections.sort(items, c);
        if (items.get(0).value != -1.0 || items.get(1).value != 2.25 || items.get(2).value != 3.5) {
            throw new RuntimeException("unexpected sort order");
        }
        if (c.compare(new Item(1.0, "x"), new Item(2.0, "y")) >= 0) {
            throw new RuntimeException("smaller key must compare less");
        }
        if (c.compare(new Item(2.0, "x"), new Item(1.0, "y")) <= 0) {
            throw new RuntimeException("larger key must compare greater");
        }
    }

    static void testEqualKeysCompareZero() {
        Comparator<Item> c = Comparator.<Item>comparingDouble(i -> i.value);
        if (c.compare(new Item(4.5, "p"), new Item(4.5, "q")) != 0) {
            throw new RuntimeException("equal keys must compare as zero");
        }
    }

    static void testNegativeZeroAndNaN() {
        Comparator<Item> c = Comparator.<Item>comparingDouble(i -> i.value);
        if (c.compare(new Item(-0.0, "neg"), new Item(0.0, "pos")) >= 0) {
            throw new RuntimeException("-0.0 must compare less than 0.0");
        }
        if (c.compare(new Item(Double.NaN, "nan"), new Item(1.0, "one")) <= 0) {
            throw new RuntimeException("NaN must compare greater than 1.0");
        }
        if (c.compare(new Item(Double.NaN, "nan1"), new Item(Double.NaN, "nan2")) != 0) {
            throw new RuntimeException("NaN must compare equal to NaN");
        }
    }

    static void testResultIsSerializable() {
        Comparator<Item> c = Comparator.<Item>comparingDouble(i -> i.value);
        if (!(c instanceof Serializable)) {
            throw new RuntimeException("comparingDouble result must be Serializable");
        }
    }
}
