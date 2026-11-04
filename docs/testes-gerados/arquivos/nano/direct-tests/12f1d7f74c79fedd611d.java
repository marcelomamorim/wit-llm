/*
 * @test
 * @summary Test appendLocalizedOffset method for valid and invalid styles
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendLocalizedOffsetValidStyles();
        testAppendLocalizedOffsetInvalidStyle();
    }

    static void testAppendLocalizedOffsetValidStyles() {
        try {
            DateTimeFormatterBuilder builderFull = new DateTimeFormatterBuilder();
            builderFull.appendLocalizedOffset(TextStyle.FULL);
            System.out.println("appendLocalizedOffset with FULL style succeeded.");
        } catch (Exception e) {
            System.out.println("appendLocalizedOffset with FULL style failed: " + e);
        }
        try {
            DateTimeFormatterBuilder builderShort = new DateTimeFormatterBuilder();
            builderShort.appendLocalizedOffset(TextStyle.SHORT);
            System.out.println("appendLocalizedOffset with SHORT style succeeded.");
        } catch (Exception e) {
            System.out.println("appendLocalizedOffset with SHORT style failed: " + e);
        }
    }

    static void testAppendLocalizedOffsetInvalidStyle() {
        try {
            DateTimeFormatterBuilder builderInvalid = new DateTimeFormatterBuilder();
            // Passing null should throw NullPointerException
            builderInvalid.appendLocalizedOffset(null);
            System.out.println("appendLocalizedOffset with null style did not throw.");
        } catch (NullPointerException e) {
            System.out.println("appendLocalizedOffset with null style threw NullPointerException as expected.");
        } catch (Exception e) {
            System.out.println("appendLocalizedOffset with null style threw unexpected exception: " + e);
        }
        try {
            // Creating a dummy TextStyle to simulate invalid style (not possible directly, so simulate via reflection or assume invalid)
            // Since TextStyle is an enum, passing null is the main invalid case
            DateTimeFormatterBuilder builderInvalid2 = new DateTimeFormatterBuilder();
            builderInvalid2.appendInternal(new LocalizedOffsetIdPrinterParser(null));
            System.out.println("appendInternal with null style did not throw.");
        } catch (NullPointerException e) {
            System.out.println("appendInternal with null style threw NullPointerException as expected.");
        } catch (Exception e) {
            System.out.println("appendInternal with null style threw unexpected exception: " + e);
        }
    }

    // Dummy class to simulate the internal method call, since appendInternal is private
    static class LocalizedOffsetIdPrinterParser {
        TextStyle style;
        LocalizedOffsetIdPrinterParser(TextStyle style) {
            this.style = style;
        }
    }
}
