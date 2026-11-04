/*
 * @test
 * @summary Test StackWalker.getMethodType() method for default behavior.
 * @run main StackWalkerWitupTest
 */
import java.lang.StackWalker;
import java.lang.invoke.MethodType;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testGetMethodType();
    }

    public static void testGetMethodType() {
        StackWalker walker = StackWalker.getInstance();
        try {
            MethodType result = walker.getMethodType();
            // Since the default implementation throws UnsupportedOperationException,
            // reaching this line indicates a failure.
            System.out.println("Unexpected success: getMethodType() returned: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Expected exception caught: " + e);
        } catch (Throwable t) {
            System.out.println("Unexpected exception: " + t);
        }
    }
}
