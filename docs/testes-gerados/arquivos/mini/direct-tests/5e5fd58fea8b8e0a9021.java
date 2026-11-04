/*
 * @test
 * @summary Basic tests for java.io.FilePermission constructor with various actions
 * @run main FilePermissionWitupTest
 */

import java.io.FilePermission;

public class FilePermissionWitupTest {
    public static void main(String[] args) {
        testValidActions();
        testCaseInsensitiveActions();
        testMultipleActions();
        testEmptyActions();
        testNullActions();
    }

    private static void testValidActions() {
        // Test each valid action individually
        new FilePermission("/tmp/file", "read");
        new FilePermission("/tmp/file", "write");
        new FilePermission("/tmp/file", "execute");
        new FilePermission("/tmp/file", "delete");
        new FilePermission("/tmp/file", "readlink");
    }

    private static void testCaseInsensitiveActions() {
        // Actions string should be case-insensitive
        new FilePermission("/tmp/file", "READ,Write,ExEcuTe,Delete,ReadLink");
    }

    private static void testMultipleActions() {
        // Multiple actions separated by commas
        new FilePermission("/tmp/file", "read,write,execute");
        new FilePermission("/tmp/file", "delete,readlink");
    }

    private static void testEmptyActions() {
        // Empty actions string should result in no permissions (mask=0)
        new FilePermission("/tmp/file", "");
    }

    private static void testNullActions() {
        // Null actions string should throw NullPointerException
        try {
            new FilePermission("/tmp/file", null);
            throw new RuntimeException("Expected NullPointerException for null actions");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
