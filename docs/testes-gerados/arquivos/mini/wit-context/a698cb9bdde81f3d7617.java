/*
 * @test
 * @summary Test OptionalDouble.getAsDouble() behavior including exception when empty
 * @run main OptionalDoubleWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalDouble;

public class OptionalDoubleWitupTest {
    public static void main(String[] args) {
        testGetAsDoubleWithValue();
        testGetAsDoubleEmptyThrows();
        System.out.println("All tests passed.");
    }

    private static void testGetAsDoubleWithValue() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double val = opt.getAsDouble();
        if (val != 3.14) {
            throw new AssertionError("Expected 3.14 but got " + val);
        }
    }

    private static void testGetAsDoubleEmptyThrows() {
        OptionalDouble emptyOpt = OptionalDouble.empty();
        try {
            emptyOpt.getAsDouble();
            throw new AssertionError("Expected NoSuchElementException for empty OptionalDouble");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
