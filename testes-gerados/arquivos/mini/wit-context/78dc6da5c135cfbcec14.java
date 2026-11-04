/*
 * @test
 * @summary Test StackWalker.forEach method for null argument and normal usage
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.Objects;
import java.util.function.Consumer;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testForEachNullArgument();
        testForEachNormalUsage();
    }

    private static void testForEachNullArgument() {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testForEachNormalUsage() {
        StackWalker walker = StackWalker.getInstance();
        final int[] count = new int[1];
        Consumer<StackFrame> action = new Consumer<StackFrame>() {
            @Override
            public void accept(StackFrame frame) {
                Objects.requireNonNull(frame);
                count[0]++;
            }
        };
        walker.forEach(action);
        if (count[0] == 0) {
            throw new AssertionError("Expected at least one stack frame to be processed");
        }
    }
}
