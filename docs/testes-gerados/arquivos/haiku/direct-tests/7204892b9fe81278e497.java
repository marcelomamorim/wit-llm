/*
 * @test
 * @summary Basic observable behavior of Runtime.Version.compareToIgnoreOptional
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {

    public static void main(String[] args) {
        Runtime.Version v1101 = Runtime.Version.parse("11.0.1");
        Runtime.Version v1101b = Runtime.Version.parse("11.0.1");
        Runtime.Version v1102 = Runtime.Version.parse("11.0.2");
        Runtime.Version v11 = Runtime.Version.parse("11");

        check(v1101.compareToIgnoreOptional(v1101b) == 0, "equal versions must compare to 0");
        check(v1101b.compareToIgnoreOptional(v1101) == 0, "equal versions must compare to 0 (reversed)");
        check(v1101.compareToIgnoreOptional(v1102) < 0, "11.0.1 must be lower than 11.0.2");
        check(v1102.compareToIgnoreOptional(v1101) > 0, "11.0.2 must be higher than 11.0.1");
        check(v11.compareToIgnoreOptional(v1101) < 0, "11 must be lower than 11.0.1");
        check(v1101.compareToIgnoreOptional(v11) > 0, "11.0.1 must be higher than 11");

        check(sign(v1101.compareToIgnoreOptional(v1102)) == sign(v1101.compareTo(v1102)),
              "sign must agree with compareTo for version-only strings");
        check(sign(v1102.compareToIgnoreOptional(v1101)) == sign(v1102.compareTo(v1101)),
              "sign must agree with compareTo for version-only strings (reversed)");
    }

    private static int sign(int x) {
        return Integer.signum(x);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
