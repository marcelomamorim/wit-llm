/*
 * @test
 * @summary Witup-generated tests for LocalDate.format(DateTimeFormatter)
 * @run main LocalDateWitupTest
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testNullFormatterThrowsNPE();
        testIsoFormat();
        testPatternFormat();
    }

    static void testNullFormatterThrowsNPE() {
        LocalDate d = LocalDate.of(2020, 1, 15);
        try {
            d.format(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null formatter");
    }

    static void testIsoFormat() {
        LocalDate d = LocalDate.of(2020, 1, 15);
        check("2020-01-15", d.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    static void testPatternFormat() {
        LocalDate d = LocalDate.of(2020, 1, 5);
        check("05/01/2020", d.format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
