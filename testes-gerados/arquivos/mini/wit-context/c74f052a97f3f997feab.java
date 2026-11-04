/*
 * @test
 * @summary Test java.util.Stack pop() method for normal and exceptional behavior
 * @run main StackWitupTest
 */

import java.util.EmptyStackException;
import java.util.Stack;

public class StackWitupTest {
    public static void main(String[] args) {
        testPopNormal();
        testPopEmptyStackException();
    }

    private static void testPopNormal() {
        Stack<String> stack = new Stack<>();
        stack.push("first");
        stack.push("second");

        String popped = stack.pop();
        if (!"second".equals(popped)) {
            throw new AssertionError("Expected 'second' but got: " + popped);
        }

        popped = stack.pop();
        if (!"first".equals(popped)) {
            throw new AssertionError("Expected 'first' but got: " + popped);
        }

        if (!stack.empty()) {
            throw new AssertionError("Stack should be empty after popping all elements");
        }
    }

    private static void testPopEmptyStackException() {
        Stack<Object> stack = new Stack<>();
        boolean thrown = false;
        try {
            stack.pop();
        } catch (EmptyStackException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected EmptyStackException when popping empty stack");
        }
    }
}
