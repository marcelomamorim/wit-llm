/*
 * @test
 * @summary Tests observable behavior of DateTimeFormatter ClassicFormat.parseObject(String) via toFormat()
 * @run main DateTimeFormatterWitupTest
 */

import java.text.Format;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullTextThrowsNpe();
        testParseWithoutTypeReturnsTemporalAccessor();
        testParseWithQueryReturnsLocalDate();
        testInvalidTextThrowsParseExceptionWithErrorOffset();
    }

    static void testNullTextThrowsNpe() {
        Format f = DateTimeFormatter.ISO_LOCAL_DATE.toFormat();
        try {
            f.parseObject(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (ParseException e) {
            throw new RuntimeException("unexpected ParseException", e);
        }
    }

    static void testParseWithoutTypeReturnsTemporalAccessor() throws Exception {
        Format f = DateTimeFormatter.ISO_LOCAL_DATE.toFormat();
        Object o = f.parseObject("2020-01-15");
        if (!(o instanceof TemporalAccessor)) {
            throw new RuntimeException("expected TemporalAccessor but got " + o);
        }
        TemporalAccessor ta = (TemporalAccessor) o;
        if (ta.get(ChronoField.YEAR) != 2020) {
            throw new RuntimeException("wrong YEAR");
        }
        if (ta.get(ChronoField.MONTH_OF_YEAR) != 1) {
            throw new RuntimeException("wrong MONTH_OF_YEAR");
        }
        if (ta.get(ChronoField.DAY_OF_MONTH) != 15) {
            throw new RuntimeException("wrong DAY_OF_MONTH");
        }
    }

    static void testParseWithQueryReturnsLocalDate() throws Exception {
        TemporalQuery<LocalDate> query = LocalDate::from;
        Format f = DateTimeFormatter.ISO_LOCAL_DATE.toFormat(query);
        Object o = f.parseObject("2020-01-15");
        if (!LocalDate.of(2020, 1, 15).equals(o)) {
            throw new RuntimeException("expected 2020-01-15 but got " + o);
        }
    }

    static void testInvalidTextThrowsParseExceptionWithErrorOffset() throws Exception {
        TemporalQuery<LocalDate> query = LocalDate::from;
        Format f = DateTimeFormatter.ISO_LOCAL_DATE.toFormat(query);
        try {
            f.parseObject("abc");
            throw new RuntimeException("expected ParseException");
        } catch (ParseException e) {
            if (e.getErrorOffset() != 0) {
                throw new RuntimeException("expected error offset 0 but got " + e.getErrorOffset());
            }
        }
    }
}
