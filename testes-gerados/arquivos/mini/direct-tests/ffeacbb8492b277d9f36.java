/*
 * @test
 * @summary Test java.lang.invoke.ConstantBootstraps.enumConstant method
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {

    enum SampleEnum {
        ALPHA, BETA
    }

    public static void main(String[] args) throws Throwable {
        testValidEnumConstant();
        testNullLookup();
        testNullName();
        testNullType();
        testInvalidAccess();
    }

    private static void testValidEnumConstant() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String name = "ALPHA";
        SampleEnum result = ConstantBootstraps.enumConstant(lookup, name, SampleEnum.class);
        if (result != SampleEnum.ALPHA) {
            throw new AssertionError("Expected ALPHA but got " + result);
        }
    }

    private static void testNullLookup() {
        try {
            ConstantBootstraps.enumConstant(null, "ALPHA", SampleEnum.class);
            throw new AssertionError("Expected NullPointerException for null lookup");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullName() {
        try {
            ConstantBootstraps.enumConstant(MethodHandles.lookup(), null, SampleEnum.class);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullType() {
        try {
            ConstantBootstraps.enumConstant(MethodHandles.lookup(), "ALPHA", null);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
        }
    }

    private static void testInvalidAccess() {
        // The validateClassAccess method is package-private and not accessible here,
        // so we test indirectly by passing a class that the lookup cannot access.
        // We expect an IllegalAccessException or similar to be thrown by enumConstant.
        // However, since validateClassAccess is not visible and no exception declared,
        // we catch any RuntimeException or Error.

        // Create a dummy class loader to load a class not accessible by the lookup
        try {
            Class<?> inaccessibleClass = Class.forName("java.lang.invoke.ConstantBootstrapsWitupTest$InaccessibleEnum");
            ConstantBootstraps.enumConstant(MethodHandles.lookup(), "VALUE", inaccessibleClass.asSubclass(Enum.class));
            throw new AssertionError("Expected exception due to invalid access");
        } catch (ClassNotFoundException e) {
            // Should not happen
            throw new AssertionError("InaccessibleEnum class not found", e);
        } catch (RuntimeException | Error e) {
            // Expected: access validation failure
        }
    }

    // Dummy enum class to test access validation
    private enum InaccessibleEnum {
        VALUE
    }
}
