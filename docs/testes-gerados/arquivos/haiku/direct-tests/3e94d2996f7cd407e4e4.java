/*
 * @test
 * @summary Basic observable behavior of the PropertyPermission(String, String) constructor
 * @run main PropertyPermissionWitupTest
 */

import java.util.PropertyPermission;

public class PropertyPermissionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(String name, String actions) {
        try {
            new PropertyPermission(name, actions);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for name=" + name + " actions=" + actions);
    }

    public static void main(String[] args) {
        PropertyPermission p = new PropertyPermission("java.home", "read");
        check("java.home".equals(p.getName()), "name not preserved");
        check("read".equals(p.getActions()), "actions 'read' not preserved: " + p.getActions());

        PropertyPermission upper = new PropertyPermission("user.dir", "READ");
        check("read".equals(upper.getActions()), "actions should be lowercased: " + upper.getActions());

        PropertyPermission reversed = new PropertyPermission("user.dir", "write,read");
        check("read,write".equals(reversed.getActions()), "canonical order expected: " + reversed.getActions());

        PropertyPermission both = new PropertyPermission("user.dir", "read,write");
        check("read,write".equals(both.getActions()), "read,write not preserved: " + both.getActions());

        PropertyPermission wildcard = new PropertyPermission("java.*", "read");
        check(wildcard.implies(new PropertyPermission("java.home", "read")),
              "wildcard java.* should imply java.home read");
        check(!wildcard.implies(new PropertyPermission("java.home", "write")),
              "read-only permission must not imply write");

        expectIllegalArgument("java.home", "execute");
        expectIllegalArgument("", "read");
    }
}
