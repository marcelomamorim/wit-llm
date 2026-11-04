/*
 * @test
 * @summary Test for java.lang.invoke.ConstantBootstraps.getStaticFinal to check null handling and basic behavior
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;
import sun.invoke.util.Wrapper;

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) {
        testNullTypeThrowsNPE();
        testPrimitiveTypeDelegation();
        testReferenceTypeDelegation();
    }

    private static void testNullTypeThrowsNPE() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String name = "anyName";
        Class<?> type = null;
        try {
            ConstantBootstraps.getStaticFinal(lookup, name, type);
            throw new Error("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testPrimitiveTypeDelegation() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String name = "primitiveField";
        Class<?> primitiveType = int.class;

        // The method should delegate to getStaticFinal with wrapper type for primitive
        // We cannot test the full delegation without access to getStaticFinal(lookup, name, type, declaring)
        // but we can test that the declaring class is the wrapper type

        // We test indirectly by calling getStaticFinal and catching any unexpected exceptions
        try {
            Object result = ConstantBootstraps.getStaticFinal(lookup, name, primitiveType);
            // result may be null or any object, we do not assert on it
        } catch (Throwable t) {
            throw new Error("Unexpected exception for primitive type delegation: " + t);
        }
    }

    private static void testReferenceTypeDelegation() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String name = "referenceField";
        Class<?> refType = String.class;

        // The method should delegate to getStaticFinal with the same reference type
        // We test that no exception is thrown
        try {
            Object result = ConstantBootstraps.getStaticFinal(lookup, name, refType);
            // result may be null or any object, no assertion
        } catch (Throwable t) {
            throw new Error("Unexpected exception for reference type delegation: " + t);
        }
    }
}
