/* @test    @summary Test for Stack.peek() method to verify behavior when stack is empty and non-empty    @run main StackWitupTest_Pd2f8b182383a */

import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest_Pd2f8b182383a {
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
            throw new AssertionError("Expected EmptyStackException when peeking on empty stack");
        }
    }

    private static void testPeekOnNonEmptyStack() {
        Stack<String> stack = new Stack<>();
        String element = "test";
        stack.push(element);
        Object result = stack.peek();
        if (result == null || !result.equals(element)) {
            throw new AssertionError("Peek did not return the last pushed element");
        }
        if (stack.empty()) {
            throw new AssertionError("Stack should not be empty after peek on non-empty stack");
        }
    }
}
