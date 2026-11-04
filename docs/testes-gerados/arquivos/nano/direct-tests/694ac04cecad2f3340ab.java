/* @test    @summary Test the clear() method of CleanerImpl.    @run main CleanerImplWitupTest */

import java.lang.reflect.Method;
import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) throws Exception {
        testClearMethod();
    }

    public static void testClearMethod() throws Exception {
        CleanerImpl cleanerImpl = new CleanerImpl();
        Method clearMethod = CleanerImpl.class.getDeclaredMethod("clear");
        try {
            clearMethod.invoke(cleanerImpl);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof UnsupportedOperationException) {
                System.out.println("clear() method correctly throws UnsupportedOperationException.");
            } else {
                throw e;
            }
        }
    }
}
