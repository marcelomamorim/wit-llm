/*
 * @test
 * @summary Verifies OptionalLong.orElseThrow returns the contained value when present and throws NoSuchElementException with the documented message when empty
 * @run main OptionalLongWitupTest
 */
import java.util.NoSuchElementException;
import java.util.OptionalLong;

public class OptionalLongWitupTest {
    public static void main(String[] args) {
        check(OptionalLong.of(42L).orElseThrow() == 42L, "value 42");
        check(OptionalLong.of(0L).orElseThrow() == 0L, "value 0");
        check(OptionalLong.of(-1L).orElseThrow() == -1L, "value -1");
        check(OptionalLong.of(Long.MIN_VALUE).orElseThrow() == Long.MIN_VALUE, "MIN_VALUE");
        check(OptionalLong.of(Long.MAX_VALUE).orElseThrow() == Long.MAX_VALUE, "MAX_VALUE");
        try {
            OptionalLong.empty().orElseThrow();
            throw new RuntimeException("Failed: expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            check("No value present".equals(e.getMessage()), "exception message");
        }
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Failed: " + what);
        }
    }
}
