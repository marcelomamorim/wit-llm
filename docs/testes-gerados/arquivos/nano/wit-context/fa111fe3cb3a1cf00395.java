/*
 * @test
 * @summary Test for java.util.Objects.requireNonNull method
 * @run main ObjectsWitupTest
 */

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testRequireNonNullWithNullObject();
        testRequireNonNullWithNonNullObject();
        testRequireNonNullWithNullMessageSupplier();
        testRequireNonNullWithNonNullMessageSupplier();
    }

    public static void testRequireNonNullWithNullObject() {
        try {
            Objects.requireNonNull(null, () -> "message");
            System.out.println("FAIL: Expected NullPointerException for null object");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null object");
        }
    }

    public static void testRequireNonNullWithNonNullObject() {
        String obj = "test";
        String result = Objects.requireNonNull(obj, () -> "message");
        if (result == obj) {
            System.out.println("PASS: Non-null object returned correctly");
        } else {
            System.out.println("FAIL: Unexpected result for non-null object");
        }
    }

    public static void testRequireNonNullWithNullMessageSupplier() {
        try {
            Objects.requireNonNull(null, null);
            System.out.println("FAIL: Expected NullPointerException for null object with null message supplier");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null object with null message supplier");
        }
    }

    public static void testRequireNonNullWithNonNullMessageSupplier() {
        String obj = "test";
        Supplier<String> supplier = () -> "error message";
        String result = Objects.requireNonNull(obj, supplier);
        if (result == obj) {
            System.out.println("PASS: Non-null object returned correctly with message supplier");
        } else {
            System.out.println("FAIL: Unexpected result with message supplier");
        }
    }
}
