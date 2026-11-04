/* @test    @summary Test PropertyPermission constructor with various inputs to observe exception behavior    @run main PropertyPermissionWitupTest */

import java.util.PropertyPermission;

public class PropertyPermissionWitupTest {

    public static void main(String[] args) {
        testNullActions();
        testEmptyActions();
        testReadAction();
        testWriteAction();
        testNullName();
        testInvalidActions();
    }

    private static void testNullActions() {
        try {
            new PropertyPermission("java.home", null);
            System.out.println("testNullActions: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNullActions: Passed");
        } catch (Throwable t) {
            System.out.println("testNullActions: Failed - Unexpected exception: " + t);
        }
    }

    private static void testEmptyActions() {
        try {
            PropertyPermission pp = new PropertyPermission("java.home", "");
            System.out.println("testEmptyActions: Passed - " + pp);
        } catch (Throwable t) {
            System.out.println("testEmptyActions: Failed - " + t);
        }
    }

    private static void testReadAction() {
        try {
            PropertyPermission pp = new PropertyPermission("java.home", "read");
            System.out.println("testReadAction: Passed - " + pp);
        } catch (Throwable t) {
            System.out.println("testReadAction: Failed - " + t);
        }
    }

    private static void testWriteAction() {
        try {
            PropertyPermission pp = new PropertyPermission("java.home", "write");
            System.out.println("testWriteAction: Passed - " + pp);
        } catch (Throwable t) {
            System.out.println("testWriteAction: Failed - " + t);
        }
    }

    private static void testNullName() {
        try {
            new PropertyPermission(null, "read");
            System.out.println("testNullName: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNullName: Passed");
        } catch (Throwable t) {
            System.out.println("testNullName: Failed - Unexpected exception: " + t);
        }
    }

    private static void testInvalidActions() {
        try {
            new PropertyPermission("java.home", "invalidAction");
            System.out.println("testInvalidActions: Passed - No exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidActions: Passed - Caught IllegalArgumentException");
        } catch (Throwable t) {
            System.out.println("testInvalidActions: Failed - Unexpected exception: " + t);
        }
    }
}
