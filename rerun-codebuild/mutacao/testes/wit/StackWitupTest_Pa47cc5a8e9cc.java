/*
 * @test
 * @summary Testa o método peek() da classe java.util.Stack para verificar comportamento esperado e exceções.
 * @run main StackWitupTest_Pa47cc5a8e9cc
 */

import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest_Pa47cc5a8e9cc {

    public static void main(String[] args) {
        testPeekEmptyStack();
        testPeekNonEmptyStack();
    }

    private static void testPeekEmptyStack() {
        Stack<Object> stack = new Stack<>();
        boolean exceptionThrown = false;
        try {
            stack.peek();
        } catch (EmptyStackException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected EmptyStackException when peeking empty stack");
        }
    }

    private static void testPeekNonEmptyStack() {
        Stack<String> stack = new Stack<>();
        String element = "test";
        stack.push(element);
        Object result = stack.peek();
        if (result == null || !result.equals(element)) {
            throw new AssertionError("peek() did not return the last pushed element");
        }
        if (stack.empty()) {
            throw new AssertionError("Stack should not be empty after push");
        }
    }
}
