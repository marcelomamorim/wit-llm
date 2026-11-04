/*
 * @test
 * @summary Test StackWalker.getDescriptor() throws UnsupportedOperationException
 * @run main StackWalkerWitupTest
 */

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        StackWalker walker = StackWalker.getInstance();
        try {
            walker.getDescriptor();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
