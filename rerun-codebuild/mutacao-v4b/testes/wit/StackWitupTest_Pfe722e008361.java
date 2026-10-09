/* @test
   @summary Testa o método peek() da classe java.util.Stack, incluindo o caso de exceção.
   @run main StackWitupTest_Pfe722e008361
 */
import java.util.Stack;
import java.util.EmptyStackException;

public class StackWitupTest_Pfe722e008361 {
    public static void main(String[] args) {
        testPeekNormal();
        testPeekEmptyStack();
    }

    private static void testPeekNormal() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        Integer top = stack.peek();
        if (top == null || top != 20) {
            throw new AssertionError("Expected top to be 20, but was " + top);
        }
        if (stack.size() != 2) {
            throw new AssertionError("Stack size changed after peek");
        }
    }

    private static void testPeekEmptyStack() {
        Stack<Integer> stack = new Stack<>();
        try {
            stack.peek();
            throw new AssertionError("Expected EmptyStackException to be thrown");
        } catch (EmptyStackException e) {
            // expected
        }
    }
}
