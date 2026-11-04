/*
 * @test
 * @summary appendText(TemporalField) rejects a null field and returns the same builder
 * @run main DateTimeFormatterBuilderWitupTest
 */
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendText(ChronoField.DAY_OF_MONTH);
        if (result != builder) {
            throw new RuntimeException("appendText(field) should return the same builder");
        }
        try {
            builder.appendText(null);
            throw new RuntimeException("expected NullPointerException for null field");
        } catch (NullPointerException expected) {
        }
    }
}
