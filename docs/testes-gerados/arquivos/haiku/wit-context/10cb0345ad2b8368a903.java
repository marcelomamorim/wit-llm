/*
 * @test
 * @summary Verifies Date(String) delegates to parsing: valid RFC 1123 strings yield the expected instant and unparseable strings throw IllegalArgumentException
 * @run main DateWitupTest
 */
public class DateWitupTest {
    public static void main(String[] args) {
        long epoch = new java.util.Date("Thu, 01 Jan 1970 00:00:00 GMT").getTime();
        if (epoch != 0L) {
            throw new RuntimeException("Expected 0 but got " + epoch);
        }

        String valid = "Thu, 01 Jan 1970 00:00:01 GMT";
        long oneSecond = new java.util.Date(valid).getTime();
        if (oneSecond != 1000L) {
            throw new RuntimeException("Expected 1000 but got " + oneSecond);
        }

        if (new java.util.Date(valid).getTime() != java.util.Date.parse(valid)) {
            throw new RuntimeException("Date(String) must agree with Date.parse(String)");
        }

        String[] invalid = {"not a date", "", "xyzzy 123"};
        for (String s : invalid) {
            try {
                new java.util.Date(s);
                throw new RuntimeException("Expected IllegalArgumentException for: " + s);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }
}
