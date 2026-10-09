/* @test    @summary Test for Collections.nCopies method to verify correct list creation and exception handling    @run main CollectionsWitupTest_Pd86e3458a6ec */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest_Pd86e3458a6ec {
    public static void main(String[] args) {
        testNCopiesWithPositiveNumber();
        testNCopiesWithZero();
        testNCopiesWithNegativeNumber();
    }

    private static void testNCopiesWithPositiveNumber() {
        int n = 5;
        String o = "test";
        List<String> list = Collections.nCopies(n, o);
        if (list.size() != n) {
            throw new AssertionError("Expected list size " + n + ", but got " + list.size());
        }
        for (String item : list) {
            if (!item.equals(o)) {
                throw new AssertionError("List item does not match expected value: " + item);
            }
        }
    }

    private static void testNCopiesWithZero() {
        int n = 0;
        String o = "zero";
        List<String> list = Collections.nCopies(n, o);
        if (list.size() != 0) {
            throw new AssertionError("Expected list size 0, but got " + list.size());
        }
    }

    private static void testNCopiesWithNegativeNumber() {
        int n = -3;
        String o = "negative";
        try {
            Collections.nCopies(n, o);
            throw new AssertionError("Expected IllegalArgumentException for negative n") ;
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
