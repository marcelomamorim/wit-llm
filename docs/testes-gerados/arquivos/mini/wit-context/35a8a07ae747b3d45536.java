/*
 * @test
 * @summary Test OptionalDouble.orElseThrow() behavior for present and empty cases
 * @run main OptionalDoubleWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalDouble;

public class OptionalDoubleWitupTest {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowEmpty();
    }

    private static void testOrElseThrowWithValue() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double val = opt.orElseThrow();
        if (val != 3.14) {
            throw new AssertionError("Expected 3.14 but got " + val);
        }
    }

    private static void testOrElseThrowEmpty() {
        OptionalDouble emptyOpt = OptionalDouble.empty();
        try {
            emptyOpt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException for empty OptionalDouble");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
