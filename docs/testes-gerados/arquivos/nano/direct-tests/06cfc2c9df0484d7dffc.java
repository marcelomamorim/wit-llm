/* @test    @summary Test for ConstantBootstraps.nullConstant method to ensure it returns null for reference types and throws exception for primitive types.    @run main ConstantBootstrapsWitupTest */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ConstantBootstrapsWitupTest {

    public static void main(String[] args) {
        testNullConstantWithReferenceType();
        testNullConstantWithPrimitiveType();
    }

    static void testNullConstantWithReferenceType() {
        try {
            Object result = java.lang.invoke.ConstantBootstraps.nullConstant(MethodHandles.lookup(), "test", String.class);
            if (result != null) {
                throw new AssertionError("Expected null for reference type, but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for reference type: " + e);
        }
    }

    static void testNullConstantWithPrimitiveType() {
        try {
            java.lang.invoke.ConstantBootstraps.nullConstant(MethodHandles.lookup(), "test", int.class);
            throw new AssertionError("Expected IllegalArgumentException for primitive type, but no exception was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            if (!e.getMessage().contains("not reference")) {
                throw new AssertionError("Exception message does not contain expected text: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
