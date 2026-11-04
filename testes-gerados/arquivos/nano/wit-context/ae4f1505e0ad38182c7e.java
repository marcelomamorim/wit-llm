/*
 * @test
 * @summary Test for MethodHandles.zero(Class<?>) method.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.Wrapper;
import java.lang.reflect.Modifier;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testZeroWithNull();
        testZeroWithPrimitiveType();
        testZeroWithObjectType();
    }

    private static void testZeroWithNull() {
        try {
            MethodHandles.zero(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static void testZeroWithPrimitiveType() {
        try {
            Class<?> primitiveType = int.class;
            MethodHandle mh = MethodHandles.zero(primitiveType);
            if (mh == null) {
                System.err.println("MethodHandle is null for primitive type");
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Unexpected exception for primitive type: " + t);
            System.exit(1);
        }
    }

    private static void testZeroWithObjectType() {
        try {
            Class<?> objectType = String.class;
            MethodHandle mh = MethodHandles.zero(objectType);
            if (mh == null) {
                System.err.println("MethodHandle is null for object type");
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Unexpected exception for object type: " + t);
            System.exit(1);
        }
    }
}
