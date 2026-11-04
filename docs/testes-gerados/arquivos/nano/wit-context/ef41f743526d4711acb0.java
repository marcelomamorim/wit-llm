/* @test    @summary Test for JumboEnumSet.remove() method to verify exception throwing when lastReturned is 0    @run main JumboEnumSetWitupTest */

import java.util.JumboEnumSet;

public class JumboEnumSetWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsExceptionWhenLastReturnedIsZero();
    }

    public static void testRemoveThrowsExceptionWhenLastReturnedIsZero() {
        JumboEnumSet<Integer> set = createEmptyJumboEnumSet();
        // Simulate internal state where lastReturned == 0
        set.setLastReturned(0);
        try {
            set.remove();
            // Fail if no exception is thrown
            throw new AssertionError("Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            // Expected exception, test passes
        }
    }

    private static JumboEnumSet<Integer> createEmptyJumboEnumSet() {
        // Create an instance with minimal setup
        // Since constructor is package-private, assume a factory method or reflection; here, we simulate with subclassing or direct instantiation if possible
        // For testing, we can create an anonymous subclass or assume access to constructor for test purposes
        return new JumboEnumSet<Integer>(Integer.class, new Integer[]{}) {
            public void setLastReturned(int value) {
                // Access to internal field for testing purposes
                this.lastReturned = value;
            }
        };
    }
}
