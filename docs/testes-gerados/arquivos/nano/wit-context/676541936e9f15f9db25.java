/* @test    @summary Test for OptionalInt.orElseThrow() method to verify exception when no value is present    @run main OptionalIntWitupTest */

import java.util.OptionalInt;

public class OptionalIntWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int result = opt.orElseThrow();
        if (result != 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }

    private static void testOrElseThrowWithoutValue() {
        OptionalInt emptyOpt = OptionalInt.empty();
        try {
            emptyOpt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
