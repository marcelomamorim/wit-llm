/*
 * @test
 * @summary Test appendOptional method of DateTimeFormatterBuilder for null and valid formatter
 * @run main DateTimeFormatterBuilderWitupTest
 */
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatterBuilderWitupTest;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendOptionalWithNull();
        testAppendOptionalWithValidFormatter();
    }

    static void testAppendOptionalWithNull() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.appendOptional(null);
            System.out.println("FAIL: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    static void testAppendOptionalWithValidFormatter() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        builder.appendOptional(formatter);
        // No exception expected, test passes if no exception thrown
        System.out.println("PASS: appendOptional with valid formatter executed without exception");
    }
}
