/*
 * @test
 * @summary Checks OptionalLong.orElseThrow for present and empty instances
 * @run main OptionalLongWitupTest
 */
import java.util.NoSuchElementException;
import java.util.OptionalLong;

public class OptionalLongWitupTest {
    public static void main(String[] args) {
        check(OptionalLong.of(42L).orElseThrow() == 42L, "value 42");
        check(OptionalLong.of(0L).orElseThrow() == 0L, "value 0");
        check(OptionalLong.of(-7L).orElseThrow() == -7L, "negative value");
        check(OptionalLong.of(Long.MIN_VALUE).orElseThrow() == Long.MIN_VALUE, "min value");
        check(OptionalLong.of(Long.MAX_VALUE).orElseThrow() == Long.MAX_VALUE, "max value");
        try {
            OptionalLong.empty().orElseThrow();
            throw new AssertionError("expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            check("No value present".equals(e.getMessage()), "unexpected message");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
