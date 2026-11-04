/* @test    @summary Test for SplittableRandom.ints(int, int) method to ensure it throws IllegalArgumentException for invalid range.    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testIntsWithInvalidRange();
        testIntsWithValidRange();
    }

    static void testIntsWithInvalidRange() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.ints(10, 10).limit(1).forEach(i -> {});
            throw new AssertionError("Expected IllegalArgumentException for equal bounds");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        try {
            sr.ints(20, 10).limit(1).forEach(i -> {});
            throw new AssertionError("Expected IllegalArgumentException for lower bound > upper bound");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testIntsWithValidRange() {
        SplittableRandom sr = new SplittableRandom();
        IntStream stream = sr.ints(5, 10);
        int count = 0;
        for (int i : stream.limit(100).toArray()) {
            if (i < 5 || i >= 10) {
                throw new AssertionError("Generated value out of range: " + i);
            }
            count++;
        }
        if (count != 100) {
            throw new AssertionError("Stream did not generate expected number of values");
        }
    }
}
