/*
 * @test
 * @summary Tests OptionalInt.orElseThrow(Supplier) returning value when present and throwing supplied exception when empty
 * @run main OptionalIntWitupTest
 */

import java.util.NoSuchElementException;
import java.util.OptionalInt;

public class OptionalIntWitupTest {
    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        OptionalInt present = OptionalInt.of(42);
        int v = present.orElseThrow(() -> new IllegalStateException("unexpected"));
        check(v == 42, "orElseThrow on present value should return 42, got " + v);

        OptionalInt presentZero = OptionalInt.of(0);
        int z = presentZero.orElseThrow(() -> new IllegalStateException("unexpected"));
        check(z == 0, "orElseThrow on present zero should return 0, got " + z);

        boolean thrown = false;
        try {
            OptionalInt.empty().orElseThrow(() -> new NoSuchElementException("custom"));
        } catch (NoSuchElementException e) {
            thrown = true;
            check("custom".equals(e.getMessage()),
                  "expected supplied exception message 'custom', got " + e.getMessage());
        }
        check(thrown, "orElseThrow on empty must throw the supplied exception");

        final int[] calls = {0};
        try {
            OptionalInt.empty().orElseThrow(() -> {
                calls[0]++;
                return new IllegalArgumentException("lazy");
            });
            check(false, "orElseThrow on empty must throw");
        } catch (IllegalArgumentException e) {
            check(calls[0] == 1, "supplier should be invoked exactly once, was " + calls[0]);
        }

        final int[] presentCalls = {0};
        present.orElseThrow(() -> {
            presentCalls[0]++;
            return new IllegalStateException("should not be called");
        });
        check(presentCalls[0] == 0, "supplier must not be invoked when value is present");
    }
}
