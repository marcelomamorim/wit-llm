/*
 * @test
 * @summary Verify DateTimeFormatterBuilder.appendLocalizedOffset argument validation and fluent return
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();

        if (builder.appendLocalizedOffset(TextStyle.FULL) != builder) {
            throw new RuntimeException("FULL style must return the same builder");
        }
        if (builder.appendLocalizedOffset(TextStyle.SHORT) != builder) {
            throw new RuntimeException("SHORT style must return the same builder");
        }
        if (builder.toFormatter() == null) {
            throw new RuntimeException("toFormatter must return a formatter");
        }

        checkNullRejected();
        checkIllegalStyleRejected(TextStyle.NARROW);
        checkIllegalStyleRejected(TextStyle.FULL_STANDALONE);
        checkIllegalStyleRejected(TextStyle.SHORT_STANDALONE);
        checkIllegalStyleRejected(TextStyle.NARROW_STANDALONE);
    }

    private static void checkNullRejected() {
        try {
            new DateTimeFormatterBuilder().appendLocalizedOffset(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("null style must throw NullPointerException");
    }

    private static void checkIllegalStyleRejected(TextStyle style) {
        try {
            new DateTimeFormatterBuilder().appendLocalizedOffset(style);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Unsupported style must throw IllegalArgumentException: " + style);
    }
}
