/*
 * @test
 * @summary Verify Clock.millis() derives its value from instant().toEpochMilli() and propagates overflow
 * @run main ClockWitupTest
 */
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ClockWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static Clock fixedClock(Instant instant) {
        return new Clock() {
            @Override
            public ZoneId getZone() {
                return ZoneOffset.UTC;
            }

            @Override
            public Clock withZone(ZoneId zone) {
                return this;
            }

            @Override
            public Instant instant() {
                return instant;
            }
        };
    }

    public static void main(String[] args) {
        check(fixedClock(Instant.EPOCH).millis() == 0L, "epoch millis");
        check(fixedClock(Instant.ofEpochMilli(1234L)).millis() == 1234L, "positive millis");
        check(fixedClock(Instant.ofEpochSecond(0, 999_999_999)).millis() == 999L, "sub-second truncation");
        check(fixedClock(Instant.ofEpochSecond(-1, 500_000_000)).millis() == -500L, "negative instant with nanos");

        boolean threw = false;
        try {
            fixedClock(Instant.MAX).millis();
        } catch (ArithmeticException e) {
            threw = true;
        }
        check(threw, "overflow must raise ArithmeticException");
    }
}
