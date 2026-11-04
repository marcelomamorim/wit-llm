/*
 * @test
 * @summary Test explicitCastArguments method for handling type mismatches.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testExplicitCastArgumentsTypeMismatch();
        testExplicitCastArgumentsSameType();
        testExplicitCastArgumentsEquivalentType();
    }

    private static void testExplicitCastArgumentsTypeMismatch() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestMethods.class, "sampleMethod", MethodType.methodType(void.class, int.class));
            MethodType newType = MethodType.methodType(void.class, String.class);
            MethodHandles.explicitCastArguments(mh, newType);
            // Expect WrongMethodTypeException due to parameter count mismatch
            System.out.println("Failed: Expected WrongMethodTypeException not thrown.");
        } catch (WrongMethodTypeException e) {
            System.out.println("Passed: Caught expected WrongMethodTypeException.");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testExplicitCastArgumentsSameType() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestMethods.class, "sampleMethod", MethodType.methodType(void.class, int.class));
            MethodType sameType = MethodType.methodType(void.class, int.class);
            MethodHandle result = MethodHandles.explicitCastArguments(mh, sameType);
            if (result == mh) {
                System.out.println("Passed: Returned same handle for identical types.");
            } else {
                System.out.println("Failed: Did not return same handle for identical types.");
            }
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testExplicitCastArgumentsEquivalentType() {
        try {
            MethodHandle mh = MethodHandles.lookup().findStatic(TestMethods.class, "sampleMethod", MethodType.methodType(void.class, int.class));
            MethodType equivalentType = MethodType.methodType(void.class, int.class);
            MethodHandle result = MethodHandles.explicitCastArguments(mh, equivalentType);
            if (result != null) {
                System.out.println("Passed: Successfully cast to equivalent type.");
            } else {
                System.out.println("Failed: Result is null for equivalent type.");
            }
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
        }
    }

    public static class TestMethods {
        public static void sampleMethod(int x) {
            // dummy method for lookup
        }
    }
}
