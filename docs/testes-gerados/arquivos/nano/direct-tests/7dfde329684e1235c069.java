/*
 * @test
 * @summary Test iteratedLoop method for various scenarios
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testIteratedLoopWithNullIterator();
        testIteratedLoopWithCustomIterator();
        testIteratedLoopWithVoidReturnType();
    }

    private static void testIteratedLoopWithNullIterator() {
        try {
            MethodHandle init = MethodHandles.constant(String.class, "start");
            MethodHandle body = MethodHandles.dropArguments(MethodHandles.constant(String.class, "body"), 0, Object.class);
            MethodHandle result = MethodHandles.iteratedLoop(null, init, body);
            Object res = result.invoke();
            if (!"start".equals(res)) {
                throw new AssertionError("Expected 'start', got: " + res);
            }
        } catch (Throwable t) {
            throw new RuntimeException("Failed testIteratedLoopWithNullIterator", t);
        }
    }

    private static void testIteratedLoopWithCustomIterator() {
        try {
            MethodHandle init = MethodHandles.constant(Iterator.class, new Iterator() {
                int count = 0;
                public boolean hasNext() { return count < 2; }
                public Object next() { return count++; }
            });
            MethodHandle body = MethodHandles.dropArguments(MethodHandles.constant(String.class, "body"), 0, Object.class);
            MethodHandle iterator = init;
            MethodHandle result = MethodHandles.iteratedLoop(iterator, init, body);
            Object res = result.invoke();
            if (!"body".equals(res)) {
                throw new AssertionError("Expected 'body', got: " + res);
            }
        } catch (Throwable t) {
            throw new RuntimeException("Failed testIteratedLoopWithCustomIterator", t);
        }
    }

    private static void testIteratedLoopWithVoidReturnType() {
        try {
            MethodHandle init = MethodHandles.constant(String.class, "start");
            MethodHandle body = MethodHandles.dropArguments(MethodHandles.constant(Void.TYPE, null), 0, Object.class);
            MethodHandle result = MethodHandles.iteratedLoop(null, init, body);
            Object res = result.invoke();
            if (res != null) {
                throw new AssertionError("Expected null, got: " + res);
            }
        } catch (Throwable t) {
            throw new RuntimeException("Failed testIteratedLoopWithVoidReturnType", t);
        }
    }
}
