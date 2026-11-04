/*
 * @test
 * @summary Year.format(DateTimeFormatter) delegates to the formatter and rejects null
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;

public class YearWitupTest {

    public static void main(String[] args) {
        DateTimeFormatter uuuu = DateTimeFormatter.ofPattern("uuuu");

        String s1 = Year.of(2020).format(uuuu);
        if (!"2020".equals(s1)) {
            throw new AssertionError("Expected 2020 but got " + s1);
        }

        String s2 = Year.of(7).format(uuuu);
        if (!"0007".equals(s2)) {
            throw new AssertionError("Expected 0007 but got " + s2);
        }

        String s3 = Year.of(2020).format(DateTimeFormatter.ofPattern("uuuu"));
        if (!s1.equals(s3)) {
            throw new AssertionError("Formatting is not stable: " + s1 + " vs " + s3);
        }

        boolean npe = false;
        try {
            Year.of(2020).format(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("Expected NullPointerException for null formatter");
        }
    }
}
