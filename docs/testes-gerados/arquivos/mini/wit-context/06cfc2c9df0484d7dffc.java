/*
 * @test
 * @summary Test java.lang.invoke.ConstantBootstraps.nullConstant method behavior
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) {
        testNullTypeThrowsNPE();
        testPrimitiveTypeThrowsIAE();
        testReferenceTypeReturnsNull();
    }

    private static void testNullTypeThrowsNPE() {
        try {
            ConstantBootstraps.nullConstant(MethodHandles.lookup(), "name", null);
            throw new Error("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPrimitiveTypeThrowsIAE() {
        try {
            ConstantBootstraps.nullConstant(MethodHandles.lookup(), "name", int.class);
            throw new Error("Expected IllegalArgumentException for primitive type");
        } catch (IllegalArgumentException expected) {
            String msg = expected.getMessage();
            if (msg == null || !msg.contains("not reference")) {
                throw new Error("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }

    private static void testReferenceTypeReturnsNull() {
        Object result = ConstantBootstraps.nullConstant(MethodHandles.lookup(), "name", String.class);
        if (result != null) {
            throw new Error("Expected null return for reference type");
        }
    }
}
