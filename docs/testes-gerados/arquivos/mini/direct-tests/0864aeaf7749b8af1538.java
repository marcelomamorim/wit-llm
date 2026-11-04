/*
 * @test
 * @summary Test java.util.Stack.peek() method behavior
 * @run main StackWitupTest
 */

import java.util.EmptyStackException;
import java.util.Stack;

public class StackWitupTest {
    public static void main(String[] args) {
        testPeekOnEmptyStack();
        testPeekOnNonEmptyStack();
    }

    private static void testPeekOnEmptyStack() {
        Stack<Object> stack = new Stack<>();
        try {
            stack.peek();
            throw new RuntimeException("Expected EmptyStackException not thrown");
        } catch (EmptyStackException e) {
            // Expected exception
        }
    }

    private static void testPeekOnNonEmptyStack() {
        Stack<String> stack = new Stack<>();
        stack.push("first");
        stack.push("second");

        String top = stack.peek();
        if (!"second".equals(top)) {
            throw new RuntimeException("peek() did not return the last pushed element");
        }

        // Ensure peek does not remove the element
        if (stack.size() != 2) {
            throw new RuntimeException("peek() should not remove the element from the stack");
        }
    }
}
