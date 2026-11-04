/*
 * @test
 * @summary Test CleanerImpl.get() throws UnsupportedOperationException
 * @run main CleanerImplWitupTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        CleanerImpl cleanerImpl = new CleanerImpl();
        try {
            cleanerImpl.get();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"get".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
