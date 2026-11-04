/*
 * @test
 * @summary Test StringJoiner.setEmptyValue behavior and null rejection
 * @run main StringJoinerWitupTest
 */

import java.util.StringJoiner;
import java.util.Objects;

public class StringJoinerWitupTest {
    public static void main(String[] args) {
        testSetEmptyValueReturnsThis();
        testSetEmptyValueRejectsNull();
    }

    private static void testSetEmptyValueReturnsThis() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        StringJoiner returned = sj.setEmptyValue("empty");
        if (returned != sj) {
            throw new AssertionError("setEmptyValue should return the same StringJoiner instance");
        }
        // The emptyValue is private, so test observable behavior:
        // When no elements added, toString() returns emptyValue
        String result = sj.toString();
        if (!"empty".equals(result)) {
            throw new AssertionError("toString() should return the set emptyValue when no elements added, got: " + result);
        }
    }

    private static void testSetEmptyValueRejectsNull() {
        StringJoiner sj = new StringJoiner(",");
        try {
            sj.setEmptyValue(null);
            throw new AssertionError("setEmptyValue(null) should throw NullPointerException");
        } catch (NullPointerException npe) {
            String msg = npe.getMessage();
            if (msg == null || !msg.contains("empty value")) {
                throw new AssertionError("NullPointerException message unexpected: " + msg);
            }
        }
    }
}
