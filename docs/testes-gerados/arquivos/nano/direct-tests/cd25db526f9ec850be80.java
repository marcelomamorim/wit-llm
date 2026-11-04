/*
 * @test
 * @summary Test appendOffsetId method of DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendOffsetId();
    }

    public static void testAppendOffsetId() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendOffsetId();
        // Since appendOffsetId returns the builder itself, test that method completes without exception
        System.out.println("appendOffsetId executed successfully");
    }
}
