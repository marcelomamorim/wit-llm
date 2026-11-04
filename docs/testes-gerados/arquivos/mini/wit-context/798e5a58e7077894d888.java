/*
 * @test
 * @summary Test StringJoiner.setEmptyValue method for null and non-null inputs
 * @run main StringJoinerWitupTest
 */

import java.util.StringJoiner;
import java.util.Objects;

public class StringJoinerWitupTest {
    public static void main(String[] args) {
        testSetEmptyValueNonNull();
        testSetEmptyValueNull();
    }

    private static void testSetEmptyValueNonNull() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        StringJoiner returned = sj.setEmptyValue("empty");
        if (returned != sj) {
            throw new AssertionError("setEmptyValue should return the same StringJoiner instance");
        }
        String result = sj.toString();
        // Since nothing added, toString should return the emptyValue set
        if (!"empty".equals(result)) {
            throw new AssertionError("toString() should return the set emptyValue when no elements added, got: " + result);
        }
    }

    private static void testSetEmptyValueNull() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        try {
            sj.setEmptyValue(null);
            throw new AssertionError("Expected NullPointerException when setting null emptyValue");
        } catch (NullPointerException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("empty value")) {
                throw new AssertionError("NullPointerException message unexpected: " + msg);
            }
        }
    }
}
