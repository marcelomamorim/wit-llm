/* @test
   @summary Tests the peek() method of java.util.Stack for correct behavior and exception handling.
   @run main StackWitupTest_Pfe722e008361
 */
import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest_Pfe722e008361 {
    public static void main(String[] args) {
        testPeekOnEmptyStack();
        testPeekOnNonEmptyStack();
    }

    private static void testPeekOnEmptyStack() {
        Stack<Object> stack = new Stack<>();
        boolean exceptionThrown = false;
        try {
            stack.peek();
        } catch (EmptyStackException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected EmptyStackException when peeking empty stack");
        }
    }

    private static void testPeekOnNonEmptyStack() {
        Stack<String> stack = new Stack<>();
        String element = "test";
        stack.push(element);
        String result = stack.peek();
        if (!result.equals(element)) {
            throw new AssertionError("peek() did not return the last pushed element");
        }
        if (stack.empty()) {
            throw new AssertionError("Stack should not be empty after push");
        }
    }
}
