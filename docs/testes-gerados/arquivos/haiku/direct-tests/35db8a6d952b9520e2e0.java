/*
 * @test
 * @summary Verifies OptionalInt.getAsInt returns the contained value and throws NoSuchElementException when empty
 * @run main OptionalIntWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalInt;

public class OptionalIntWitupTest {
    public static void main(String[] args) {
        check(OptionalInt.of(42).getAsInt() == 42, "of(42).getAsInt() should be 42");
        check(OptionalInt.of(0).getAsInt() == 0, "of(0).getAsInt() should be 0");
        check(OptionalInt.of(-1).getAsInt() == -1, "of(-1).getAsInt() should be -1");
        check(OptionalInt.of(Integer.MIN_VALUE).getAsInt() == Integer.MIN_VALUE,
              "of(MIN_VALUE).getAsInt() should be MIN_VALUE");
        check(OptionalInt.of(Integer.MAX_VALUE).getAsInt() == Integer.MAX_VALUE,
              "of(MAX_VALUE).getAsInt() should be MAX_VALUE");

        OptionalInt present = OptionalInt.of(7);
        check(present.getAsInt() == 7, "first call should return 7");
        check(present.getAsInt() == 7, "repeated call should return 7");

        boolean thrown = false;
        String message = null;
        try {
            OptionalInt.empty().getAsInt();
        } catch (NoSuchElementException e) {
            thrown = true;
            message = e.getMessage();
        }
        check(thrown, "empty().getAsInt() should throw NoSuchElementException");
        check("No value present".equals(message),
              "exception message should be 'No value present' but was: " + message);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
