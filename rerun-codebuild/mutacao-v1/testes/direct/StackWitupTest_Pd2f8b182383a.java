/* @test    @summary Test for Stack.peek() method to ensure correct behavior and exception handling.    @run main StackWitupTest_Pd2f8b182383a */

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
            throw new AssertionError("Expected EmptyStackException was not thrown.");
        } catch (EmptyStackException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("EmptyStackException was expected but not thrown.");
        }
    }

    private static void testPeekOnNonEmptyStack() {
        Stack<String> stack = new Stack<>();
        String element = "testElement";
        stack.push(element);
        String peeked = stack.peek();
        if (!peeked.equals(element)) {
            throw new AssertionError("peek() returned: " + peeked + ", expected: " + element);
        }
        if (stack.empty()) {
            throw new AssertionError("Stack should not be empty after push.");
        }
        if (stack.size() != 1) {
            throw new AssertionError("Stack size should be 1 after one push.");
        }
    }
}
