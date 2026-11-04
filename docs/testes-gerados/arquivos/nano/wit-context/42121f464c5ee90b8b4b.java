/* @test    @summary Tests for java.util.SplittableRandom.ints() method    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testIntsReturnsStream();
        testIntsStreamIsNotNull();
    }

    public static void testIntsReturnsStream() {
        SplittableRandom sr = new SplittableRandom();
        IntStream stream = sr.ints();
        if (stream == null) {
            throw new AssertionError("ints() returned null");
        }
    }

    public static void testIntsStreamIsNotNull() {
        SplittableRandom sr = new SplittableRandom();
        IntStream stream = sr.ints();
        boolean hasElements = stream.anyMatch(i -> true);
        if (!hasElements) {
            throw new AssertionError("ints() stream has no elements");
        }
    }
}
