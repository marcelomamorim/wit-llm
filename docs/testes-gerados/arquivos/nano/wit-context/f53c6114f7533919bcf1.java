/* @test    @summary Test for jdk.internal.ref.CleanerImpl.get() method.
 * @run main CleanerImplGetTest
 */

import java.lang.reflect.InvocationTargetException;
import jdk.internal.ref.CleanerImpl;

public class CleanerImplGetTest {

    public static void main(String[] args) {
        testGetMethod();
    }

    public static void testGetMethod() {
        CleanerImpl cleanerImpl = new CleanerImpl();
        try {
            Object result = cleanerImpl.get();
            // Since the method always throws UnsupportedOperationException,
            // reaching this line indicates a failure.
            System.err.println("Expected exception was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes.
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            // Any other exception indicates a test failure.
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
