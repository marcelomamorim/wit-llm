/*
 * @test
 * @summary Tests that Stack.peek() returns the top item without removing it and throws EmptyStackException when the stack is empty
 * @run main StackWitupTest
 */

import java.util.EmptyStackException;
import java.util.Stack;

public class StackWitupTest {
    public static void main(String[] args) {
        testPeekEmptyThrows();
        testPeekReturnsTopWithoutRemoving();
        testPeekAfterPop();
        testPeekNullTop();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPeekEmptyThrows() {
        Stack<Integer> stack = new Stack<>();
        boolean thrown = false;
        try {
            stack.peek();
        } catch (EmptyStackException e) {
            thrown = true;
        }
        check(thrown, "peek() on empty stack must throw EmptyStackException");
        check(stack.empty(), "stack must remain empty after failed peek()");
    }

    static void testPeekReturnsTopWithoutRemoving() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        check(stack.peek() == 2, "peek() must return the last pushed item");
        check(stack.size() == 2, "peek() must not remove the item");
        check(stack.peek() == 2, "repeated peek() must return the same top item");
        check(stack.size() == 2, "repeated peek() must not change size");
    }

    static void testPeekAfterPop() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        check(stack.pop() == 30, "pop() must return top item");
        check(stack.peek() == 20, "peek() must return new top after pop()");
        stack.pop();
        stack.pop();
        boolean thrown = false;
        try {
            stack.peek();
        } catch (EmptyStackException e) {
            thrown = true;
        }
        check(thrown, "peek() must throw EmptyStackException after all items are popped");
    }

    static void testPeekNullTop() {
        Stack<Integer> stack = new Stack<>();
        stack.push(null);
        check(stack.peek() == null, "peek() must return null when null is the top item");
        check(stack.size() == 1, "peek() must not remove a null top item");
    }
}
