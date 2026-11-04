/*
 * @test
 * @summary Tests the public BasicPermission(String name) constructor: name retention, null/empty rejection and wildcard semantics
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;

public class BasicPermissionWitupTest {

    public static void main(String[] args) {
        testNameIsRetained();
        testNullNameThrowsNPE();
        testEmptyNameThrowsIAE();
        testWildcardImpliesSubName();
        testNonWildcardDoesNotImplyWildcard();
        testStarImpliesAnyName();
    }

    private static void testNameIsRetained() {
        BasicPermission p = new BasicPermission("print.queueJob");
        if (!"print.queueJob".equals(p.getName())) {
            throw new RuntimeException("Unexpected name: " + p.getName());
        }
        if (p.getActions() == null || !p.getActions().isEmpty()) {
            throw new RuntimeException("Expected empty actions, got: " + p.getActions());
        }
    }

    private static void testNullNameThrowsNPE() {
        try {
            new BasicPermission(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null name");
    }

    private static void testEmptyNameThrowsIAE() {
        try {
            new BasicPermission("");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for empty name");
    }

    private static void testWildcardImpliesSubName() {
        BasicPermission wildcard = new BasicPermission("java.*");
        BasicPermission target = new BasicPermission("java.lang.String");
        if (!wildcard.implies(target)) {
            throw new RuntimeException("java.* should imply java.lang.String");
        }
    }

    private static void testNonWildcardDoesNotImplyWildcard() {
        BasicPermission named = new BasicPermission("java.lang");
        BasicPermission wildcard = new BasicPermission("java.*");
        if (named.implies(wildcard)) {
            throw new RuntimeException("java.lang should not imply java.*");
        }
    }

    private static void testStarImpliesAnyName() {
        BasicPermission star = new BasicPermission("*");
        if (!star.implies(new BasicPermission("exit"))) {
            throw new RuntimeException("* should imply any BasicPermission name");
        }
    }
}
