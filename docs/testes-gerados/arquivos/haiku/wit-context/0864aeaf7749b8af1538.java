/*
 * @test
 * @summary Verify Stack.peek() returns the top element and throws EmptyStackException when the stack is empty
 * @run main StackWitupTest
 */

import java.util.EmptyStackException;
import java.util.Stack;

public class StackWitupTest {
    public static void main(String[] args) {
        testPeekOnEmptyStackThrows();
        testPeekReturnsTopWithoutRemoving();
        testPeekAfterPopReturnsNewTop();
        testPeekAfterEmptyingThrowsAgain();
    }

    static void testPeekOnEmptyStackThrows() {
        Stack<String> stack = new Stack<>();
        try {
            stack.peek();
            throw new AssertionError("Expected EmptyStackException from peek() on empty stack");
        } catch (EmptyStackException expected) {
        }
    }

    static void testPeekReturnsTopWithoutRemoving() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Integer top = stack.peek();
        if (top.intValue() != 3) {
            throw new AssertionError("Expected top 3 but was " + top);
        }
        if (stack.size() != 3) {
            throw new AssertionError("peek() must not remove elements; size was " + stack.size());
        }
        if (stack.peek().intValue() != 3) {
            throw new AssertionError("Repeated peek() must return same top element");
        }
    }

    static void testPeekAfterPopReturnsNewTop() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.pop();
        String top = stack.peek();
        if (!"a".equals(top)) {
            throw new AssertionError("Expected \"a\" but was " + top);
        }
    }

    static void testPeekAfterEmptyingThrowsAgain() {
        Stack<String> stack = new Stack<>();
        stack.push("x");
        stack.pop();
        try {
            stack.peek();
            throw new AssertionError("Expected EmptyStackException after stack was emptied");
        } catch (EmptyStackException expected) {
        }
    }
}
