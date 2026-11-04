/*
 * @test
 * @summary Checks ordering and consistency of Runtime.Version.compareTo for parsed version strings
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime.Version;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testEqualVersionsCompareToZero();
        testPatchOrdering();
        testMajorOrdering();
        testAntisymmetry();
        testReflexivity();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEqualVersionsCompareToZero() {
        Version a = Version.parse("11.0.1");
        Version b = Version.parse("11.0.1");
        check(a.compareTo(b) == 0, "equal versions must compare to 0");
    }

    static void testPatchOrdering() {
        Version lower = Version.parse("11.0.1");
        Version higher = Version.parse("11.0.2");
        check(lower.compareTo(higher) < 0, "11.0.1 must be less than 11.0.2");
        check(higher.compareTo(lower) > 0, "11.0.2 must be greater than 11.0.1");
    }

    static void testMajorOrdering() {
        Version ten = Version.parse("10.0.2");
        Version eleven = Version.parse("11.0.1");
        check(ten.compareTo(eleven) < 0, "10.0.2 must be less than 11.0.1");
        check(eleven.compareTo(ten) > 0, "11.0.1 must be greater than 10.0.2");
    }

    static void testAntisymmetry() {
        Version a = Version.parse("9.0.4");
        Version b = Version.parse("11.0.1");
        int ab = a.compareTo(b);
        int ba = b.compareTo(a);
        check(Integer.signum(ab) == -Integer.signum(ba),
              "compareTo must be antisymmetric in sign");
    }

    static void testReflexivity() {
        Version v = Version.parse("11.0.2");
        check(v.compareTo(v) == 0, "a version must compare equal to itself");
    }
}
