/*
 * @test
 * @summary Test Comparator.comparingLong with valid and null keyExtractor
 * @run main ComparatorWitupTest
 */

import java.util.Comparator;
import java.util.function.ToLongFunction;
import java.io.Serializable;

public class ComparatorWitupTest {
    public static void main(String[] args) {
        testComparingLongWithValidKeyExtractor();
        testComparingLongWithNullKeyExtractor();
    }

    private static void testComparingLongWithValidKeyExtractor() {
        ToLongFunction<String> lengthExtractor = new ToLongFunction<String>() {
            public long applyAsLong(String s) {
                return s.length();
            }
        };

        Comparator<String> comp = Comparator.comparingLong(lengthExtractor);

        // Check that comparator orders strings by length
        String a = "a";
        String bb = "bb";
        int result = comp.compare(a, bb);
        if (result >= 0) {
            throw new AssertionError("Expected 'a' < 'bb' by length, got " + result);
        }

        // Check that comparator is Serializable
        if (!(comp instanceof Serializable)) {
            throw new AssertionError("Comparator returned is not Serializable");
        }

        // Check that comparing equal lengths returns 0
        String c1 = "aa";
        String c2 = "bb";
        int eqResult = comp.compare(c1, c2);
        if (eqResult != 0) {
            throw new AssertionError("Expected equal length strings to compare as 0, got " + eqResult);
        }
    }

    private static void testComparingLongWithNullKeyExtractor() {
        try {
            Comparator.comparingLong(null);
            throw new AssertionError("Expected NullPointerException for null keyExtractor");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
