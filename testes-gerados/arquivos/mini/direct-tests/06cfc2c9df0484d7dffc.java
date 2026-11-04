/*
 * @test
 * @summary Test java.lang.invoke.ConstantBootstraps.nullConstant method
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) {
        testNullConstantWithReferenceType();
        testNullConstantWithPrimitiveType();
    }

    private static void testNullConstantWithReferenceType() {
        Object result = ConstantBootstraps.nullConstant(MethodHandles.lookup(), "name", String.class);
        if (result != null) {
            throw new AssertionError("Expected null for reference type, got: " + result);
        }
    }

    private static void testNullConstantWithPrimitiveType() {
        try {
            ConstantBootstraps.nullConstant(MethodHandles.lookup(), "name", int.class);
            throw new AssertionError("Expected IllegalArgumentException for primitive type");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not reference")) {
                throw new AssertionError("Exception message does not contain expected text: " + msg);
            }
        }
    }
}
