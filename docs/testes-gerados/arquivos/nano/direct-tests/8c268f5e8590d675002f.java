/*
 * @test
 * @summary Tests for LocalTime.of(int, int)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testOfValidValues();
        testOfInvalidHour();
        testOfInvalidMinute();
    }

    static void testOfValidValues() {
        LocalTime time = LocalTime.of(10, 30);
        assert time.getHour() == 10 : "Expected hour 10, got " + time.getHour();
        assert time.getMinute() == 30 : "Expected minute 30, got " + time.getMinute();
    }

    static void testOfInvalidHour() {
        try {
            LocalTime.of(24, 0);
            assert false : "Expected exception for invalid hour";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testOfInvalidMinute() {
        try {
            LocalTime.of(10, 60);
            assert false : "Expected exception for invalid minute";
        } catch (DateTimeException e) {
            // expected
        }
    }
}
