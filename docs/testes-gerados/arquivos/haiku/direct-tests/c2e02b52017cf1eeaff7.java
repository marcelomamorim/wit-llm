/*
 * @test
 * @summary Verifies StackWalker.getCallerClass() returns the caller class when RETAIN_CLASS_REFERENCE is set and throws UnsupportedOperationException otherwise
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.Option;
import java.util.EnumSet;

public class StackWalkerWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static Class<?> callerViaWalker(StackWalker walker) {
        return walker.getCallerClass();
    }

    static Class<?> invokeWithRetain() {
        StackWalker walker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
        return callerViaWalker(walker);
    }

    static Class<?> invokeWithRetainAndDepth() {
        StackWalker walker = StackWalker.getInstance(
                EnumSet.of(Option.RETAIN_CLASS_REFERENCE), 16);
        return callerViaWalker(walker);
    }

    static boolean throwsUnsupported(StackWalker walker) {
        try {
            walker.getCallerClass();
            return false;
        } catch (UnsupportedOperationException e) {
            return true;
        }
    }

    public static void main(String[] args) {
        Class<?> c1 = invokeWithRetain();
        check(c1 == StackWalkerWitupTest.class,
              "getCallerClass with RETAIN_CLASS_REFERENCE should return test class, got " + c1);

        Class<?> c2 = invokeWithRetainAndDepth();
        check(c2 == StackWalkerWitupTest.class,
              "getCallerClass with depth variant should return test class, got " + c2);

        check(throwsUnsupported(StackWalker.getInstance()),
              "default StackWalker.getCallerClass should throw UnsupportedOperationException");

        check(throwsUnsupported(StackWalker.getInstance(Option.SHOW_REFLECT_FRAMES)),
              "StackWalker without RETAIN_CLASS_REFERENCE should throw UnsupportedOperationException");

        check(throwsUnsupported(StackWalker.getInstance(EnumSet.of(Option.SHOW_HIDDEN_FRAMES), 8)),
              "StackWalker with hidden frames only should throw UnsupportedOperationException");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
