/*
 * @test
 * @summary Witup-generated checks for ZoneOffset.ofTotalSeconds range validation and returned offsets
 * @run main ZoneOffsetWitupTest
 */
import java.time.DateTimeException;
import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {
    public static void main(String[] args) {
        checkOutOfRange(64801);
        checkOutOfRange(-64801);
        checkOutOfRange(Integer.MAX_VALUE);
        checkOutOfRange(Integer.MIN_VALUE);

        check(ZoneOffset.ofTotalSeconds(64800).getTotalSeconds(), 64800);
        check(ZoneOffset.ofTotalSeconds(-64800).getTotalSeconds(), -64800);
        check(ZoneOffset.ofTotalSeconds(3600).getTotalSeconds(), 3600);
        check(ZoneOffset.ofTotalSeconds(0).getTotalSeconds(), 0);
        check(ZoneOffset.ofTotalSeconds(1).getTotalSeconds(), 1);

        if (!ZoneOffset.ofTotalSeconds(3600).getId().equals("+01:00")) {
            throw new RuntimeException("unexpected id for 3600 seconds");
        }

        ZoneOffset a = ZoneOffset.ofTotalSeconds(900);
        ZoneOffset b = ZoneOffset.ofTotalSeconds(900);
        if (!a.equals(b)) {
            throw new RuntimeException("equal offsets expected for 900 seconds");
        }
    }

    static void checkOutOfRange(int seconds) {
        try {
            ZoneOffset.ofTotalSeconds(seconds);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for " + seconds);
    }

    static void check(int actual, int expected) {
        if (actual != expected) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
