/*
 * @test
 * @summary Test java.lang.invoke.ConstantBootstraps.primitiveClass method for null checks and argument validation
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;
import sun.invoke.util.Wrapper;

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testNullTypeThrowsNPE();
        testTypeNotClassThrowsIAE();
        testNameLengthZeroThrowsIAE();
        testNameLengthGreaterThanOneThrowsIAE();
        testValidPrimitiveReturnsCorrectClass();
    }

    private static void testNullNameThrowsNPE() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), null, Class.class);
            throw new Error("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullTypeThrowsNPE() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "I", null);
            throw new Error("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTypeNotClassThrowsIAE() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "I", String.class);
            throw new Error("Expected IllegalArgumentException for type not Class.class");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNameLengthZeroThrowsIAE() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "", Class.class);
            throw new Error("Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNameLengthGreaterThanOneThrowsIAE() {
        try {
            ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "II", Class.class);
            throw new Error("Expected IllegalArgumentException for name length > 1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidPrimitiveReturnsCorrectClass() {
        Class<?> result = ConstantBootstraps.primitiveClass(MethodHandles.lookup(), "I", Class.class);
        Class<?> expected = Wrapper.forPrimitiveType('I').primitiveType();
        if (result != expected) {
            throw new Error("Expected primitiveClass to return " + expected + " but got " + result);
        }
    }
}
