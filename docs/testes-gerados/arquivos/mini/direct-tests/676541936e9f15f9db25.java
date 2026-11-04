/*
 * @test
 * @summary Test OptionalInt.orElseThrow() behavior for present and empty values
 * @run main OptionalIntWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalInt;

public class OptionalIntWitupTest {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowEmpty();
    }

    private static void testOrElseThrowWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int val = opt.orElseThrow();
        if (val != 42) {
            throw new AssertionError("Expected 42 but got " + val);
        }
    }

    private static void testOrElseThrowEmpty() {
        OptionalInt emptyOpt = OptionalInt.empty();
        try {
            emptyOpt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException for empty OptionalInt");
        } catch (NoSuchElementException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("No value present")) {
                throw new AssertionError("Exception message mismatch: " + msg);
            }
        }
    }
}
