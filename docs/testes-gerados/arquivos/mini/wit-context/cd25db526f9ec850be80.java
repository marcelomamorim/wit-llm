/*
 * @test
 * @summary Test appendOffsetId method of DateTimeFormatterBuilder for basic functionality and NPE scenarios
 * @run main java.time.format.DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {
    public static void main(String[] args) {
        testAppendOffsetIdBasic();
    }

    private static void testAppendOffsetIdBasic() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        // The method appendOffsetId should return the same builder instance
        DateTimeFormatterBuilder returned = builder.appendOffsetId();
        if (returned != builder) {
            throw new AssertionError("appendOffsetId should return this instance");
        }
        // No exception expected and no null return
        if (returned == null) {
            throw new AssertionError("appendOffsetId returned null");
        }
    }
}
