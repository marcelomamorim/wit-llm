/* @test    @summary Test java.util.Objects.requireNonNull with non-null object    @run main ObjectsWitupTest_P2c7e20f8e646 */

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsWitupTest_P2c7e20f8e646 {
    public static void main(String[] args) {
        testRequireNonNullWithNonNullObject();
        testRequireNonNullWithNullObject();
        testRequireNonNullWithNullMessageSupplier();
        System.out.println("PASS");
    }

    static void testRequireNonNullWithNonNullObject() {
        Object obj = new Object();
        Object result = Objects.requireNonNull(obj, () -> "Should not throw");
        if (result != obj) {
            throw new AssertionError("Failed: result should be the same as input object");
        }
    }

    static void testRequireNonNullWithNullObject() {
        try {
            Objects.requireNonNull(null, () -> "Null object test");
            throw new AssertionError("Failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testRequireNonNullWithNullMessageSupplier() {
        try {
            Objects.requireNonNull(null, (Supplier<String>) null);
            throw new AssertionError("Failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
