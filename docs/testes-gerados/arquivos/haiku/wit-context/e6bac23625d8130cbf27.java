/*
 * @test
 * @summary Witup-generated checks for ZoneOffset.ofHours boundaries and exception behavior
 * @run main ZoneOffsetWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {

    public static void main(String[] args) {
        checkValid(18);
        checkValid(-18);
        checkValid(1);
        checkValid(0);

        ZoneOffset plusOne = ZoneOffset.ofHours(1);
        if (plusOne.getTotalSeconds() != 3600) {
            throw new RuntimeException("ofHours(1) total seconds: " + plusOne.getTotalSeconds());
        }
        if (!"+01:00".equals(plusOne.getId())) {
            throw new RuntimeException("ofHours(1) id: " + plusOne.getId());
        }

        ZoneOffset minusOne = ZoneOffset.ofHours(-1);
        if (minusOne.getTotalSeconds() != -3600) {
            throw new RuntimeException("ofHours(-1) total seconds: " + minusOne.getTotalSeconds());
        }
        if (!"-01:00".equals(minusOne.getId())) {
            throw new RuntimeException("ofHours(-1) id: " + minusOne.getId());
        }

        ZoneOffset zero = ZoneOffset.ofHours(0);
        if (zero.getTotalSeconds() != 0) {
            throw new RuntimeException("ofHours(0) total seconds: " + zero.getTotalSeconds());
        }

        expectDateTimeException(19);
        expectDateTimeException(-19);
        expectDateTimeException(Integer.MAX_VALUE);
        expectDateTimeException(Integer.MIN_VALUE);
    }

    static void checkValid(int hours) {
        ZoneOffset offset = ZoneOffset.ofHours(hours);
        if (offset == null) {
            throw new RuntimeException("ofHours(" + hours + ") returned null");
        }
        if (offset.getTotalSeconds() != hours * 3600) {
            throw new RuntimeException("ofHours(" + hours + ") total seconds: " + offset.getTotalSeconds());
        }
    }

    static void expectDateTimeException(int hours) {
        try {
            ZoneOffset.ofHours(hours);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for ofHours(" + hours + ")");
    }
}
