/*
 * @test
 * @summary Test java.util.Collections.nCopies method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testZeroCopies();
        testPositiveCopies();
        testNegativeCopiesThrows();
    }

    private static void testZeroCopies() {
        List<String> list = Collections.nCopies(0, "test");
        if (list == null) {
            throw new AssertionError("nCopies(0, o) returned null");
        }
        if (!list.isEmpty()) {
            throw new AssertionError("nCopies(0, o) should return empty list");
        }
    }

    private static void testPositiveCopies() {
        final int n = 5;
        final String element = "hello";
        List<String> list = Collections.nCopies(n, element);
        if (list == null) {
            throw new AssertionError("nCopies(n, o) returned null");
        }
        if (list.size() != n) {
            throw new AssertionError("nCopies(n, o) returned list of wrong size: " + list.size());
        }
        for (int i = 0; i < n; i++) {
            if (!element.equals(list.get(i))) {
                throw new AssertionError("Element at index " + i + " is not equal to the original element");
            }
        }
        // Test immutability: add should throw UnsupportedOperationException
        try {
            list.add(element);
            throw new AssertionError("nCopies list should be immutable, add did not throw");
        } catch (UnsupportedOperationException expected) {
        }
        // Test immutability: set should throw UnsupportedOperationException
        try {
            list.set(0, element);
            throw new AssertionError("nCopies list should be immutable, set did not throw");
        } catch (UnsupportedOperationException expected) {
        }
    }

    private static void testNegativeCopiesThrows() {
        try {
            Collections.nCopies(-1, "fail");
            throw new AssertionError("nCopies with negative n did not throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }
}
