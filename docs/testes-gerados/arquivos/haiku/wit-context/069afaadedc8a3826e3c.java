/*
 * @test
 * @summary Witup-generated checks for Year.format(DateTimeFormatter)
 * @run main YearWitupTest
 */
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class YearWitupTest {
    public static void main(String[] args) {
        testNullFormatterThrowsNPE();
        testFormatDelegatesToFormatter();
    }

    static void testNullFormatterThrowsNPE() {
        try {
            Year.of(2024).format(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException");
    }

    static void testFormatDelegatesToFormatter() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu");
        String s = Year.of(2024).format(f);
        if (!"2024".equals(s)) {
            throw new AssertionError("got " + s);
        }
    }
}
