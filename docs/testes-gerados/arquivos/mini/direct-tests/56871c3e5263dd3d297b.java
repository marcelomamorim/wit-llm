/*
 * @test
 * @summary Test StackWalker.getMethodType() throws UnsupportedOperationException
 * @run main StackWalkerWitupTest
 */

import java.lang.StackWalker;
import java.lang.invoke.MethodType;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        StackWalker walker = StackWalker.getInstance();
        try {
            MethodType mt = walker.getMethodType();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
