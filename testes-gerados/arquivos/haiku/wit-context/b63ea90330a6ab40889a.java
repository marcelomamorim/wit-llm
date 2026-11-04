/*
 * @test
 * @summary Checks the public Format.parseObject(String, ParsePosition) behavior returned by DateTimeFormatter.toFormat()
 * @run main DateTimeFormatterWitupTest
 */
import java.text.Format;
import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        Format format = DateTimeFormatter.ISO_LOCAL_DATE.toFormat();

        ParsePosition pos = new ParsePosition(0);
        Object result = format.parseObject("2020-01-02", pos);
        check(result != null, "valid text must parse");
        check(pos.getIndex() == 10, "index must advance to end of parsed text");
        check(pos.getErrorIndex() < 0, "no error index expected on success");
        check(((TemporalAccessor) result).isSupported(ChronoField.YEAR), "parsed result must support YEAR");

        pos = new ParsePosition(0);
        result = format.parseObject("abc", pos);
        check(result == null, "invalid text must return null");
        check(pos.getErrorIndex() >= 0, "invalid text must set error index");

        pos = new ParsePosition(50);
        result = format.parseObject("2020-01-02", pos);
        check(result == null, "position beyond text must return null");
        check(pos.getErrorIndex() >= 0, "position beyond text must set error index");

        boolean npe = false;
        try {
            format.parseObject(null, new ParsePosition(0));
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null text must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
