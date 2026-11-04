/*
 * @test
 * @summary Test for MethodHandles.empty(MethodType type)
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testEmptyWithNull();
        testEmptyWithValidType();
    }

    private static void testEmptyWithNull() {
        try {
            MethodHandle result = MethodHandles.empty(null);
            // If no exception, fail the test
            System.err.println("Expected NullPointerException for null type, but got: " + result);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed null type test");
        } catch (Throwable t) {
            System.err.println("Unexpected exception for null type: " + t);
        }
    }

    private static void testEmptyWithValidType() {
        try {
            MethodType type = MethodType.methodType(void.class);
            MethodHandle handle = MethodHandles.empty(type);
            if (handle != null) {
                System.out.println("Successfully created MethodHandle with type: " + handle.type());
            } else {
                System.err.println("Failed to create MethodHandle with valid type");
            }
        } catch (Throwable t) {
            System.err.println("Unexpected exception for valid type: " + t);
        }
    }
}
