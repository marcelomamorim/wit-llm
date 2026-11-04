/*
 * @test
 * @summary Tests LinkPermission two-argument constructor validation of name and actions
 * @run main LinkPermissionWitupTest
 */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void expectThrows(Class<? extends Throwable> expected, String name, String actions, String msg) {
        try {
            new LinkPermission(name, actions);
            failures++;
            System.err.println("FAILED (no exception): " + msg);
        } catch (Throwable t) {
            if (!expected.isInstance(t)) {
                failures++;
                System.err.println("FAILED (wrong exception " + t + "): " + msg);
            }
        }
    }

    public static void main(String[] args) {
        LinkPermission hardNull = new LinkPermission("hard", null);
        check("hard".equals(hardNull.getName()), "name is preserved for hard/null");
        check("".equals(hardNull.getActions()), "actions are empty for null actions");

        LinkPermission symEmpty = new LinkPermission("symbolic", "");
        check("symbolic".equals(symEmpty.getName()), "name is preserved for symbolic/empty");
        check("".equals(symEmpty.getActions()), "actions are empty for empty actions");

        check(new LinkPermission("hard", null).equals(new LinkPermission("hard")),
              "two-arg null actions equals one-arg constructor");
        check(!new LinkPermission("hard", null).equals(new LinkPermission("symbolic", null)),
              "different link names are not equal");

        expectThrows(IllegalArgumentException.class, "bogus", null, "invalid name with null actions");
        expectThrows(IllegalArgumentException.class, "HARD", null, "name check is case sensitive");
        expectThrows(IllegalArgumentException.class, "", null, "empty name");
        expectThrows(IllegalArgumentException.class, "hard", "read", "non-empty actions rejected");
        expectThrows(IllegalArgumentException.class, "symbolic", "x", "non-empty actions rejected for symbolic");
        expectThrows(IllegalArgumentException.class, "bogus", "read", "invalid name with invalid actions");
        expectThrows(NullPointerException.class, null, null, "null name");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
