/* @test    @summary Test for Collections.nCopies method to ensure correct list creation and exception handling.    @run main CollectionsWitupTest_P9870d2d40dd4 */

import java.util.List;
import java.util.Collections;

public class CollectionsWitupTest_P9870d2d40dd4 {

    public static void main(String[] args) {
        testNCopiesWithPositiveNumber();
        testNCopiesWithZero();
        testNCopiesWithNegativeNumber();
    }

    private static void testNCopiesWithPositiveNumber() {
        int n = 5;
        String o = "test";
        List<String> list = Collections.nCopies(n, o);
        boolean sizeCorrect = list.size() == n;
        boolean allElementsEqual = true;
        for (String element : list) {
            if (!element.equals(o)) {
                allElementsEqual = false;
                break;
            }
        }
        if (!sizeCorrect) {
            throw new AssertionError("Expected size " + n + ", but got " + list.size());
        }
        if (!allElementsEqual) {
            throw new AssertionError("Not all elements are equal to the original object.");
        }
    }

    private static void testNCopiesWithZero() {
        int n = 0;
        String o = "zero";
        List<String> list = Collections.nCopies(n, o);
        if (list.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + list.size());
        }
    }

    private static void testNCopiesWithNegativeNumber() {
        int n = -3;
        String o = "negative";
        try {
            List<String> list = Collections.nCopies(n, o);
            throw new AssertionError("Expected IllegalArgumentException for negative n, but no exception was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes.
        }
    }
}
