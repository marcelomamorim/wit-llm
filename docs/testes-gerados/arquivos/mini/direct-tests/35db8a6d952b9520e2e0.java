/*
 * @test
 * @summary Test OptionalInt.getAsInt() behavior for present and empty instances
 * @run main java.util.OptionalIntWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalInt;

public class OptionalIntWitupTest {
    public static void main(String[] args) {
        testGetAsIntPresent();
        testGetAsIntEmpty();
    }

    private static void testGetAsIntPresent() {
        OptionalInt opt = OptionalInt.of(42);
        int val = opt.getAsInt();
        if (val != 42) {
            throw new AssertionError("Expected 42 but got " + val);
        }
    }

    private static void testGetAsIntEmpty() {
        OptionalInt emptyOpt = OptionalInt.empty();
        try {
            emptyOpt.getAsInt();
            throw new AssertionError("Expected NoSuchElementException for empty OptionalInt");
        } catch (NoSuchElementException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("No value present")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
