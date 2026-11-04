/* @test    @summary Test Long.parseLong(String s) with valid and invalid inputs    @run main LongWitupTest */

import java.lang.Long;

public class LongWitupTest {

    public static void main(String[] args) {
        testParseLongValid();
        testParseLongInvalid();
    }

    static void testParseLongValid() {
        long result1 = Long.parseLong("0");
        assert result1 == 0L : "Expected 0, got " + result1;
        long result2 = Long.parseLong("12345");
        assert result2 == 12345L : "Expected 12345, got " + result2;
        long result3 = Long.parseLong("-98765");
        assert result3 == -98765L : "Expected -98765, got " + result3;
        long result4 = Long.parseLong("9223372036854775807"); // MAX_VALUE
        assert result4 == Long.MAX_VALUE : "Expected MAX_VALUE, got " + result4;
        long result5 = Long.parseLong("-9223372036854775808"); // MIN_VALUE
        assert result5 == Long.MIN_VALUE : "Expected MIN_VALUE, got " + result5;
    }

    static void testParseLongInvalid() {
        boolean exceptionThrown = false;
        try {
            Long.parseLong("notANumber");
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NumberFormatException for input 'notANumber'";

        exceptionThrown = false;
        try {
            Long.parseLong("");
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NumberFormatException for empty string";

        exceptionThrown = false;
        try {
            Long.parseLong("9223372036854775808"); // MAX_VALUE + 1
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NumberFormatException for overflow";

        exceptionThrown = false;
        try {
            Long.parseLong("-9223372036854775809"); // MIN_VALUE - 1
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NumberFormatException for underflow";
    }
}
