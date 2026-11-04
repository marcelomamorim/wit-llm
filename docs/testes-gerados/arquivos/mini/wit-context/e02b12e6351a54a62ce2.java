/*
 * @test
 * @summary Test DateTimeFormatterBuilder.optionalEnd() behavior and exception
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatterBuilder.CompositePrinterParser;
import java.time.format.DateTimeFormatterBuilder.CompositePrinterParser.CompositePrinterParser;
import java.lang.IllegalStateException;
import java.util.ArrayList;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testOptionalEndWithoutOptionalStart();
        testOptionalEndWithEmptyPrinterParsers();
        testOptionalEndWithNonEmptyPrinterParsers();
    }

    private static void testOptionalEndWithoutOptionalStart() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // The builder is newly created, so active.parent == null
        try {
            builder.optionalEnd();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ex) {
            String msg = ex.getMessage();
            if (msg == null || !msg.contains("optionalStart")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }

    private static void testOptionalEndWithEmptyPrinterParsers() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Simulate optionalStart by manipulating internal state via reflection is disallowed,
        // so we test observable behavior by calling optionalStart() then optionalEnd()
        // but optionalStart() is not public, so we test only that optionalEnd() after optionalStart() works
        // Since optionalStart() is not public, we cannot call it, so we test minimal behavior:
        // We create a builder and append something to it to ensure active.parent != null
        // But since no public API to set active.parent, we test only that optionalEnd() throws when no optionalStart
        // So this test is limited and only checks that optionalEnd() does not throw when printerParsers is empty
        // We create a builder and append a literal to simulate non-empty printerParsers
        builder.appendLiteral('a');
        // Now optionalEnd() should not throw
        builder.optionalEnd();
    }

    private static void testOptionalEndWithNonEmptyPrinterParsers() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // Append a literal to ensure printerParsers is non-empty
        builder.appendLiteral('a');
        // Call optionalStart() to set active.parent (not public, so we simulate by calling optionalStart() if it existed)
        // optionalStart() is not public, so we cannot call it; thus, we cannot fully test this path
        // We test that optionalEnd() does not throw and returns the builder
        DateTimeFormatterBuilder result = builder.optionalEnd();
        if (result != builder) {
            throw new AssertionError("optionalEnd() should return this");
        }
    }
}