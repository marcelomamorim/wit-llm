/*
 * @test
 * @summary Test OptionalDouble.getAsDouble() behavior for present and empty instances
 * @run main OptionalDoubleWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalDouble;

public class OptionalDoubleWitupTest {
    public static void main(String[] args) {
        testGetAsDoublePresent();
        testGetAsDoubleEmpty();
    }

    private static void testGetAsDoublePresent() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double val = opt.getAsDouble();
        if (val != 3.14) {
            throw new AssertionError("Expected 3.14 but got " + val);
        }
    }

    private static void testGetAsDoubleEmpty() {
        OptionalDouble emptyOpt = OptionalDouble.empty();
        try {
            emptyOpt.getAsDouble();
            throw new AssertionError("Expected NoSuchElementException for empty OptionalDouble");
        } catch (NoSuchElementException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("No value present")) {
                throw new AssertionError("Exception message mismatch: " + msg);
            }
        }
    }
}
