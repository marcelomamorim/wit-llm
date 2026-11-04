/*
 * @test
 * @summary Test CleanerImpl.clear() throws UnsupportedOperationException
 * @run main CleanerImplWitupTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        CleanerImpl cleanerImpl = new CleanerImpl();
        try {
            cleanerImpl.clear();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"clear".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
