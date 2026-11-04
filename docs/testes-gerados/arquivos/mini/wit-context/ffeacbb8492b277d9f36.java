/*
 * @test
 * @summary Test java.lang.invoke.ConstantBootstraps.enumConstant method for null checks and normal behavior
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) {
        testNullLookup();
        testNullName();
        testNullType();
        testValidEnumConstant();
    }

    private static void testNullLookup() {
        try {
            ConstantBootstraps.enumConstant(null, "MONDAY", java.time.DayOfWeek.class);
            throw new Error("Expected NullPointerException for null lookup");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullName() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            ConstantBootstraps.enumConstant(lookup, null, java.time.DayOfWeek.class);
            throw new Error("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullType() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            ConstantBootstraps.enumConstant(lookup, "MONDAY", null);
            throw new Error("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidEnumConstant() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        java.time.DayOfWeek result = ConstantBootstraps.enumConstant(lookup, "MONDAY", java.time.DayOfWeek.class);
        if (result != java.time.DayOfWeek.MONDAY) {
            throw new Error("Expected DayOfWeek.MONDAY but got " + result);
        }
    }
}
