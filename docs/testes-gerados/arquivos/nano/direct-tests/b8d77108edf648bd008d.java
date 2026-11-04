/*
 * @test
 * @summary Test the whileLoop method in MethodHandles to ensure it executes correctly with simple loop conditions.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testWhileLoopBasic();
    }

    static void testWhileLoopBasic() throws Throwable {
        // Create a counter variable
        final int[] counter = {0};

        // Define the init method handle: initializes counter to 0
        MethodHandle init = MethodHandles.constant(int.class, 0);

        // Define the predicate method handle: continue while counter < 5
        MethodHandle pred = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "lessThanFive", MethodType.methodType(boolean.class, int.class)).bindTo(counter[0]);

        // Define the body method handle: increment counter
        MethodHandle body = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "increment", MethodType.methodType(int.class, int.class)).bindTo(counter);

        // Call whileLoop
        MethodHandle loopHandle = MethodHandles.whileLoop(init, pred, body);

        // Invoke the loop
        Object result = loopHandle.invoke();

        // After loop, counter should be 5
        if (counter[0] != 5) {
            throw new AssertionError("Counter expected to be 5 but was " + counter[0]);
        }
        // The return value should be null or void, depending on the body type
        // Since body returns int, the loop's return type is int, but the value is not used here
        System.out.println("Test passed: counter = " + counter[0]);
    }

    public static boolean lessThanFive(int value) {
        return value < 5;
    }

    public static int increment(int value) {
        return value + 1;
    }
}
