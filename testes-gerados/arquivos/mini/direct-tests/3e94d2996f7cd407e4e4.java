/*
 * @test
 * @summary Test PropertyPermission constructor with various action strings
 * @run main java.util.PropertyPermissionWitupTest
 */

import java.util.PropertyPermission;

public class PropertyPermissionWitupTest {
    public static void main(String[] args) {
        testValidActions();
        testCaseInsensitiveActions();
        testEmptyActions();
        testNullName();
        testWildcardName();
    }

    private static void testValidActions() {
        PropertyPermission p1 = new PropertyPermission("java.home", "read");
        PropertyPermission p2 = new PropertyPermission("java.home", "write");
        PropertyPermission p3 = new PropertyPermission("java.home", "read,write");
        PropertyPermission p4 = new PropertyPermission("java.home", "write,read");
        // No exception means pass
    }

    private static void testCaseInsensitiveActions() {
        PropertyPermission p1 = new PropertyPermission("java.home", "READ");
        PropertyPermission p2 = new PropertyPermission("java.home", "Write");
        PropertyPermission p3 = new PropertyPermission("java.home", "ReAd,WrItE");
        // No exception means pass
    }

    private static void testEmptyActions() {
        try {
            new PropertyPermission("java.home", "");
            throw new RuntimeException("Expected IllegalArgumentException for empty actions");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNullName() {
        try {
            new PropertyPermission(null, "read");
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testWildcardName() {
        PropertyPermission p1 = new PropertyPermission("java.*", "read");
        PropertyPermission p2 = new PropertyPermission("*", "write");
        // No exception means pass
    }
}
