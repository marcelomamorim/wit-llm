/*
 * @test
 * @summary Witup-derived checks for MonthDay.getMonth and MonthDay.of month validation
 * @run main MonthDayWitupTest
 */
import java.time.DateTimeException;
import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        for (int m = 1; m <= 12; m++) {
            MonthDay md = MonthDay.of(m, 1);
            Month expected = Month.of(m);
            if (md.getMonth() != expected) {
                throw new RuntimeException("getMonth mismatch for month " + m);
            }
        }
        for (Month month : Month.values()) {
            if (MonthDay.of(month, 1).getMonth() != month) {
                throw new RuntimeException("getMonth mismatch for enum " + month);
            }
        }
        if (MonthDay.of(2, 29).getMonth() != Month.FEBRUARY) {
            throw new RuntimeException("leap day month mismatch");
        }
        if (MonthDay.from(MonthDay.of(12, 31)).getMonth() != Month.DECEMBER) {
            throw new RuntimeException("from() month mismatch");
        }
        checkInvalidMonth(0);
        checkInvalidMonth(13);
        checkInvalidMonth(-1);
    }

    private static void checkInvalidMonth(int month) {
        try {
            MonthDay.of(month, 1);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for month " + month);
    }
}
