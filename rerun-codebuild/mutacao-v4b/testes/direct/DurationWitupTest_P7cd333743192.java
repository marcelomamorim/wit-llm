/* @test
   @summary Tests the dividedBy(long) method of java.time.Duration.
   @run main DurationWitupTest_P7cd333743192
 */
import java.time.Duration;
public class DurationWitupTest_P7cd333743192 {
    public static void main(String[] args) {
        testDividedByNormal();
        testDividedByZero();
        testDividedByOne();
    }
    private static void testDividedByNormal() {
        Duration duration = Duration.ofSeconds(10);
        Duration result = duration.dividedBy(2);
        long seconds = result.getSeconds();
        if (seconds != 5) {
            throw new AssertionError("Expected 5 seconds, but got " + seconds);
        }
    }
    private static void testDividedByZero() {
        Duration duration = Duration.ofSeconds(10);
        try {
            duration.dividedBy(0);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            // expected
        }
    }
    private static void testDividedByOne() {
        Duration duration = Duration.ofSeconds(10);
        Duration result = duration.dividedBy(1);
        if (!result.equals(duration)) {
            throw new AssertionError("Dividing by 1 should return the same duration");
        }
    }
}
