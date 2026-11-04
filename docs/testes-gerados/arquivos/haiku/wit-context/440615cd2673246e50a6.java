/*
 * @test
 * @summary Witup-derived checks for ThaiBuddhistEra.getDisplayName
 * @run main ThaiBuddhistEraWitupTest
 */
import java.time.chrono.ThaiBuddhistEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class ThaiBuddhistEraWitupTest {

    public static void main(String[] args) {
        ThaiBuddhistEra be = ThaiBuddhistEra.of(1);
        ThaiBuddhistEra beforeBe = ThaiBuddhistEra.of(0);

        String beName = be.getDisplayName(TextStyle.FULL, Locale.US);
        String beforeName = beforeBe.getDisplayName(TextStyle.FULL, Locale.US);

        check(beName != null && !beName.isEmpty(), "BE display name must not be null or empty");
        check(beforeName != null && !beforeName.isEmpty(), "BEFORE_BE display name must not be null or empty");
        check(!beName.equals(beforeName), "BE and BEFORE_BE display names must differ");
        check(beName.equals(be.getDisplayName(TextStyle.FULL, Locale.US)),
              "getDisplayName must be deterministic for the same input");

        expectNpe(() -> be.getDisplayName(null, Locale.US), "null style must throw NullPointerException");
        expectNpe(() -> be.getDisplayName(TextStyle.FULL, null), "null locale must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private interface Action {
        void run();
    }

    private static void expectNpe(Action action, String message) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException(message);
    }
}
