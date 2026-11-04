/*
 * @test
 * @summary Verify OptionalLong.getAsLong returns the contained value or throws NoSuchElementException when empty
 * @run main OptionalLongWitupTest
 */
import java.util.NoSuchElementException;
import java.util.OptionalLong;

public class OptionalLongWitupTest {
    public static void main(String[] args) {
        check(OptionalLong.of(42L).getAsLong() == 42L, "of(42L) should return 42");
        check(OptionalLong.of(0L).getAsLong() == 0L, "of(0L) should return 0");
        check(OptionalLong.of(-7L).getAsLong() == -7L, "of(-7L) should return -7");
        check(OptionalLong.of(Long.MIN_VALUE).getAsLong() == Long.MIN_VALUE, "Long.MIN_VALUE should be preserved");
        check(OptionalLong.of(Long.MAX_VALUE).getAsLong() == Long.MAX_VALUE, "Long.MAX_VALUE should be preserved");

        checkEmptyThrows();
        checkEmptyThrowsRepeatedly();
    }

    static void checkEmptyThrows() {
        boolean thrown = false;
        String message = null;
        try {
            OptionalLong.empty().getAsLong();
        } catch (NoSuchElementException e) {
            thrown = true;
            message = e.getMessage();
        }
        check(thrown, "empty().getAsLong() should throw NoSuchElementException");
        check("No value present".equals(message), "unexpected message: " + message);
    }

    static void checkEmptyThrowsRepeatedly() {
        for (int i = 0; i < 3; i++) {
            boolean thrown = false;
            try {
                OptionalLong.empty().getAsLong();
            } catch (NoSuchElementException e) {
                thrown = true;
            }
            check(thrown, "empty().getAsLong() should throw on iteration " + i);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
