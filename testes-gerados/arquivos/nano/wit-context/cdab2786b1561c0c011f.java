/* @test    @summary Test for OffsetTime.of(LocalTime, ZoneOffset) method to verify behavior and exception paths    @run main OffsetTimeWitupTest */

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.OffsetTime;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNullTime();
        testNullOffset();
    }

    static void testValidInputs() {
        LocalTime time = LocalTime.of(12, 30, 45);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetTime ot = OffsetTime.of(time, offset);
        if (ot == null) {
            throw new AssertionError("OffsetTime.of should not return null for valid inputs");
        }
        if (!ot.getLocalTime().equals(time)) {
            throw new AssertionError("LocalTime mismatch");
        }
        if (!ot.getOffset().equals(offset)) {
            throw new AssertionError("ZoneOffset mismatch");
        }
    }

    static void testNullTime() {
        try {
            ZoneOffset offset = ZoneOffset.ofHours(2);
            OffsetTime.of(null, offset);
            throw new AssertionError("Expected NullPointerException for null LocalTime");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullOffset() {
        try {
            LocalTime time = LocalTime.of(12, 30, 45);
            OffsetTime.of(time, null);
            throw new AssertionError("Expected NullPointerException for null ZoneOffset");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
