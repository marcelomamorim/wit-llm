/*
 * @test
 * @summary Witup-derived tests for DateTimeFormatterBuilder.appendText null checks and text output
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullField();
        testNullTextStyle();
        testNullFieldReportedBeforeStyle();
        testValidCallChainsAndFormats();
    }

    static void testNullField() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().appendText(null, TextStyle.FULL);
        } catch (NullPointerException e) {
            thrown = true;
            if (!"field".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null field");
        }
    }

    static void testNullTextStyle() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().appendText(ChronoField.DAY_OF_WEEK, null);
        } catch (NullPointerException e) {
            thrown = true;
            if (!"textStyle".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null textStyle");
        }
    }

    static void testNullFieldReportedBeforeStyle() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().appendText(null, null);
        } catch (NullPointerException e) {
            thrown = true;
            if (!"field".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected when both arguments are null");
        }
    }

    static void testValidCallChainsAndFormats() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = b.appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL);
        if (result != b) {
            throw new RuntimeException("appendText must return the same builder");
        }
        String text = b.toFormatter(Locale.US).format(LocalDate.of(2018, 9, 17));
        if (!"Monday".equals(text)) {
            throw new RuntimeException("expected Monday but was " + text);
        }
    }
}
