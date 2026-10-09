/*
 * @test
 * @summary Test DateTimeFormatterBuilder.optionalEnd() behavior
 * @run main DateTimeFormatterBuilderWitupTest_P931fe6f10ee0
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest_P931fe6f10ee0 {
    public static void main(String[] args) {
        testOptionalEndWithoutOptionalStart();
        testOptionalEndWithEmptyPrinterParsers();
        testOptionalEndWithNonEmptyPrinterParsers();
    }

    static void testOptionalEndWithoutOptionalStart() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.optionalEnd();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ex) {
            String msg = ex.getMessage();
            if (!"Cannot call optionalEnd() as there was no previous call to optionalStart()".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    static void testOptionalEndWithEmptyPrinterParsers() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.optionalStart();
        // no append calls, so printerParsers is empty
        builder.optionalEnd();
        // Should not throw and should return the same builder instance
        if (builder == null) {
            throw new RuntimeException("Builder is null after optionalEnd");
        }
    }

    static void testOptionalEndWithNonEmptyPrinterParsers() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.optionalStart();
        builder.appendLiteral('A'); // appendLiteral adds a printerParser
        DateTimeFormatterBuilder returned = builder.optionalEnd();
        if (returned != builder) {
            throw new RuntimeException("optionalEnd() did not return the same builder instance");
        }
    }
}
