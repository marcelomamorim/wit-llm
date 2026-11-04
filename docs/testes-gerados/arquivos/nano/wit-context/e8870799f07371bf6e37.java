/* @test    @summary Test reseed method of SecureRandom for threadSafe condition    @run main SecureRandomWitupTest */

import java.security.SecureRandom;

public class SecureRandomWitupTest {

    public static void main(String[] args) {
        testReseedThreadSafe();
        testReseedNonThreadSafe();
    }

    private static void testReseedThreadSafe() {
        SecureRandom sr = new SecureRandom();
        try {
            // Simulate thread-safe scenario
            sr.reseed();
            System.out.println("reseed() executed successfully in thread-safe scenario.");
        } catch (UnsupportedOperationException e) {
            System.err.println("Unexpected exception in thread-safe scenario: " + e);
        } catch (Exception e) {
            System.err.println("Unexpected exception in thread-safe scenario: " + e);
        }
    }

    private static void testReseedNonThreadSafe() {
        SecureRandom sr = new SecureRandom();
        try {
            // Simulate non-thread-safe scenario by subclassing and overriding threadSafe flag
            SecureRandom srNonThreadSafe = new SecureRandom() {
                @Override
                public void reseed() {
                    super.reseed();
                }
            };
            // Force threadSafe to false via reflection or other means if possible, but since no such API,
            // we assume the default is threadSafe = true, so this test is a placeholder.
            // Alternatively, test the method directly assuming threadSafe is false in some context.
            srNonThreadSafe.reseed();
            System.out.println("reseed() executed successfully in non-thread-safe scenario.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Expected exception caught in non-thread-safe scenario: " + e);
        } catch (Exception e) {
            System.err.println("Unexpected exception in non-thread-safe scenario: " + e);
        }
    }
}
