/*
 * @test
 * @summary Verify ThaiBuddhistEra.getDisplayName returns non-empty, distinct and deterministic names for both eras
 * @run main ThaiBuddhistEraWitupTest
 */
import java.time.chrono.ThaiBuddhistEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class ThaiBuddhistEraWitupTest {

    public static void main(String[] args) {
        ThaiBuddhistEra be = ThaiBuddhistEra.of(1);
        ThaiBuddhistEra beforeBe = ThaiBuddhistEra.of(0);

        String beFull = be.getDisplayName(TextStyle.FULL, Locale.US);
        String beforeFull = beforeBe.getDisplayName(TextStyle.FULL, Locale.US);
        String beShort = be.getDisplayName(TextStyle.SHORT, Locale.US);
        String beforeShort = beforeBe.getDisplayName(TextStyle.SHORT, Locale.US);

        check(beFull != null && !beFull.isEmpty(), "FULL name for BE must be non-empty");
        check(beforeFull != null && !beforeFull.isEmpty(), "FULL name for before BE must be non-empty");
        check(beShort != null && !beShort.isEmpty(), "SHORT name for BE must be non-empty");
        check(beforeShort != null && !beforeShort.isEmpty(), "SHORT name for before BE must be non-empty");

        check(!beFull.equals(beforeFull), "BE and before-BE FULL names must differ");
        check(!beShort.equals(beforeShort), "BE and before-BE SHORT names must differ");

        check(beFull.equals(be.getDisplayName(TextStyle.FULL, Locale.US)),
              "getDisplayName must be deterministic for BE");
        check(beforeFull.equals(beforeBe.getDisplayName(TextStyle.FULL, Locale.US)),
              "getDisplayName must be deterministic for before BE");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
