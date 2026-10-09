/* @test    @summary Test for Stack.peek() method, including exception path when stack is empty    @run main StackWitupTest_Pea3f88de6e48 */

import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest_Pea3f88de6e48 {

    public static void main(String[] args) {
        testPeekNonEmptyStack();
        testPeekEmptyStack();
    }

    private static void testPeekNonEmptyStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        Integer top = stack.peek();
        if (top == null || top != 20) {
            throw new AssertionError("Expected top element to be 20, but was " + top);
        }
    }

    private static void testPeekEmptyStack() {
        Stack<Integer> stack = new Stack<>();
        try {
            stack.peek();
            throw new AssertionError("Expected EmptyStackException to be thrown");
        } catch (EmptyStackException e) {
            // Expected exception, test passes
        }
    }
}
