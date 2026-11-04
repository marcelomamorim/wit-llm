/*
 * @test
 * @summary Test for StackWalker forEach method handling null action
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.function.Consumer;

public class StackWalkerWitupTest {

    public static void main(String[] args) {
        testForEachNullAction();
        testForEachValidAction();
    }

    private static void testForEachNullAction() {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.forEach(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testForEachValidAction() {
        StackWalker walker = StackWalker.getInstance();
        final boolean[] called = {false};
        Consumer<StackFrame> action = frame -> {
            called[0] = true;
        };
        try {
            walker.forEach(action);
            if (!called[0]) {
                System.err.println("Action was not called");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
