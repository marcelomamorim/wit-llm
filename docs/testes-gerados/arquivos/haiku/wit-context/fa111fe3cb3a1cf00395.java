/*
 * @test
 * @summary Verifies Objects.requireNonNull(T, Supplier<String>) returns non-null arguments unchanged, invokes the supplier only on null, and throws NullPointerException with the supplied message or a null message.
 * @run main ObjectsWitupTest
 */

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        String value = "x";
        Object same = Objects.requireNonNull(value, () -> "msg");
        if (same != value) {
            throw new AssertionError("requireNonNull must return the same non-null instance");
        }

        int[] calls = {0};
        Supplier<String> counting = () -> {
            calls[0]++;
            return "m";
        };
        Objects.requireNonNull(value, counting);
        if (calls[0] != 0) {
            throw new AssertionError("supplier must not be invoked for a non-null argument");
        }

        expectNpe(() -> Objects.requireNonNull(null, () -> "custom message"), "custom message");
        expectNpe(() -> Objects.requireNonNull(null, null), null);
        expectNpe(() -> Objects.requireNonNull(null, () -> null), null);
    }

    static void expectNpe(Runnable action, String expectedMessage) {
        try {
            action.run();
        } catch (NullPointerException e) {
            if (!Objects.equals(e.getMessage(), expectedMessage)) {
                throw new AssertionError("unexpected NPE message: " + e.getMessage());
            }
            return;
        }
        throw new AssertionError("NullPointerException expected");
    }
}
