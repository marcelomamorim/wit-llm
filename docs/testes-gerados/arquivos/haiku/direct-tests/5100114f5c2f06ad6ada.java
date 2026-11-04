/*
 * @test
 * @summary checkPackageDefinition rejects null and accepts unrestricted package names deterministically
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;

public class SecurityManagerWitupTest {

    static int failures = 0;

    static void fail(String msg) {
        failures++;
        System.err.println("FAIL: " + msg);
    }

    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        testNullPackageThrowsNPE(sm);
        testUnrestrictedPackageDoesNotThrowUnexpectedly(sm);
        testRestrictedPackageOnlyFailsWithDefinePermission(sm);
        testRepeatedCallsAreConsistent(sm);

        if (failures > 0) {
            throw new RuntimeException(failures + " test(s) failed");
        }
    }

    static void testNullPackageThrowsNPE(SecurityManager sm) {
        try {
            sm.checkPackageDefinition(null);
            fail("expected NullPointerException for null package name");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(pkg, ...)
        }
    }

    static void testUnrestrictedPackageDoesNotThrowUnexpectedly(SecurityManager sm) {
        try {
            sm.checkPackageDefinition("witup.unrestricted.pkg");
        } catch (SecurityException e) {
            fail("unexpected SecurityException for unrestricted package: " + e);
        }
    }

    static void testRestrictedPackageOnlyFailsWithDefinePermission(SecurityManager sm) {
        String pkg = "sun.witup.restricted";
        try {
            sm.checkPackageDefinition(pkg);
        } catch (SecurityException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("defineClassInPackage." + pkg)) {
                fail("SecurityException message does not reference defineClassInPackage." + pkg + ": " + msg);
            }
        }
    }

    static void testRepeatedCallsAreConsistent(SecurityManager sm) {
        String pkg = "witup.repeat.pkg";
        boolean first;
        boolean second;
        try {
            sm.checkPackageDefinition(pkg);
            first = true;
        } catch (SecurityException e) {
            first = false;
        }
        try {
            sm.checkPackageDefinition(pkg);
            second = true;
        } catch (SecurityException e) {
            second = false;
        }
        if (first != second) {
            fail("inconsistent results across repeated calls for " + pkg);
        }
    }
}
