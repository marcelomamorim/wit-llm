/*
 * @test
 * @summary Witup-derived behavioral tests for LinkPermission(String name, String actions) constructor
 * @run main LinkPermissionWitupTest
 */

import java.nio.file.LinkPermission;

public class LinkPermissionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIAE(String name, String actions) {
        try {
            new LinkPermission(name, actions);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for name=" + name + ", actions=" + actions);
    }

    static void expectNPE(String name, String actions) {
        try {
            new LinkPermission(name, actions);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for name=" + name);
    }

    public static void main(String[] args) {
        LinkPermission hardEmpty = new LinkPermission("hard", "");
        check("hard".equals(hardEmpty.getName()), "name should be hard");

        LinkPermission symbolicNull = new LinkPermission("symbolic", null);
        check("symbolic".equals(symbolicNull.getName()), "name should be symbolic");

        LinkPermission symbolicEmpty = new LinkPermission("symbolic", "");
        check("symbolic".equals(symbolicEmpty.getName()), "name should be symbolic (empty actions)");

        expectIAE("hard", "read");
        expectIAE("symbolic", "write");
        expectIAE("hard", "x");

        expectIAE("foo", "");
        expectIAE("foo", null);
        expectIAE("", "");
        expectIAE("HARD", "");

        expectNPE(null, "");

        System.out.println("All LinkPermissionWitupTest checks passed");
    }
}
