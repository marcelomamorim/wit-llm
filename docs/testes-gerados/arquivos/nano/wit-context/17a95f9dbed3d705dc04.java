/*
 * @test
 * @summary Test for MethodHandle.asVarargsCollector with null argument
 * @run main MethodHandleWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleWitupTest {

    public static void main(String[] args) {
        testAsVarargsCollectorNull();
        testAsVarargsCollectorValid();
    }

    static void testAsVarargsCollectorNull() {
        MethodHandle mh = MethodHandles.constant(String.class, "test");
        try {
            mh.asVarargsCollector(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testAsVarargsCollectorValid() {
        MethodType mt = MethodType.methodType(String.class, String[].class);
        MethodHandle mh = MethodHandles.constant(String.class, "test");
        try {
            MethodHandle result = mh.asVarargsCollector(String[].class);
            if (result == null) {
                System.err.println("Resulting MethodHandle is null");
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
