/*
 * @test
 * @summary Test for OptionalInt.getAsInt() method, including exception path.
 * @run main OptionalIntWitupTest
 */

import java.util.OptionalInt;

public class OptionalIntWitupTest {

    public static void main(String[] args) {
        testGetAsIntWithValue();
        testGetAsIntWithoutValue();
    }

    private static void testGetAsIntWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int result = opt.getAsInt();
        if (result != 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }

    private static void testGetAsIntWithoutValue() {
        OptionalInt emptyOpt = OptionalInt.empty();
        try {
            emptyOpt.getAsInt();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception, test passes
        }
    }
}
