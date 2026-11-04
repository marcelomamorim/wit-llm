/* @test    @summary Test the pop() method of java.util.Stack for correct behavior and exception handling    @run main StackWitupTest */

import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest {

    public static void main(String[] args) {
        testPopWithItems();
        testPopOnEmptyStack();
    }

    private static void testPopWithItems() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        int top = stack.pop();
        if (top != 3) {
            throw new AssertionError("Expected top element to be 3, but was " + top);
        }
        if (stack.size() != 2) {
            throw new AssertionError("Expected stack size to be 2 after pop, but was " + stack.size());
        }
        int secondTop = stack.pop();
        if (secondTop != 2) {
            throw new AssertionError("Expected second top element to be 2, but was " + secondTop);
        }
        int last = stack.pop();
        if (last != 1) {
            throw new AssertionError("Expected last element to be 1, but was " + last);
        }
        if (!stack.empty()) {
            throw new AssertionError("Expected stack to be empty after popping all elements");
        }
    }

    private static void testPopOnEmptyStack() {
        Stack<Integer> stack = new Stack<>();
        boolean exceptionThrown = false;
        try {
            stack.pop();
        } catch (EmptyStackException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected EmptyStackException when popping from empty stack");
        }
    }
}
