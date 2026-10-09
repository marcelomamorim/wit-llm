/*
 * @test
 * @summary Test java.util.Stack.peek() method behavior including EmptyStackException
 * @run main StackWitupTest_P059b13515124
 */

import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest_P059b13515124 {
    public static void main(String[] args) {
        testPeekOnEmptyStackThrows();
        testPeekReturnsLastPushedElement();
    }

    private static void testPeekOnEmptyStackThrows() {
        Stack<Object> stack = new Stack<>();
        try {
            stack.peek();
            throw new AssertionError("Expected EmptyStackException not thrown");
        } catch (EmptyStackException e) {
            // Expected exception
        }
    }

    private static void testPeekReturnsLastPushedElement() {
        Stack<String> stack = new Stack<>();
        String first = "first";
        String second = "second";
        stack.push(first);
        stack.push(second);
        String peeked = stack.peek();
        if (!peeked.equals(second)) {
            throw new AssertionError("peek() did not return the last pushed element");
        }
        // Ensure peek does not remove the element
        if (stack.size() != 2) {
            throw new AssertionError("peek() should not remove element from stack");
        }
    }
}
