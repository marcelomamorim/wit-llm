/*
 * @test
 * @summary Test explicitCastArguments method of MethodHandles for correct behavior and exception throwing
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testExplicitCastArgumentsValid();
        testExplicitCastArgumentsThrowsWrongMethodTypeException();
    }

    static void testExplicitCastArgumentsValid() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class));
        MethodType newType = MethodType.methodType(String.class, int.class);

        // Should return the same method handle if types are equal
        MethodHandle result1 = MethodHandles.explicitCastArguments(mh, newType);
        if (result1 == null) throw new AssertionError("explicitCastArguments returned null for equal types");

        // Should return a converted method handle if types are explicitly cast equivalent
        MethodType newType2 = MethodType.methodType(CharSequence.class, int.class);
        MethodHandle result2 = MethodHandles.explicitCastArguments(mh, newType2);
        if (result2 == null) throw new AssertionError("explicitCastArguments returned null for explicitCastEquivalentToAsType");

        // Should return a converted method handle for different but compatible types
        MethodType newType3 = MethodType.methodType(CharSequence.class, Integer.TYPE);
        MethodHandle result3 = MethodHandles.explicitCastArguments(mh, newType3);
        if (result3 == null) throw new AssertionError("explicitCastArguments returned null for different compatible types");
    }

    static void testExplicitCastArgumentsThrowsWrongMethodTypeException() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            MethodHandle mh = lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class));
            // newType with different parameter count triggers WrongMethodTypeException
            MethodType wrongType = MethodType.methodType(String.class, int.class, int.class);
            MethodHandles.explicitCastArguments(mh, wrongType);
            throw new AssertionError("Expected WrongMethodTypeException not thrown");
        } catch (WrongMethodTypeException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
