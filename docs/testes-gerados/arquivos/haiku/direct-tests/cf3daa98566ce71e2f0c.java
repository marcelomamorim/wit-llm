/*
 * @test
 * @summary BigDecimal(char[], int, int) must parse only the selected slice and match the String constructor
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        assertSlice("12345", 0, 5, "12345");
        assertSlice("ab-123.40cd", 2, 6, "-123.40");
        assertSlice("xx1.5E3yy", 2, 5, "1.5E+3");
        assertSlice("0.000", 0, 5, "0.000");
        assertSlice("-0", 0, 2, "0");
        assertSlice("zz42zz", 2, 2, "42");

        String[] samples = {"123.45", "-7", "1E-3", "98.7600"};
        for (String s : samples) {
            BigDecimal fromSlice = new BigDecimal(s.toCharArray(), 0, s.length());
            BigDecimal fromString = new BigDecimal(s);
            if (fromSlice.compareTo(fromString) != 0 || fromSlice.scale() != fromString.scale()) {
                throw new RuntimeException("Mismatch for " + s);
            }
        }

        boolean thrown = false;
        try {
            new BigDecimal("12a4".toCharArray(), 0, 4);
        } catch (NumberFormatException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NumberFormatException for illegal character");
        }
    }

    private static void assertSlice(String src, int offset, int len, String expected) {
        BigDecimal bd = new BigDecimal(src.toCharArray(), offset, len);
        if (!bd.toString().equals(expected)) {
            throw new RuntimeException("Expected " + expected + " but got " + bd.toString());
        }
    }
}
