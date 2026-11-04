/* @test    @summary Test for OffsetTime.format with null formatter    @run main OffsetTimeWitupTest */
/* @modules java.base */

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testFormatNullFormatter();
    }

    public static void testFormatNullFormatter() {
        OffsetTime time = OffsetTime.of(12, 30, 45, 123456789, OffsetTime.now().getOffset());
        try {
            time.format(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException caught as expected");
        }
    }
}
