/*
 * @test
 * @summary Test for OptionalInt.orElseThrow() method behavior.
 * @run main OptionalIntWitupTest
 */

import java.util.OptionalInt;
import java.util.NoSuchElementException;

public class OptionalIntWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    static void testOrElseThrowWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int result = opt.orElseThrow();
        if (result != 42) {
            throw new AssertionError("Expected 42, got " + result);
        }
    }

    static void testOrElseThrowWithoutValue() {
        OptionalInt opt = OptionalInt.empty();
        try {
            opt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
