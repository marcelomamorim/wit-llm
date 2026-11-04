/*
 * @test
 * @summary Tests for DecimalStyle.of(Locale): null rejection, caching and standard symbols
 * @run main DecimalStyleWitupTest
 */

import java.time.format.DecimalStyle;
import java.util.Locale;

public class DecimalStyleWitupTest {

    public static void main(String[] args) {
        testNullLocaleThrowsNPE();
        testSameLocaleReturnsCachedInstance();
        testNonNullResultForUS();
        testStandardSymbolsForUS();
        testStandardConstantSymbols();
    }

    static void testNullLocaleThrowsNPE() {
        boolean thrown = false;
        try {
            DecimalStyle.of(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }

    static void testSameLocaleReturnsCachedInstance() {
        DecimalStyle first = DecimalStyle.of(Locale.US);
        DecimalStyle second = DecimalStyle.of(Locale.US);
        if (first != second) {
            throw new RuntimeException("Expected cached instance for same locale");
        }
    }

    static void testNonNullResultForUS() {
        DecimalStyle style = DecimalStyle.of(Locale.US);
        if (style == null) {
            throw new RuntimeException("of(Locale.US) returned null");
        }
    }

    static void testStandardSymbolsForUS() {
        DecimalStyle style = DecimalStyle.of(Locale.US);
        if (style.getZeroDigit() != '0') {
            throw new RuntimeException("Unexpected zero digit: " + style.getZeroDigit());
        }
        if (style.getDecimalSeparator() != '.') {
            throw new RuntimeException("Unexpected decimal separator: " + style.getDecimalSeparator());
        }
    }

    static void testStandardConstantSymbols() {
        DecimalStyle std = DecimalStyle.STANDARD;
        if (std.getZeroDigit() != '0' || std.getPositiveSign() != '+'
                || std.getNegativeSign() != '-' || std.getDecimalSeparator() != '.') {
            throw new RuntimeException("STANDARD symbols do not match expected ASCII values");
        }
    }
}
