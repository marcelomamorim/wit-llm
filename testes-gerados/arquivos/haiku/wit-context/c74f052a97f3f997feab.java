/*
 * @test
 * @summary Witup-derived tests for Stack.pop()
 * @run main StackWitupTest
 */
import java.util.EmptyStackException;
import java.util.Stack;

public class StackWitupTest {
    public static void main(String[] args) {
        testPopOnEmptyStackThrowsEmptyStackException();
        testPopReturnsTopItemAndRemovesIt();
        testPopOnDrainedStackThrowsEmptyStackException();
        testPopAfterPushReturnsLastPushed();
    }

    static void testPopOnEmptyStackThrowsEmptyStackException() {
        Stack<Integer> stack = new Stack<>();
        try {
            stack.pop();
            throw new RuntimeException("pop() on empty stack did not throw EmptyStackException");
        } catch (EmptyStackException expected) {
            // expected
        }
        if (!stack.empty()) {
            throw new RuntimeException("stack should remain empty after failed pop()");
        }
    }

    static void testPopReturnsTopItemAndRemovesIt() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        String top = stack.pop();
        if (!"c".equals(top)) {
            throw new RuntimeException("pop() returned " + top + " expected c");
        }
        if (stack.size() != 2) {
            throw new RuntimeException("size after pop() is " + stack.size() + " expected 2");
        }
        if (!"b".equals(stack.peek())) {
            throw new RuntimeException("new top after pop() is not b");
        }
    }

    static void testPopOnDrainedStackThrowsEmptyStackException() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.pop();
        try {
            stack.pop();
            throw new RuntimeException("pop() on drained stack did not throw EmptyStackException");
        } catch (EmptyStackException expected) {
            // expected
        }
        if (!stack.empty() || stack.size() != 0) {
            throw new RuntimeException("stack not empty after draining");
        }
    }

    static void testPopAfterPushReturnsLastPushed() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }
        for (int i = 99; i >= 0; i--) {
            Integer v = stack.pop();
            if (v != i) {
                throw new RuntimeException("pop() returned " + v + " expected " + i);
            }
        }
        if (!stack.empty()) {
            throw new RuntimeException("stack not empty after popping all items");
        }
    }
}
