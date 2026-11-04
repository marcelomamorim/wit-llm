/*
 * @test
 * @summary Test the pop() method of java.util.Stack for normal and exceptional behavior.
 * @run main StackWitupTest
 */

import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest {

    public static void main(String[] args) {
        testPopOnEmptyStack();
        testPopOnNonEmptyStack();
    }

    private static void testPopOnEmptyStack() {
        Stack<Object> stack = new Stack<>();
        boolean threwException = false;
        try {
            stack.pop();
        } catch (EmptyStackException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected EmptyStackException when popping from empty stack");
        }
    }

    private static void testPopOnNonEmptyStack() {
        Stack<String> stack = new Stack<>();
        String element = "test";
        stack.push(element);
        String popped = stack.pop();
        if (!popped.equals(element)) {
            throw new AssertionError("Popped element does not match pushed element");
        }
        if (stack.size() != 0) {
            throw new AssertionError("Stack should be empty after pop");
        }
    }
}
