/*
 * @test
 * @summary Test OptionalLong.getAsLong() behavior including exception when empty
 * @run main OptionalLongWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalLong;

public class OptionalLongWitupTest {
    public static void main(String[] args) {
        testGetAsLongWithValue();
        testGetAsLongEmptyThrows();
        System.out.println("All tests passed.");
    }

    private static void testGetAsLongWithValue() {
        OptionalLong opt = OptionalLong.of(12345L);
        long val = opt.getAsLong();
        if (val != 12345L) {
            throw new AssertionError("Expected 12345L but got " + val);
        }
    }

    private static void testGetAsLongEmptyThrows() {
        OptionalLong emptyOpt = OptionalLong.empty();
        try {
            emptyOpt.getAsLong();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No value present")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
