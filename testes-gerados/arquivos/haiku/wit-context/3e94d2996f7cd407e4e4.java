/*
 * @test
 * @summary Witup-derived checks for the PropertyPermission(String, String) constructor: valid actions, wildcard names, invalid actions, and null/empty names
 * @run main PropertyPermissionWitupTest
 */

import java.util.PropertyPermission;

public class PropertyPermissionWitupTest {

    public static void main(String[] args) {
        validReadAction();
        validWriteAction();
        validReadWriteActions();
        uppercaseActionsAreAccepted();
        wildcardNameImpliesMatchingNames();
        invalidActionsThrowIAE();
        nullActionsThrowIAE();
        nullNameThrowsNPE();
        emptyNameThrowsIAE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIAE(String name, String actions) {
        try {
            new PropertyPermission(name, actions);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for actions=" + actions);
    }

    static void validReadAction() {
        PropertyPermission p = new PropertyPermission("java.home", "read");
        check("java.home".equals(p.getName()), "name not preserved");
        check("read".equals(p.getActions()), "actions mismatch for read: " + p.getActions());
    }

    static void validWriteAction() {
        PropertyPermission p = new PropertyPermission("user.dir", "write");
        check("user.dir".equals(p.getName()), "name not preserved");
        check("write".equals(p.getActions()), "actions mismatch for write: " + p.getActions());
    }

    static void validReadWriteActions() {
        PropertyPermission p = new PropertyPermission("user.name", "read,write");
        String actions = p.getActions();
        check(actions.contains("read"), "read missing from actions: " + actions);
        check(actions.contains("write"), "write missing from actions: " + actions);
        check(p.implies(new PropertyPermission("user.name", "read")), "read,write should imply read");
        check(p.implies(new PropertyPermission("user.name", "write")), "read,write should imply write");
    }

    static void uppercaseActionsAreAccepted() {
        PropertyPermission p = new PropertyPermission("os.name", "READ");
        check("read".equals(p.getActions()), "uppercase actions should be lowercased: " + p.getActions());
    }

    static void wildcardNameImpliesMatchingNames() {
        PropertyPermission p = new PropertyPermission("java.*", "read");
        check(p.implies(new PropertyPermission("java.home", "read")), "java.* should imply java.home");
        check(!p.implies(new PropertyPermission("user.dir", "read")), "java.* must not imply user.dir");
    }

    static void invalidActionsThrowIAE() {
        expectIAE("java.home", "");
        expectIAE("java.home", "execute");
        expectIAE("java.home", "read,execute");
    }

    static void nullActionsThrowIAE() {
        expectIAE("java.home", null);
    }

    static void nullNameThrowsNPE() {
        try {
            new PropertyPermission(null, "write");
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null name");
    }

    static void emptyNameThrowsIAE() {
        expectIAE("", "read");
    }
}
