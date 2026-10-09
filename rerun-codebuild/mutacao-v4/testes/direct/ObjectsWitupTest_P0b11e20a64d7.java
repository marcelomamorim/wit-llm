/* @test
   @summary Testa o método Objects.requireNonNull com Supplier.
   @run main ObjectsWitupTest_P0b11e20a64d7
 */
import java.util.Objects;
import java.util.function.Supplier;
public class ObjectsWitupTest_P0b11e20a64d7 {
    public static void main(String[] args) {
        testRequireNonNullWithNonNullObject();
        testRequireNonNullWithNullObject();
        testRequireNonNullWithNullSupplier();
        testRequireNonNullWithNonNullSupplier();
    }

    static void testRequireNonNullWithNonNullObject() {
        String obj = "test";
        String result = Objects.requireNonNull(obj, () -> "message");
        if (result != obj) {
            throw new AssertionError("Expected same object to be returned");
        }
    }

    static void testRequireNonNullWithNullObject() {
        try {
            Objects.requireNonNull(null, () -> "message");
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testRequireNonNullWithNullSupplier() {
        try {
            Objects.requireNonNull("value", (Supplier<String>) null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testRequireNonNullWithNonNullSupplier() {
        String obj = "test";
        Supplier<String> supplier = () -> "message";
        String result = Objects.requireNonNull(obj, supplier);
        if (result != obj) {
            throw new AssertionError("Expected same object to be returned");
        }
    }
}
