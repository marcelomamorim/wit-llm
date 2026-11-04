/*
 * @test
 * @summary Test OptionalInt.getAsInt() behavior including exception when empty
 * @run main OptionalIntWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalInt;

public class OptionalIntWitupTest {
    public static void main(String[] args) {
        testGetAsIntWithValue();
        testGetAsIntEmptyThrows();
        System.out.println("OptionalIntWitupTest passed");
    }

    private static void testGetAsIntWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int val = opt.getAsInt();
        if (val != 42) {
            throw new AssertionError("Expected 42 but got " + val);
        }
    }

    private static void testGetAsIntEmptyThrows() {
        OptionalInt emptyOpt = OptionalInt.empty();
        try {
            emptyOpt.getAsInt();
            throw new AssertionError("Expected NoSuchElementException for empty OptionalInt");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
