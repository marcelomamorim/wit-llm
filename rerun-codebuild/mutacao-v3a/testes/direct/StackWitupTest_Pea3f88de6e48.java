/*
 * @test    @summary Test the peek() method of java.util.Stack.
 * @run main StackWitupTest_Pea3f88de6e48
 */

import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest_Pea3f88de6e48 {
    public static void main(String[] args) {
        testPeekOnNonEmptyStack();
        testPeekOnEmptyStack();
    }

    static void testPeekOnNonEmptyStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        int top = stack.peek();
        if (top != 20) {
            throw new AssertionError("Expected top to be 20, but was " + top);
        }
    }

    static void testPeekOnEmptyStack() {
        Stack<Integer> stack = new Stack<>();
        try {
            stack.peek();
            throw new AssertionError("Expected EmptyStackException to be thrown");
        } catch (EmptyStackException e) {
            // expected
        }
    }
}
