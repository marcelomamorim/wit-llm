/*
 * @test
 * @summary Test OptionalLong.getAsLong() behavior for present and empty instances
 * @run main OptionalLongWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalLong;

public class OptionalLongWitupTest {
    public static void main(String[] args) {
        testGetAsLongPresent();
        testGetAsLongEmpty();
    }

    private static void testGetAsLongPresent() {
        OptionalLong opt = OptionalLong.of(12345L);
        long val = opt.getAsLong();
        if (val != 12345L) {
            throw new AssertionError("Expected 12345L but got " + val);
        }
    }

    private static void testGetAsLongEmpty() {
        OptionalLong empty = OptionalLong.empty();
        try {
            empty.getAsLong();
            throw new AssertionError("Expected NoSuchElementException for empty OptionalLong");
        } catch (NoSuchElementException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("No value present")) {
                throw new AssertionError("Exception message mismatch: " + msg);
            }
        }
    }
}
