/*
 * @test
 * @summary White-box style checks of Checks.requireQualifiedClassName via its public contract
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        testValidQualifiedNameIsReturnedUnchanged();
        testNestedQualifiedNameIsReturnedUnchanged();
        testNameWithoutDotIsRejected();
        testNullNameIsRejected();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void testValidQualifiedNameIsReturnedUnchanged() {
        String name = "p.C";
        String result = Checks.requireQualifiedClassName("service type name", name);
        if (!"p.C".equals(result)) {
            fail("expected p.C but got " + result);
        }
    }

    private static void testNestedQualifiedNameIsReturnedUnchanged() {
        String result = Checks.requireQualifiedClassName("service type name", "a.b.C");
        if (!"a.b.C".equals(result)) {
            fail("expected a.b.C but got " + result);
        }
    }

    private static void testNameWithoutDotIsRejected() {
        try {
            Checks.requireQualifiedClassName("service type name", "NoPackage");
            fail("expected IllegalArgumentException for name without package");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNullNameIsRejected() {
        try {
            Checks.requireQualifiedClassName("service type name", null);
            fail("expected IllegalArgumentException for null name");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void fail(String msg) {
        failures++;
        System.err.println("FAIL: " + msg);
    }
}
