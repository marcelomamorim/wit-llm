/* @test    @summary Test for LinkPermission constructor with valid and invalid names    @run main LinkPermissionWitupTest */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {

    public static void main(String[] args) {
        testValidHard();
        testValidSymbolic();
        testInvalidName();
    }

    private static void testValidHard() {
        try {
            LinkPermission perm = new LinkPermission("hard");
            System.out.println("testValidHard passed");
        } catch (Exception e) {
            System.err.println("testValidHard failed: " + e);
        }
    }

    private static void testValidSymbolic() {
        try {
            LinkPermission perm = new LinkPermission("symbolic");
            System.out.println("testValidSymbolic passed");
        } catch (Exception e) {
            System.err.println("testValidSymbolic failed: " + e);
        }
    }

    private static void testInvalidName() {
        try {
            LinkPermission perm = new LinkPermission("invalid");
            System.err.println("testInvalidName failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidName passed");
        } catch (Exception e) {
            System.err.println("testInvalidName failed: " + e);
        }
    }
}
