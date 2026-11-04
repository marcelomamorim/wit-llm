/*
 * @test
 * @summary getCallerClass must throw UnsupportedOperationException without RETAIN_CLASS_REFERENCE and return the caller class otherwise
 * @run main StackWalkerWitupTest
 */

import java.util.EnumSet;
import java.lang.StackWalker.Option;

public class StackWalkerWitupTest {
    static Class<?> viaHelper(StackWalker w) {
        return w.getCallerClass();
    }

    public static void main(String[] args) {
        StackWalker retain = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
        Class<?> c = viaHelper(retain);
        if (c != StackWalkerWitupTest.class) {
            throw new RuntimeException("unexpected caller class: " + c);
        }

        StackWalker retainSet = StackWalker.getInstance(EnumSet.of(Option.RETAIN_CLASS_REFERENCE));
        Class<?> c2 = viaHelper(retainSet);
        if (c2 != StackWalkerWitupTest.class) {
            throw new RuntimeException("unexpected caller class from set-based walker: " + c2);
        }

        StackWalker plain = StackWalker.getInstance();
        boolean thrown = false;
        try {
            plain.getCallerClass();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException without RETAIN_CLASS_REFERENCE");
        }
    }
}
