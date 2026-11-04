/*
 * @test
 * @summary Test for CleanerImpl.clear() method.
 * @run main CleanerImplWitupTest
 */

import java.lang.reflect.Method;
import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) throws Exception {
        testClearMethodThrowsException();
    }

    public static void testClearMethodThrowsException() throws Exception {
        CleanerImpl instance = new CleanerImpl();
        Method clearMethod = CleanerImpl.class.getDeclaredMethod("clear");
        try {
            clearMethod.invoke(instance);
            System.out.println("Test Failed: No exception thrown");
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof UnsupportedOperationException) {
                System.out.println("Test Passed: UnsupportedOperationException thrown as expected");
            } else {
                System.out.println("Test Failed: Unexpected exception: " + cause);
            }
        }
    }
}
