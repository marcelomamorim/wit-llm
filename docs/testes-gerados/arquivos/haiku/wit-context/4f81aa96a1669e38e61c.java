/*
 * @test
 * @summary Witup-derived tests for BasicPermission(String, String) constructor exception paths
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;

public class BasicPermissionWitupTest {

    static class TestPermission extends BasicPermission {
        private static final long serialVersionUID = 1L;

        TestPermission(String name, String actions) {
            super(name, actions);
        }
    }

    static void expectNullPointerException(String name, String actions) {
        try {
            new TestPermission(name, actions);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for name=" + name);
    }

    static void expectIllegalArgumentException(String name, String actions) {
        try {
            new TestPermission(name, actions);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for name=\"" + name + "\"");
    }

    public static void main(String[] args) {
        expectNullPointerException(null, "read");
        expectNullPointerException(null, null);

        expectIllegalArgumentException("", "read");
        expectIllegalArgumentException("", null);

        TestPermission wildcard = new TestPermission("*", null);
        if (!"*".equals(wildcard.getName())) {
            throw new RuntimeException("Unexpected name for wildcard permission: " + wildcard.getName());
        }

        TestPermission javaWildcard = new TestPermission("java.*", "ignored");
        if (!"java.*".equals(javaWildcard.getName())) {
            throw new RuntimeException("Unexpected name: " + javaWildcard.getName());
        }
        if (!javaWildcard.implies(new TestPermission("java.lang", ""))) {
            throw new RuntimeException("java.* should imply java.lang");
        }
        if (new TestPermission("java.lang", "").implies(javaWildcard)) {
            throw new RuntimeException("java.lang should not imply java.*");
        }
    }
}
