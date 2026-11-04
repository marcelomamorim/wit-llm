/*
 * @test
 * @summary Verifies Arrays.sort(Object[], int, int) sorts only the requested range, keeps stability and orders large inputs
 * @run main ArraysWitupTest
 */
import java.util.Arrays;
import java.util.Random;

public class ArraysWitupTest {

    static final class Item implements Comparable<Item> {
        final int key;
        final int id;

        Item(int key, int id) {
            this.key = key;
            this.id = id;
        }

        public int compareTo(Item o) {
            return Integer.compare(key, o.key);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Object[] full = {5, 3, 9, 1};
        Arrays.sort(full, 0, full.length);
        check(Arrays.equals(full, new Object[]{1, 3, 5, 9}), "full range sort failed");

        Object[] partial = {9, 8, 7, 6, 5, 4};
        Arrays.sort(partial, 1, 4);
        check(Arrays.equals(partial, new Object[]{9, 6, 7, 8, 5, 4}),
              "partial range sort must only reorder indices 1..3");

        Object[] empty = {3, 1, 2};
        Arrays.sort(empty, 1, 1);
        check(Arrays.equals(empty, new Object[]{3, 1, 2}), "empty range must not change array");

        Object[] prefix = {3, 2, 1};
        Arrays.sort(prefix, 0, 2);
        check(Arrays.equals(prefix, new Object[]{2, 3, 1}), "prefix range sort failed");

        Object[] strings = {"pear", "apple", "fig"};
        Arrays.sort(strings, 0, strings.length);
        check(Arrays.equals(strings, new Object[]{"apple", "fig", "pear"}), "string sort failed");

        Item[] items = {new Item(2, 0), new Item(1, 1), new Item(2, 2), new Item(1, 3), new Item(2, 4)};
        Arrays.sort(items, 0, items.length);
        int[] expectedIds = {1, 3, 0, 2, 4};
        for (int i = 0; i < items.length; i++) {
            check(items[i].id == expectedIds[i], "sort must be stable for equal keys at position " + i);
        }

        Random random = new Random(42);
        Object[] big = new Object[1000];
        long sumBefore = 0;
        for (int i = 0; i < big.length; i++) {
            int v = random.nextInt(500);
            big[i] = v;
            sumBefore += v;
        }
        Arrays.sort(big, 0, big.length);
        long sumAfter = 0;
        for (int i = 0; i < big.length; i++) {
            sumAfter += (Integer) big[i];
            if (i > 0) {
                check(((Integer) big[i - 1]).compareTo((Integer) big[i]) <= 0,
                      "large array not sorted at index " + i);
            }
        }
        check(sumBefore == sumAfter, "large array sort changed element multiset");
    }
}
