/*
 * @test
 * @summary Test String.repeat(int) method for correctness and exceptions
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testNegativeCount();
        testCountOneReturnsSameInstance();
        testEmptyStringReturnsEmpty();
        testCountZeroReturnsEmpty();
        testSingleCharStringRepeat();
        testMultipleCharStringRepeat();
        testLargeRepeatThrowsOutOfMemoryError();
    }

    private static void testNegativeCount() {
        String s = "abc";
        try {
            s.repeat(-1);
            throw new Error("Expected IllegalArgumentException for negative count");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("count is negative")) {
                throw new Error("IllegalArgumentException message unexpected: " + e.getMessage());
            }
        }
    }

    private static void testCountOneReturnsSameInstance() {
        String s = "test";
        String r = s.repeat(1);
        if (r != s) {
            throw new Error("repeat(1) should return the same String instance");
        }
    }

    private static void testEmptyStringReturnsEmpty() {
        String s = "";
        String r = s.repeat(5);
        if (!r.equals("")) {
            throw new Error("Repeating empty string should return empty string");
        }
    }

    private static void testCountZeroReturnsEmpty() {
        String s = "abc";
        String r = s.repeat(0);
        if (!r.equals("")) {
            throw new Error("repeat(0) should return empty string");
        }
    }

    private static void testSingleCharStringRepeat() {
        String s = "x";
        String r = s.repeat(3);
        if (!r.equals("xxx")) {
            throw new Error("Single char string repeated 3 times should be 'xxx', got: " + r);
        }
    }

    private static void testMultipleCharStringRepeat() {
        String s = "ab";
        String r = s.repeat(4);
        if (!r.equals("abababab")) {
            throw new Error("String 'ab' repeated 4 times should be 'abababab', got: " + r);
        }
    }

    private static void testLargeRepeatThrowsOutOfMemoryError() {
        String s = "abc";
        // Calculate count to exceed Integer.MAX_VALUE when multiplied by s.length()
        int len = s.length();
        int count = (Integer.MAX_VALUE / len) + 1;
        try {
            s.repeat(count);
            throw new Error("Expected OutOfMemoryError for too large repeat count");
        } catch (OutOfMemoryError e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Repeating")) {
                throw new Error("OutOfMemoryError message unexpected: " + msg);
            }
        }
    }
}
