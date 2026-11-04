/*
 * @test
 * @summary Checks DateTimeFormatter.toFormat().parseObject(String) null handling, valid parsing and ParseException reporting
 * @run main DateTimeFormatterWitupTest
 */

import java.text.Format;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Format format = dtf.toFormat();

        boolean npe = false;
        try {
            format.parseObject((String) null);
        } catch (NullPointerException ex) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null text");
        }

        Object parsed = format.parseObject("2020-01-02");
        if (parsed == null) {
            throw new RuntimeException("Expected non-null result for valid text");
        }
        if (!(parsed instanceof TemporalAccessor)) {
            throw new RuntimeException("Expected TemporalAccessor result");
        }

        boolean parseEx = false;
        try {
            format.parseObject("abc");
        } catch (ParseException ex) {
            parseEx = true;
            if (ex.getErrorOffset() != 0) {
                throw new RuntimeException("Unexpected error offset: " + ex.getErrorOffset());
            }
        }
        if (!parseEx) {
            throw new RuntimeException("Expected ParseException for invalid text");
        }
    }
}
