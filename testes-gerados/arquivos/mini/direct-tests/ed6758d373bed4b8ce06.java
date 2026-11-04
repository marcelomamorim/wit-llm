/*
 * @test
 * @summary Test java.lang.invoke.ConstantBootstraps.getStaticFinal method behavior
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;
import sun.invoke.util.Wrapper;

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) throws Throwable {
        testGetStaticFinalWithReferenceType();
        testGetStaticFinalWithPrimitiveType();
    }

    private static void testGetStaticFinalWithReferenceType() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String name = "testName";
        Class<?> type = String.class;

        Object result = ConstantBootstraps.getStaticFinal(lookup, name, type);

        // The returned object should be the result of getStaticFinal(lookup, name, type, declaring)
        // where declaring == type for reference types
        // We cannot assert exact value, but result must not be null (unless implementation returns null)
        if (result == null) {
            throw new AssertionError("getStaticFinal returned null for reference type");
        }
    }

    private static void testGetStaticFinalWithPrimitiveType() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String name = "testName";
        Class<?> primitiveType = int.class;

        // For primitive type, declaring is wrapperType (Integer.class for int.class)
        Object result = ConstantBootstraps.getStaticFinal(lookup, name, primitiveType);

        if (result == null) {
            throw new AssertionError("getStaticFinal returned null for primitive type");
        }
    }
}
