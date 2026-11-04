/*
 * @test
 * @summary Test for Optional.or(Supplier) method to verify behavior with null supplier and non-null supplier.
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testOrWithNullSupplier();
        testOrWithNonNullSupplier();
    }

    private static void testOrWithNullSupplier() {
        Optional<String> opt = Optional.of("value");
        try {
            opt.or(null);
            System.out.println("FAIL: Expected NullPointerException for null supplier");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null supplier");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testOrWithNonNullSupplier() {
        Optional<String> opt = Optional.empty();
        Supplier<Optional<String>> supplier = () -> Optional.of("fallback");
        Optional<String> result = opt.or(supplier);
        if (result.isPresent() && "fallback".equals(result.get())) {
            System.out.println("PASS: or() returned expected fallback value");
        } else {
            System.out.println("FAIL: or() did not return expected fallback value");
        }
    }
}
