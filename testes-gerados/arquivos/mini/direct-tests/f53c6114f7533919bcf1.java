/*
 * @test
 * @summary Test that CleanerImpl.get() throws UnsupportedOperationException
 * @run main CleanerImplWitupTest
 */

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        jdk.internal.ref.CleanerImpl cleanerImpl = new jdk.internal.ref.CleanerImpl();
        try {
            cleanerImpl.get();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"get".equals(e.getMessage())) {
                throw new Error("Exception message mismatch: " + e.getMessage());
            }
        }
    }
}
