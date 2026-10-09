/*
 * @test
 * @summary Test the peek() method of java.util.Stack for normal and empty stack cases
 * @run main StackWitupTest_Pa47cc5a8e9cc
 */

import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest_Pa47cc5a8e9cc {

    public static void main(String[] args) {
        testPeekOnNonEmptyStack();
        testPeekOnEmptyStack();
    }

    private static void testPeekOnNonEmptyStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        Integer top = stack.peek();
        if (top == null || top != 20) {
            throw new AssertionError("Expected top element to be 20, but was " + top);
        }
    }

    private static void testPeekOnEmptyStack() {
        Stack<Integer> stack = new Stack<>();
        try {
            stack.peek();
            throw new AssertionError("Expected EmptyStackException to be thrown");
        } catch (EmptyStackException e) {
            // Expected exception, test passes
        }
    }
}
