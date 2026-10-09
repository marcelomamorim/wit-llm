/* @test
   @summary Testa o método getDisplayName de java.util.TimeZone.
   @run main TimeZoneWitupTest_P3b3e2f86f23f
 */
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest_P3b3e2f86f23f {
    public static void main(String[] args) {
        testGetDisplayNameWithValidStyles();
        testGetDisplayNameWithInvalidStyle();
        testGetDisplayNameWithGMTId();
        testGetDisplayNameWithOffsetId();
    }

    private static void testGetDisplayNameWithValidStyles() {
        TimeZone tz = TimeZone.getDefault();
        String nameShort = tz.getDisplayName(true, TimeZone.SHORT, Locale.US);
        String nameLong = tz.getDisplayName(false, TimeZone.LONG, Locale.US);
        if (nameShort == null || nameLong == null) {
            throw new AssertionError("Display name should not be null for valid styles");
        }
    }

    private static void testGetDisplayNameWithInvalidStyle() {
        TimeZone tz = TimeZone.getDefault();
        try {
            tz.getDisplayName(true, 2, Locale.US); // estilo inválido
            throw new AssertionError("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    private static void testGetDisplayNameWithGMTId() {
        TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
        String name = tz.getDisplayName(false, TimeZone.SHORT, Locale.US);
        if (name == null || !name.contains("GMT")) {
            throw new AssertionError("Display name for GMT ID should contain 'GMT'");
        }
    }

    private static void testGetDisplayNameWithOffsetId() {
        TimeZone tz = TimeZone.getTimeZone("+02:00");
        String name = tz.getDisplayName(false, TimeZone.SHORT, Locale.US);
        if (name == null || !name.contains("+02:00")) {
            throw new AssertionError("Display name for offset ID should contain '+02:00'");
        }
    }
}
