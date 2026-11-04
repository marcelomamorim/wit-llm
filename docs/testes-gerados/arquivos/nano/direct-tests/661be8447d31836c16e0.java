/* @test    @summary Test getDisplayName method of java.time.Month    @run main MonthWitupTest */

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthWitupTest {

    public static void main(String[] args) {
        testGetDisplayName();
    }

    public static void testGetDisplayName() {
        Month month = Month.JULY;
        String displayNameDefault = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String displayNameShort = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String displayNameNarrow = month.getDisplayName(TextStyle.NARROW, Locale.ENGLISH);
        
        // Check that the display names are not null
        assert displayNameDefault != null : "Default display name should not be null";
        assert displayNameShort != null : "Short display name should not be null";
        assert displayNameNarrow != null : "Narrow display name should not be null";
        
        // Check that the display names are different for different styles
        assert !displayNameDefault.equals(displayNameShort) : "Default and Short display names should differ";
        assert !displayNameDefault.equals(displayNameNarrow) : "Default and Narrow display names should differ";
        
        // Check that the display names are consistent for the same style and locale
        String repeatDefault = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        assert displayNameDefault.equals(repeatDefault) : "Repeated getDisplayName should return same result";
        
        // Check that the display name for a different locale is different
        String displayNameFrench = month.getDisplayName(TextStyle.FULL, Locale.FRENCH);
        assert !displayNameDefault.equals(displayNameFrench) : "Display name in French should differ from English";
    }
}
