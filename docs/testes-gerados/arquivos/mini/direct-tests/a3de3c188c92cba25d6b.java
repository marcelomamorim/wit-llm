/*
 * @test
 * @summary Test java.lang.invoke.ConstantBootstraps.primitiveClass method
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;
import sun.invoke.util.Wrapper;

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) {
        testValidPrimitiveClass();
        testNullName();
        testNullType();
        testInvalidTypeClass();
        testInvalidNameLengthZero();
        testInvalidNameLengthMoreThanOne();
    }

    private static void testValidPrimitiveClass() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String name = "I"; // int primitive descriptor
        Class<?> type = Class.class;
        Class<?> result = ConstantBootstraps.primitiveClass(lookup, name, type);
        if (result != int.class) {
            throw new AssertionError("Expected int.class but got " + result);
        }
    }

    private static void testNullName() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            ConstantBootstraps.primitiveClass(lookup, null, Class.class);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullType() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            ConstantBootstraps.primitiveClass(lookup, "I", null);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
        }
    }

    private static void testInvalidTypeClass() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            ConstantBootstraps.primitiveClass(lookup, "I", String.class);
            throw new AssertionError("Expected IllegalArgumentException for invalid type");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testInvalidNameLengthZero() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            ConstantBootstraps.primitiveClass(lookup, "", Class.class);
            throw new AssertionError("Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testInvalidNameLengthMoreThanOne() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            ConstantBootstraps.primitiveClass(lookup, "II", Class.class);
            throw new AssertionError("Expected IllegalArgumentException for name length > 1");
        } catch (IllegalArgumentException expected) {
        }
    }
}