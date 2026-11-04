/* @test    @summary Test Timer constructor with name parameter for normal behavior and exception scenarios    @run main TimerWitupTest */

import java.util.Timer;

public class TimerWitupTest {

    public static void main(String[] args) {
        testTimerConstructorWithName();
    }

    private static void testTimerConstructorWithName() {
        // Test case: normal construction with a non-null name
        try {
            Timer timer = new Timer("TestTimer");
            if (timer == null) {
                throw new AssertionError("Timer instance should not be null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during normal Timer construction", e);
        }

        // Test case: construction with null name, expecting NullPointerException
        try {
            Timer timer = new Timer((String) null);
            throw new AssertionError("Expected NullPointerException when passing null name");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Expected NullPointerException, but got: " + e);
        }

        // Additional test: simulate scenario where thread.setName(null) causes NullPointerException
        // Note: Since thread is private, we cannot directly manipulate it; this test assumes the constructor throws if setName(null) is called.
        // As we cannot access thread directly, this scenario is covered by the previous null name test.
    }
}
