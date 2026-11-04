/*
 * @test
 * @summary Test explicitCastArguments method of MethodHandles
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testSameTypeReturnsSameHandle();
        testExplicitCastEquivalentUsesAsType();
        testDifferentTypeUsesMakePairwiseConvert();
    }

    static void testSameTypeReturnsSameHandle() throws Throwable {
        MethodHandles mh = new MethodHandles(); // private constructor, so use MethodHandles.lookup()
        MethodHandle target = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "staticMethod", MethodType.methodType(String.class, int.class));
        MethodType type = target.type();
        MethodHandle result = invokeExplicitCastArguments(target, type);
        if (result != target) {
            throw new AssertionError("Expected same MethodHandle instance when types are equal");
        }
    }

    static void testExplicitCastEquivalentUsesAsType() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "staticMethod", MethodType.methodType(String.class, int.class));
        MethodType newType = MethodType.methodType(Object.class, Integer.class);
        MethodHandle result = invokeExplicitCastArguments(target, newType);
        // The result should be a fixed arity asType conversion
        // We test that the type of result is newType
        if (!result.type().equals(newType)) {
            throw new AssertionError("Resulting MethodHandle type should be newType");
        }
        // We test that the result is not the same as target
        if (result == target) {
            throw new AssertionError("Resulting MethodHandle should not be the same as target");
        }
    }

    static void testDifferentTypeUsesMakePairwiseConvert() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "staticMethod", MethodType.methodType(String.class, int.class));
        MethodType newType = MethodType.methodType(String.class, long.class);
        MethodHandle result = invokeExplicitCastArguments(target, newType);
        if (!result.type().equals(newType)) {
            throw new AssertionError("Resulting MethodHandle type should be newType");
        }
        if (result == target) {
            throw new AssertionError("Resulting MethodHandle should not be the same as target");
        }
    }

    // Helper method to call the package-private explicitCastArguments via reflection
    private static MethodHandle invokeExplicitCastArguments(MethodHandle target, MethodType newType) throws Throwable {
        java.lang.reflect.Method m = MethodHandles.class.getDeclaredMethod("explicitCastArguments", MethodHandle.class, MethodType.class);
        m.setAccessible(true);
        return (MethodHandle) m.invoke(null, target, newType);
    }

    // Dummy static method for MethodHandle lookup
    public static String staticMethod(int x) {
        return "Value: " + x;
    }
}
