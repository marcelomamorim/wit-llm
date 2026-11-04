/* @test    @summary Test PropertyPermission constructor with various actions    @run main PropertyPermissionWitupTest */

import java.util.PropertyPermission;

public class PropertyPermissionWitupTest {

    public static void main(String[] args) {
        testConstructorWithReadAction();
        testConstructorWithWriteAction();
        testConstructorWithAllActions();
        testConstructorWithEmptyActions();
        testConstructorWithNullName();
        testConstructorWithNullActions();
    }

    static void testConstructorWithReadAction() {
        PropertyPermission perm = new PropertyPermission("java.home", "read");
        assert perm.getName().equals("java.home") : "Name mismatch";
        assert perm.implies(new PropertyPermission("java.home", "read")) : "Should imply same permission";
    }

    static void testConstructorWithWriteAction() {
        PropertyPermission perm = new PropertyPermission("os.name", "write");
        assert perm.getName().equals("os.name") : "Name mismatch";
        assert perm.implies(new PropertyPermission("os.name", "write")) : "Should imply same permission";
    }

    static void testConstructorWithAllActions() {
        PropertyPermission perm = new PropertyPermission("java.version", "read,write");
        assert perm.getName().equals("java.version") : "Name mismatch";
        assert perm.implies(new PropertyPermission("java.version", "read")) : "Should imply read";
        assert perm.implies(new PropertyPermission("java.version", "write")) : "Should imply write";
    }

    static void testConstructorWithEmptyActions() {
        PropertyPermission perm = new PropertyPermission("user.home", "");
        assert perm.getName().equals("user.home") : "Name mismatch";
        assert !perm.implies(new PropertyPermission("user.home", "read")) : "Empty actions should not imply read";
    }

    static void testConstructorWithNullName() {
        try {
            new PropertyPermission(null, "read");
            assert false : "Null name should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullActions() {
        try {
            new PropertyPermission("java.home", null);
            // null actions are allowed, treated as empty
            PropertyPermission perm = new PropertyPermission("java.home", null);
            assert perm.getName().equals("java.home") : "Name mismatch";
        } catch (NullPointerException e) {
            assert false : "Null actions should not throw";
        }
    }
}
