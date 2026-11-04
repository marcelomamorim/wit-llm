/*
 * @test
 * @summary Test BasicPermission(String name, String actions) constructor for exception handling
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;

public class BasicPermissionWitupTest {
    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testEmptyNameThrowsIAE();
        testValidNameDoesNotThrow();
    }

    private static void testNullNameThrowsNPE() {
        try {
            new BasicPermission(null, "");
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyNameThrowsIAE() {
        try {
            new BasicPermission("", "");
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidNameDoesNotThrow() {
        BasicPermission p = new BasicPermission("validName", "");
        if (!"validName".equals(p.getName())) {
            throw new RuntimeException("Name mismatch: expected 'validName' but got '" + p.getName() + "'");
        }
    }
}
