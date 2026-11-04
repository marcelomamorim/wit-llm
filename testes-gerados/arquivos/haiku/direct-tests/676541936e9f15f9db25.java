/*
 * @test
 * @summary Verifies OptionalInt.orElseThrow returns the contained value when present and throws NoSuchElementException with message "No value present" when empty
 * @run main OptionalIntWitupTest
 */
import java.util.NoSuchElementException;
import java.util.OptionalInt;

public class OptionalIntWitupTest {
    public static void main(String[] args) {
        check(OptionalInt.of(5).orElseThrow() == 5, "of(5) should return 5");
        check(OptionalInt.of(0).orElseThrow() == 0, "of(0) should return 0");
        check(OptionalInt.of(-1).orElseThrow() == -1, "of(-1) should return -1");
        check(OptionalInt.of(Integer.MIN_VALUE).orElseThrow() == Integer.MIN_VALUE,
              "MIN_VALUE should be returned");
        check(OptionalInt.of(Integer.MAX_VALUE).orElseThrow() == Integer.MAX_VALUE,
              "MAX_VALUE should be returned");
        checkEmptyThrows();
    }

    static void checkEmptyThrows() {
        boolean thrown = false;
        String message = null;
        try {
            OptionalInt.empty().orElseThrow();
        } catch (NoSuchElementException e) {
            thrown = true;
            message = e.getMessage();
        }
        check(thrown, "empty().orElseThrow() should throw NoSuchElementException");
        check("No value present".equals(message),
              "exception message should be 'No value present' but was: " + message);
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}
