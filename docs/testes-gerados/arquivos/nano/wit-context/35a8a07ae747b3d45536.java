/* @test    @summary Test for OptionalDouble.orElseThrow() method to verify exception when no value is present.    @run main OptionalDoubleWitupTest */

import java.util.OptionalDouble;

public class OptionalDoubleWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double result = opt.orElseThrow();
        if (result != 3.14) {
            throw new AssertionError("Expected 3.14, but got " + result);
        }
    }

    private static void testOrElseThrowWithoutValue() {
        OptionalDouble emptyOpt = OptionalDouble.empty();
        try {
            emptyOpt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
