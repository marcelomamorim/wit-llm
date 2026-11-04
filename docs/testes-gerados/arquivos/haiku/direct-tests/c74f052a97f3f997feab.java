/*
 * @test
 * @summary Verify Stack.pop returns the top element, removes it, and throws EmptyStackException when empty
 * @run main StackWitupTest
 */

import java.util.EmptyStackException;
import java.util.Stack;

public class StackWitupTest {
    public static void main(String[] args) {
        testPopReturnsTopAndRemovesIt();
        testPopFollowsLifoOrder();
        testPopOnNewStackThrows();
        testPopOnDrainedStackThrows();
        testPopReturnsNullElement();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPopReturnsTopAndRemovesIt() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        String top = stack.pop();
        check("b".equals(top), "pop should return top element 'b' but was " + top);
        check(stack.size() == 1, "size after pop should be 1 but was " + stack.size());
        check("a".equals(stack.peek()), "remaining top should be 'a'");
    }

    static void testPopFollowsLifoOrder() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i <= 5; i++) {
            stack.push(i);
        }
        for (int expected = 5; expected >= 1; expected--) {
            Integer actual = stack.pop();
            check(actual.intValue() == expected,
                  "expected " + expected + " but got " + actual);
        }
        check(stack.empty(), "stack should be empty after popping all elements");
    }

    static void testPopOnNewStackThrows() {
        Stack<String> stack = new Stack<>();
        try {
            stack.pop();
            throw new RuntimeException("pop on empty stack should throw EmptyStackException");
        } catch (EmptyStackException expected) {
            // expected
        }
        check(stack.size() == 0, "size should remain 0 after failed pop");
    }

    static void testPopOnDrainedStackThrows() {
        Stack<String> stack = new Stack<>();
        stack.push("x");
        stack.pop();
        try {
            stack.pop();
            throw new RuntimeException("pop on drained stack should throw EmptyStackException");
        } catch (EmptyStackException expected) {
            // expected
        }
    }

    static void testPopReturnsNullElement() {
        Stack<String> stack = new Stack<>();
        stack.push(null);
        String value = stack.pop();
        check(value == null, "pop should return the null element pushed");
        check(stack.size() == 0, "size should be 0 after popping null element");
    }
}
