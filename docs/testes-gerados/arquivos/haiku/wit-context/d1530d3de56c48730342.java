/*
 * @test
 * @summary Witup-generated checks for DateTimeFormatterBuilder.padNext(int, char)
 * @run main DateTimeFormatterBuilderWitupTest
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testInvalidWidthThrowsIllegalArgumentException();
        testReturnsSameBuilder();
        testPaddingAppliedToNextValue();
        testWidthOneDoesNotPad();
        testValueLongerThanWidthIsNotTruncated();
    }

    static void testInvalidWidthThrowsIllegalArgumentException() {
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);
    }

    static void expectIllegalArgument(int padWidth) {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().padNext(padWidth, ' ');
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for padWidth " + padWidth);
        }
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.padNext(3, '0');
        if (result != builder) {
            throw new RuntimeException("padNext must return the same builder instance");
        }
    }

    static void testPaddingAppliedToNextValue() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .padNext(5, '*')
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        String s = f.format(LocalDate.of(2018, 9, 3));
        if (!"****3".equals(s)) {
            throw new RuntimeException("Expected ****3 but was " + s);
        }
    }

    static void testWidthOneDoesNotPad() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .padNext(1, 'x')
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        String s = f.format(LocalDate.of(2018, 9, 3));
        if (!"3".equals(s)) {
            throw new RuntimeException("Expected 3 but was " + s);
        }
    }

    static void testValueLongerThanWidthIsNotTruncated() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .padNext(5, '*')
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        String s = f.format(LocalDate.of(2018, 9, 13));
        if (!"***13".equals(s)) {
            throw new RuntimeException("Expected ***13 but was " + s);
        }
    }
}
