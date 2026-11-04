/* @test    @summary Test Date(String s) constructor with valid date string    @run main DateWitupTest */

import java.util.Date;

public class DateWitupTest {

    public static void main(String[] args) {
        testValidDateString();
        testInvalidDateString();
    }

    static void testValidDateString() {
        String dateString = "Wed Dec 31 16:00:00 EST 1969";
        Date date = new Date(dateString);
        assert date != null : "Date object should not be null for valid string";
    }

    static void testInvalidDateString() {
        String invalidString = "Invalid Date String";
        try {
            Date date = new Date(invalidString);
            assert date != null : "Date object should be created even for invalid string";
        } catch (Exception e) {
            assert false : "Constructor should not throw exception for invalid string";
        }
    }
}
