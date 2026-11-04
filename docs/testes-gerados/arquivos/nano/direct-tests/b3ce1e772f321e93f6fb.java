/* @test    @summary Test the clear() method of CleanerImpl class.    @run main CleanerImplWitupTest */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) {
        testClearMethod();
    }

    public static void testClearMethod() {
        CleanerImpl cleanerImpl = new CleanerImpl();
        try {
            cleanerImpl.clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("clear() method correctly throws UnsupportedOperationException.");
            return;
        }
        System.out.println("Error: clear() method did not throw the expected exception.");
        System.exit(1);
    }
}
