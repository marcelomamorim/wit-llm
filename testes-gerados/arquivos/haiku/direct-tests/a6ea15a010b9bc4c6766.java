/*
 * @test
 * @summary Consistency checks between LocalizedMessage instance and static formatting paths
 * @modules java.base/sun.security.util
 * @run main LocalizedMessageWitupTest
 */

public class LocalizedMessageWitupTest {

    private static final String KEY = "invalid.null.input.value";

    public static void main(String[] args) throws Exception {
        LocalizedMessage lm = new LocalizedMessage(KEY);

        String viaInstance = lm.formatLocalized(42);
        String viaStatic = LocalizedMessage.getLocalized(KEY, 42);
        check(viaInstance.equals(viaStatic),
              "formatLocalized differs from getLocalized: " + viaInstance + " vs " + viaStatic);

        String nlInstance = lm.formatNonlocalized(42);
        String nlStatic = LocalizedMessage.getNonlocalized(KEY, 42);
        check(nlInstance.equals(nlStatic),
              "formatNonlocalized differs from getNonlocalized: " + nlInstance + " vs " + nlStatic);

        String nullArgsStatic = LocalizedMessage.getLocalized(KEY, (Object[]) null);
        String nullArgsInstance = lm.formatLocalized((Object[]) null);
        check(nullArgsStatic.equals(nullArgsInstance),
              "null arguments differ between static and instance paths");

        String repeat = LocalizedMessage.getLocalized(KEY, (Object[]) null);
        check(nullArgsStatic.equals(repeat),
              "getLocalized with null arguments is not deterministic");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
