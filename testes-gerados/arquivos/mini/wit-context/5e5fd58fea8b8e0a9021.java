/*
 * @test
 * @summary Deterministic tests for java.io.FilePermission constructor with invalid and valid actions
 * @run main FilePermissionWitupTest
 */

import java.io.FilePermission;

public class FilePermissionWitupTest {
    public static void main(String[] args) {
        testValidActions();
        testInvalidActions();
        testNullActions();
    }

    private static void testValidActions() {
        // Valid actions strings should not throw
        new FilePermission("/tmp/file", "read");
        new FilePermission("/tmp/file", "write");
        new FilePermission("/tmp/file", "execute");
        new FilePermission("/tmp/file", "delete");
        new FilePermission("/tmp/file", "readlink");
        new FilePermission("/tmp/file", "read,write,execute,delete,readlink");
    }

    private static void testInvalidActions() {
        // Actions with invalid keywords or malformed should throw IllegalArgumentException
        try {
            new FilePermission("/tmp/file", "invalid");
            throw new RuntimeException("Expected IllegalArgumentException for invalid action keyword");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new FilePermission("/tmp/file", "read,invalid");
            throw new RuntimeException("Expected IllegalArgumentException for mixed invalid action keyword");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new FilePermission("/tmp/file", "");
            throw new RuntimeException("Expected IllegalArgumentException for empty action string");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new FilePermission("/tmp/file", " ");
            throw new RuntimeException("Expected IllegalArgumentException for blank action string");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testNullActions() {
        // Null actions string should throw NullPointerException as per standard constructor contract
        try {
            new FilePermission("/tmp/file", null);
            throw new RuntimeException("Expected NullPointerException for null actions");
        } catch (NullPointerException expected) {
        }
    }
}
