/*
 * @test
 * @summary Tests the LinkPermission(String name) constructor for valid and invalid names
 * @run main LinkPermissionWitupTest
 */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    static boolean throwsIAE(String name) {
        try {
            new LinkPermission(name);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    static boolean throwsNPE(String name) {
        try {
            new LinkPermission(name);
            return false;
        } catch (NullPointerException e) {
            return true;
        }
    }

    public static void main(String[] args) {
        LinkPermission hard = new LinkPermission("hard");
        check("hard".equals(hard.getName()), "getName() should return \"hard\"");

        LinkPermission symbolic = new LinkPermission("symbolic");
        check("symbolic".equals(symbolic.getName()), "getName() should return \"symbolic\"");

        LinkPermission withEmptyActions = new LinkPermission("hard", "");
        check("hard".equals(withEmptyActions.getName()), "two-arg constructor with empty actions should keep name");

        check(throwsIAE("soft"), "invalid name \"soft\" should throw IllegalArgumentException");
        check(throwsIAE("HARD"), "name is case-sensitive: \"HARD\" should throw IllegalArgumentException");
        check(throwsIAE("symbolic "), "name with trailing space should throw IllegalArgumentException");
        check(throwsIAE(""), "empty name should throw IllegalArgumentException");
        check(throwsNPE(null), "null name should throw NullPointerException");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
