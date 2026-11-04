/*
 * @test
 * @summary Test java.util.Timer(String name) constructor for expected exceptions and normal behavior
 * @run main TimerWitupTest
 */

import java.util.Timer;

public class TimerWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidName();
        testConstructorWithNullName();
    }

    private static void testConstructorWithValidName() {
        // Test normal construction with a valid non-null name
        String validName = "TestTimer";
        Timer timer = new Timer(validName);
        // The constructor should not throw and the timer thread should start
        // We cannot directly check thread status, but no exception means pass
        timer.cancel(); // cleanup
    }

    private static void testConstructorWithNullName() {
        // According to WIT, passing null name may cause NullPointerException
        // The current code calls thread.setName(name) which throws NPE if name is null
        try {
            new Timer(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception caught, test passes
        }
    }
}
