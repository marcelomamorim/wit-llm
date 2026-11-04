/*
 * @test
 * @summary Test MethodHandles.iteratedLoop with various argument combinations
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testIteratedLoopWithNullIterator();
        testIteratedLoopWithValidIterator();
        testIteratedLoopReturnTypeVoid();
    }

    // Test iteratedLoop with null iterator, valid init and body
    static void testIteratedLoopWithNullIterator() throws Throwable {
        // init: returns Integer 0
        MethodHandle init = MethodHandles.constant(Integer.class, 0);

        // body: (Integer, Integer) -> Integer, sums two integers
        MethodHandle body = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "sum", MethodType.methodType(Integer.class, Integer.class, Integer.class));

        MethodHandle loop = MethodHandles.iteratedLoop(null, init, body);
        // The returned MethodHandle should not be null
        if (loop == null) throw new AssertionError("iteratedLoop returned null with null iterator");

        // The loop handle should accept an Iterable argument (List<Integer>)
        List<Integer> list = Arrays.asList(1, 2, 3);
        Object result = loop.invoke(list);
        // Result should be Integer (sum of list elements starting from 0)
        if (!(result instanceof Integer)) throw new AssertionError("Result is not Integer");
    }

    // Test iteratedLoop with a valid iterator MethodHandle
    static void testIteratedLoopWithValidIterator() throws Throwable {
        // iterator: (Iterable) -> Iterator
        MethodHandle iterator = MethodHandles.lookup().findVirtual(Iterable.class, "iterator", MethodType.methodType(Iterator.class));

        // init: returns Integer 0
        MethodHandle init = MethodHandles.constant(Integer.class, 0);

        // body: (Integer, Integer) -> Integer, sums two integers
        MethodHandle body = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "sum", MethodType.methodType(Integer.class, Integer.class, Integer.class));

        MethodHandle loop = MethodHandles.iteratedLoop(iterator, init, body);
        if (loop == null) throw new AssertionError("iteratedLoop returned null with valid iterator");

        List<Integer> list = Arrays.asList(1, 2, 3);
        Object result = loop.invoke(list);
        if (!(result instanceof Integer)) throw new AssertionError("Result is not Integer");
    }

    // Test iteratedLoop with body returning void
    static void testIteratedLoopReturnTypeVoid() throws Throwable {
        // init: returns Integer 0
        MethodHandle init = MethodHandles.constant(Integer.class, 0);

        // body: (Integer) -> void, increments a static counter
        MethodHandle body = MethodHandles.lookup().findStatic(MethodHandlesWitupTest.class, "incrementCounter", MethodType.methodType(void.class, Integer.class));

        MethodHandle loop = MethodHandles.iteratedLoop(null, init, body);
        if (loop == null) throw new AssertionError("iteratedLoop returned null with void return body");

        List<Integer> list = Arrays.asList(1, 2, 3);
        loop.invoke(list);
        // No exception means success
    }

    // Helper static method for sum
    public static Integer sum(Integer a, Integer b) {
        return a + b;
    }

    // Static counter for incrementCounter
    static int counter = 0;

    // Helper static method for void body
    public static void incrementCounter(Integer v) {
        counter += v;
    }
}
