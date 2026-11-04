/*
 * @test
 * @summary Test OptionalLong.orElseThrow() behavior including exception when empty
 * @run main OptionalLongWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalLong;

public class OptionalLongWitupTest {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowEmptyThrows();
    }

    private static void testOrElseThrowWithValue() {
        OptionalLong opt = OptionalLong.of(123L);
        long val = opt.orElseThrow();
        if (val != 123L) {
            throw new AssertionError("Expected 123L but got " + val);
        }
    }

    private static void testOrElseThrowEmptyThrows() {
        OptionalLong emptyOpt = OptionalLong.empty();
        try {
            emptyOpt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No value present")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
