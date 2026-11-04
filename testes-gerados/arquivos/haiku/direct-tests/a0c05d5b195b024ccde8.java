/*
 * @test
 * @summary Tests observable behavior of Optional.or(Supplier)
 * @run main OptionalWitupTest
 */
import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testPresentReturnsSameInstanceWithoutCallingSupplier();
        testEmptyReturnsSupplierResult();
        testEmptyReturnsSupplierEmptyResult();
        testNullSupplierThrowsNPE();
        testNullSupplierResultThrowsNPE();
    }

    static void testPresentReturnsSameInstanceWithoutCallingSupplier() {
        Optional<String> present = Optional.of("a");
        int[] calls = {0};
        Supplier<Optional<String>> s = () -> {
            calls[0]++;
            return Optional.of("b");
        };
        Optional<String> r = present.or(s);
        check(r == present, "expected the same instance when value is present");
        check(calls[0] == 0, "supplier must not be invoked when value is present");
    }

    static void testEmptyReturnsSupplierResult() {
        Supplier<Optional<String>> s = () -> Optional.of("x");
        Optional<String> r = Optional.<String>empty().or(s);
        check(r.isPresent(), "expected present result from supplier");
        check(r.get().equals("x"), "expected value 'x' from supplier");
    }

    static void testEmptyReturnsSupplierEmptyResult() {
        Supplier<Optional<String>> s = () -> Optional.<String>empty();
        Optional<String> r = Optional.<String>empty().or(s);
        check(!r.isPresent(), "expected empty result when supplier returns empty");
    }

    static void testNullSupplierThrowsNPE() {
        try {
            Optional.<String>empty().or(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null supplier");
    }

    static void testNullSupplierResultThrowsNPE() {
        Supplier<Optional<String>> s = () -> null;
        try {
            Optional.<String>empty().or(s);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException when supplier returns null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
