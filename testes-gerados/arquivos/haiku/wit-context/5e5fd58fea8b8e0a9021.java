/*
 * @test
 * @summary Witup-derived checks for the FilePermission(String, String) constructor: valid action parsing and IllegalArgumentException for null, empty, and unknown actions
 * @run main FilePermissionWitupTest
 */

import java.io.FilePermission;

public class FilePermissionWitupTest {

    public static void main(String[] args) {
        testValidReadAction();
        testValidReadWriteActions();
        testUppercaseActionsAreNormalized();
        testNullActionsRejected();
        testEmptyActionsRejected();
        testUnknownActionRejected();
    }

    static void testValidReadAction() {
        FilePermission p = new FilePermission("/tmp/a.txt", "read");
        if (!"read".equals(p.getActions())) {
            throw new RuntimeException("expected actions 'read' but got: " + p.getActions());
        }
    }

    static void testValidReadWriteActions() {
        FilePermission p = new FilePermission("/tmp/a.txt", "read,write");
        if (!"read,write".equals(p.getActions())) {
            throw new RuntimeException("expected actions 'read,write' but got: " + p.getActions());
        }
    }

    static void testUppercaseActionsAreNormalized() {
        FilePermission p = new FilePermission("/tmp/a.txt", "READ");
        if (!"read".equals(p.getActions())) {
            throw new RuntimeException("expected normalized actions 'read' but got: " + p.getActions());
        }
    }

    static void testNullActionsRejected() {
        try {
            new FilePermission("/tmp/a.txt", null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for null actions");
    }

    static void testEmptyActionsRejected() {
        try {
            new FilePermission("/tmp/a.txt", "");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for empty actions");
    }

    static void testUnknownActionRejected() {
        try {
            new FilePermission("/tmp/a.txt", "bogus");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for unknown action");
    }
}
