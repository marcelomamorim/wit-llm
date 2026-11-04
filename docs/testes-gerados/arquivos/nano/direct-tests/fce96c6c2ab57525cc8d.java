/* @test    @summary Test StringJoiner constructor with delimiter only    @run main StringJoinerWitupTest */

import java.util.StringJoiner;

public class StringJoinerWitupTest {

    public static void main(String[] args) {
        testConstructorWithDelimiter();
        testEmptyConstructor();
    }

    private static void testConstructorWithDelimiter() {
        String delimiter = ", ";
        StringJoiner sj = new StringJoiner(delimiter);
        assert sj != null : "StringJoiner instance should not be null";
        String result = sj.toString();
        assert result.equals("") : "Expected empty string for new StringJoiner";
        sj.add("a");
        sj.add("b");
        String expected = "a, b";
        String actual = sj.toString();
        assert actual.equals(expected) : "Expected '" + expected + "', but got '" + actual + "'";
    }

    private static void testEmptyConstructor() {
        StringJoiner sj = new StringJoiner(":");
        assert sj != null : "StringJoiner instance should not be null";
        String result = sj.toString();
        assert result.equals("") : "Expected empty string for new StringJoiner";
        sj.add("x");
        String expected = "x";
        String actual = sj.toString();
        assert actual.equals(expected) : "Expected '" + expected + "', but got '" + actual + "'";
    }
}
