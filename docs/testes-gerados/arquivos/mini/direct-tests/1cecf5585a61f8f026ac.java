/*
 * @test
 * @summary Test Long(String) constructor with valid and invalid inputs
 * @run main LongWitupTest
 */
public class LongWitupTest {
    public static void main(String[] args) {
        testValidString();
        testInvalidString();
        testNegativeString();
        testZeroString();
    }

    private static void testValidString() {
        Long l = new Long("12345");
        if (l.longValue() != 12345L) {
            throw new RuntimeException("Failed: expected 12345L but got " + l.longValue());
        }
    }

    private static void testNegativeString() {
        Long l = new Long("-98765");
        if (l.longValue() != -98765L) {
            throw new RuntimeException("Failed: expected -98765L but got " + l.longValue());
        }
    }

    private static void testZeroString() {
        Long l = new Long("0");
        if (l.longValue() != 0L) {
            throw new RuntimeException("Failed: expected 0L but got " + l.longValue());
        }
    }

    private static void testInvalidString() {
        try {
            new Long("notANumber");
            throw new RuntimeException("Failed: expected NumberFormatException for input 'notANumber'");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
