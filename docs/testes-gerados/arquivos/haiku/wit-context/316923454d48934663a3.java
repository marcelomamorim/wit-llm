/*
 * @test
 * @summary Verify Objects.requireNonNullElse returns the non-null argument, the default when obj is null, and throws NPE with message defaultObj when both are null
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        String value = "value";
        String fallback = "fallback";

        if (Objects.requireNonNullElse(value, fallback) != value) {
            throw new RuntimeException("non-null obj was not returned");
        }

        if (Objects.requireNonNullElse(null, fallback) != fallback) {
            throw new RuntimeException("defaultObj was not returned when obj is null");
        }

        if (Objects.requireNonNullElse(value, null) != value) {
            throw new RuntimeException("obj was not returned when defaultObj is null");
        }

        try {
            Objects.requireNonNullElse(null, null);
            throw new RuntimeException("expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            if (!"defaultObj".equals(e.getMessage())) {
                throw new RuntimeException("unexpected NPE message: " + e.getMessage());
            }
        }
    }
}
