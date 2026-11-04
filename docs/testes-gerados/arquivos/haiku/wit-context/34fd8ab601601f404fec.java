/*
 * @test
 * @summary Tests BasicPermission(String) constructor with null, empty and valid names
 * @run main BasicPermissionWitupTest
 */

public class BasicPermissionWitupTest {

    static class TestPerm extends BasicPermission {
        private static final long serialVersionUID = 1L;

        TestPerm(String name) {
            super(name);
        }
    }

    public static void main(String[] args) {
        testNullName();
        testEmptyName();
        testValidName();
        testWildcardImplies();
        testNonWildcardImplies();
    }

    static void testNullName() {
        try {
            new TestPerm(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name");
    }

    static void testEmptyName() {
        try {
            new TestPerm("");
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for empty name");
    }

    static void testValidName() {
        TestPerm p = new TestPerm("exit");
        if (!"exit".equals(p.getName())) {
            throw new RuntimeException("unexpected name: " + p.getName());
        }
    }

    static void testWildcardImplies() {
        if (!new TestPerm("java.*").implies(new TestPerm("java.util.List"))) {
            throw new RuntimeException("java.* should imply java.util.List");
        }
        if (!new TestPerm("*").implies(new TestPerm("a.b"))) {
            throw new RuntimeException("* should imply a.b");
        }
    }

    static void testNonWildcardImplies() {
        if (!new TestPerm("java.util").implies(new TestPerm("java.util"))) {
            throw new RuntimeException("identical names should imply each other");
        }
        if (new TestPerm("java.util").implies(new TestPerm("java.*"))) {
            throw new RuntimeException("java.util should not imply java.*");
        }
    }
}
