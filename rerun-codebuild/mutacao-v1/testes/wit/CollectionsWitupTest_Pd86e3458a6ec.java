/* @test    @summary Test for Collections.nCopies handling of negative n    @run main CollectionsWitupTest_Pd86e3458a6ec */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest_Pd86e3458a6ec {
    public static void main(String[] args) {
        testNCopiesNegative();
        testNCopiesZero();
        testNCopiesPositive();
    }

    private static void testNCopiesNegative() {
        boolean exceptionThrown = false;
        try {
            List<Object> result = Collections.nCopies(-1, new Object());
            if (result != null) {
                throw new AssertionError("Expected exception not thrown for n = -1");
            }
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("IllegalArgumentException not thrown for n = -1");
        }
    }

    private static void testNCopiesZero() {
        List<Object> result = Collections.nCopies(0, new Object());
        if (result == null) {
            throw new AssertionError("Result is null for n = 0");
        }
        if (!result.isEmpty()) {
            throw new AssertionError("Result is not empty for n = 0");
        }
    }

    private static void testNCopiesPositive() {
        int n = 3;
        Object o = new Object();
        List<Object> result = Collections.nCopies(n, o);
        if (result.size() != n) {
            throw new AssertionError("Result size mismatch: expected " + n + " but was " + result.size());
        }
        for (Object item : result) {
            if (item != o) {
                throw new AssertionError("Item in list does not match the original object");
            }
        }
    }
}
