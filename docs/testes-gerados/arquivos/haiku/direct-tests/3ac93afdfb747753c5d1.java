/*
 * @test
 * @summary Behavioral checks for Comparator.comparingDouble key extraction, Double.compare ordering, null handling and Serializable result
 * @run main ComparatorWitupTest
 */

import java.io.Serializable;

public class ComparatorWitupTest {

    static class Item {
        final double v;
        Item(double v) { this.v = v; }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Comparator<Item> c = Comparator.comparingDouble(i -> i.v);

        check(c.compare(new Item(1.0), new Item(2.0)) < 0, "1.0 should be less than 2.0");
        check(c.compare(new Item(2.0), new Item(1.0)) > 0, "2.0 should be greater than 1.0");
        check(c.compare(new Item(1.5), new Item(1.5)) == 0, "equal keys should compare to 0");

        check(c.compare(new Item(-0.0), new Item(0.0)) < 0, "-0.0 should be less than 0.0 per Double.compare");
        check(c.compare(new Item(Double.NaN), new Item(Double.POSITIVE_INFINITY)) > 0,
              "NaN should be greater than POSITIVE_INFINITY per Double.compare");

        check(c instanceof Serializable, "returned comparator should be Serializable");

        Item[] items = { new Item(3.0), new Item(-1.0), new Item(2.5) };
        Arrays.sort(items, c);
        check(items[0].v == -1.0 && items[1].v == 2.5 && items[2].v == 3.0,
              "sorting by comparingDouble should order ascending by key");

        boolean npe = false;
        try {
            Comparator.comparingDouble(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null keyExtractor should throw NullPointerException");
    }
}
