/*
 * @test
 * @summary Verify Objects.requireNonNull(T) returns its argument and throws NullPointerException for null
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        String s = "value";
        if (Objects.requireNonNull(s) != s) {
            throw new RuntimeException("requireNonNull did not return the same reference");
        }

        Integer boxed = Integer.valueOf(42);
        if (Objects.requireNonNull(boxed) != boxed) {
            throw new RuntimeException("requireNonNull did not return the same Integer reference");
        }

        Object empty = new Object[0];
        if (Objects.requireNonNull(empty) != empty) {
            throw new RuntimeException("requireNonNull did not return the same array reference");
        }

        boolean thrown = false;
        try {
            Objects.requireNonNull(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("requireNonNull(null) did not throw NullPointerException");
        }

        thrown = false;
        try {
            String nothing = null;
            Objects.requireNonNull(nothing);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("requireNonNull(null variable) did not throw NullPointerException");
        }
    }
}
