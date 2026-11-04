/*
 * @test
 * @summary Test java.util.Stack pop() method behavior
 * @run main StackWitupTest
 */

import java.util.EmptyStackException;
import java.util.Stack;

public class StackWitupTest {
    public static void main(String[] args) {
        testPopReturnsLastPushedElement();
        testPopOnEmptyStackThrowsEmptyStackException();
    }

    private static void testPopReturnsLastPushedElement() {
        Stack<String> stack = new Stack<>();
        String pushed = "element";
        stack.push(pushed);
        String popped = stack.pop();
        if (!popped.equals(pushed)) {
            throw new AssertionError("pop() did not return the last pushed element");
        }
        if (!stack.empty()) {
            throw new AssertionError("Stack should be empty after pop()");
        }
    }

    private static void testPopOnEmptyStackThrowsEmptyStackException() {
        Stack<Object> stack = new Stack<>();
        try {
            stack.pop();
            throw new AssertionError("pop() on empty stack did not throw EmptyStackException");
        } catch (EmptyStackException expected) {
            // expected
        }
    }
}
