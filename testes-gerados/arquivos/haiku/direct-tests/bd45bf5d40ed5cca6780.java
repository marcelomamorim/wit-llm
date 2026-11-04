/*
 * @test
 * @summary Tests DateTimeFormatterBuilder.append(DateTimeFormatter) observable behaviour
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNpe();
        testAppendReturnsSameBuilder();
        testAppendIsoLocalDate();
        testAppendCustomPatternFormatter();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFormatterThrowsNpe() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().append((DateTimeFormatter) null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "append(null) must throw NullPointerException");
    }

    static void testAppendReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.append(DateTimeFormatter.ISO_LOCAL_DATE);
        check(result == builder, "append must return the same builder instance");
    }

    static void testAppendIsoLocalDate() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .toFormatter();
        String s = f.format(LocalDate.of(2018, 9, 15));
        check("2018-09-15".equals(s), "expected 2018-09-15 but got " + s);
    }

    static void testAppendCustomPatternFormatter() {
        DateTimeFormatter year = DateTimeFormatter.ofPattern("uuuu");
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .append(year)
                .toFormatter();
        String s = f.format(LocalDate.of(2012, 1, 1));
        check("2012".equals(s), "expected 2012 but got " + s);
    }
}
