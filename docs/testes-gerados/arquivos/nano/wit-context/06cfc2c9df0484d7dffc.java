/* @test    @summary Test for ConstantBootstraps.nullConstant() method behavior and exception paths    @run main ConstantBootstrapsWitupTest */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

public class ConstantBootstrapsWitupTest {

    public static void main(String[] args) {
        testNullTypeThrowsNullPointerException();
        testPrimitiveTypeThrowsIllegalArgumentException();
        testReferenceTypeReturnsNull();
    }

    private static void testNullTypeThrowsNullPointerException() {
        boolean exceptionThrown = false;
        try {
            Object result = ConstantBootstraps.nullConstant(MethodHandles.lookup(), "test", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when type is null");
        }
    }

    private static void testPrimitiveTypeThrowsIllegalArgumentException() {
        boolean exceptionThrown = false;
        try {
            Class<?> primitiveType = int.class;
            Object result = ConstantBootstraps.nullConstant(MethodHandles.lookup(), "test", primitiveType);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for primitive type");
        }
    }

    private static void testReferenceTypeReturnsNull() {
        Object result = ConstantBootstraps.nullConstant(MethodHandles.lookup(), "test", String.class);
        if (result != null) {
            throw new AssertionError("Expected null for reference type, but got: " + result);
        }
    }
}
