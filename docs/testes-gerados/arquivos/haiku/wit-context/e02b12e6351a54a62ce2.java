/*
 * @test
 * @summary Witup-generated tests for DateTimeFormatterBuilder.optionalEnd() exception and chaining behavior
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testOptionalEndWithoutStartThrows();
        testOptionalEndReturnsSameBuilder();
        testOptionalSectionPrintsContent();
        testEmptyOptionalSectionPrintsNothing();
        testExtraOptionalEndThrows();
    }

    static void testOptionalEndWithoutStartThrows() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        try {
            b.optionalEnd();
            throw new AssertionError("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void testOptionalEndReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        if (b.optionalStart() != b) {
            throw new AssertionError("optionalStart() must return this");
        }
        if (b.optionalEnd() != b) {
            throw new AssertionError("optionalEnd() must return this");
        }
    }

    static void testOptionalSectionPrintsContent() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendLiteral("A")
                .optionalStart()
                .appendLiteral("B")
                .optionalEnd()
                .toFormatter();
        String s = f.format(LocalDate.of(2020, 1, 1));
        if (!"AB".equals(s)) {
            throw new AssertionError("Expected AB but got " + s);
        }
    }

    static void testEmptyOptionalSectionPrintsNothing() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .optionalStart()
                .optionalEnd()
                .appendLiteral("C")
                .toFormatter();
        String s = f.format(LocalDate.of(2020, 1, 1));
        if (!"C".equals(s)) {
            throw new AssertionError("Expected C but got " + s);
        }
    }

    static void testExtraOptionalEndThrows() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder()
                .optionalStart()
                .optionalEnd();
        try {
            b.optionalEnd();
            throw new AssertionError("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }
}
