/*
 * @test
 * @summary Test CleanerImpl.get() throws UnsupportedOperationException
 * @run main CleanerImplWitupTest
 */

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        CleanerImplWitupTest test = new CleanerImplWitupTest();
        test.testGetThrows();
    }

    public void testGetThrows() {
        jdk.internal.ref.CleanerImpl cleanerImpl = new jdk.internal.ref.CleanerImpl();
        try {
            cleanerImpl.get();
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"get".equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        }
    }
}
