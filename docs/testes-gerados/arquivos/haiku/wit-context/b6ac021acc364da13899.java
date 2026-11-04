/*
 * @test
 * @summary Observable exceptional-value formatting through FloatingDecimal public entry points (NaN and Infinity)
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        check("NaN", Double.toString(Double.NaN));
        check("Infinity", Double.toString(Double.POSITIVE_INFINITY));
        check("-Infinity", Double.toString(Double.NEGATIVE_INFINITY));
        check("NaN", Float.toString(Float.NaN));
        check("Infinity", Float.toString(Float.POSITIVE_INFINITY));
        check("-Infinity", Float.toString(Float.NEGATIVE_INFINITY));
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
