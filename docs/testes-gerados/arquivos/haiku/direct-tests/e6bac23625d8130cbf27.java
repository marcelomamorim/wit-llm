/*
 * @test
 * @summary ZoneOffset.ofHours returns the offsets equivalent to the hour-based ids
 * @run main ZoneOffsetWitupTest
 */

import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {

    public static void main(String[] args) {
        check(ZoneOffset.ofHours(0).equals(ZoneOffset.of("Z")), "ofHours(0) should equal Z");
        check(ZoneOffset.ofHours(0).equals(ZoneOffset.of("+00:00")), "ofHours(0) should equal +00:00");
        check(ZoneOffset.ofHours(5).equals(ZoneOffset.of("+05:00")), "ofHours(5) should equal +05:00");
        check(ZoneOffset.ofHours(-3).equals(ZoneOffset.of("-03:00")), "ofHours(-3) should equal -03:00");
        check(ZoneOffset.ofHours(18).equals(ZoneOffset.of("+18:00")), "ofHours(18) should equal +18:00");
        check(ZoneOffset.ofHours(-18).equals(ZoneOffset.of("-18:00")), "ofHours(-18) should equal -18:00");
        check(ZoneOffset.ofHours(7).getId().equals("+07:00"), "ofHours(7) id should be +07:00");
        check(ZoneOffset.ofHours(-18).getId().equals("-18:00"), "ofHours(-18) id should be -18:00");
        check(ZoneOffset.ofHours(2).getTotalSeconds() == 7200, "ofHours(2) should be 7200 seconds");
        check(ZoneOffset.ofHours(-1).getTotalSeconds() == -3600, "ofHours(-1) should be -3600 seconds");
        check(!ZoneOffset.ofHours(1).equals(ZoneOffset.ofHours(-1)), "ofHours(1) must differ from ofHours(-1)");
        check(ZoneOffset.ofHours(4).equals(ZoneOffset.ofHours(4)), "ofHours(4) should be equal to itself by value");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
