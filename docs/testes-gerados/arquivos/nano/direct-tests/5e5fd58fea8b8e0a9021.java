/*
 * @test
 * @summary Test FilePermission constructor with various paths and actions
 * @run main FilePermissionWitupTest
 */

import java.io.FilePermission;

public class FilePermissionWitupTest {

    public static void main(String[] args) {
        testConstructorWithSimplePathAndActions();
        testConstructorWithWildcardPath();
        testConstructorWithRecursivePath();
        testConstructorWithAllFilesToken();
        testConstructorWithEmptyActions();
        testConstructorWithNullPath();
        testConstructorWithNullActions();
    }

    static void testConstructorWithSimplePathAndActions() {
        String path = "/tmp/testfile";
        String actions = "read,write";
        FilePermission fp = new FilePermission(path, actions);
        assert fp.getActions().contains("read") : "Actions should contain read";
        assert fp.getActions().contains("write") : "Actions should contain write";
        assert fp.getName().equals(path) : "Path should match";
    }

    static void testConstructorWithWildcardPath() {
        String path = "/tmp/*";
        String actions = "read";
        FilePermission fp = new FilePermission(path, actions);
        assert fp.getName().equals(path) : "Wildcard path should match";
        assert fp.getActions().contains("read") : "Actions should contain read";
    }

    static void testConstructorWithRecursivePath() {
        String path = "/tmp/-";
        String actions = "write";
        FilePermission fp = new FilePermission(path, actions);
        assert fp.getName().equals(path) : "Recursive path should match";
        assert fp.getActions().contains("write") : "Actions should contain write";
    }

    static void testConstructorWithAllFilesToken() {
        String path = "<<ALL FILES>>";
        String actions = "read";
        FilePermission fp = new FilePermission(path, actions);
        assert fp.getName().equals(path) : "All files token should match";
        assert fp.getActions().contains("read") : "Actions should contain read";
    }

    static void testConstructorWithEmptyActions() {
        String path = "/tmp/test";
        String actions = "";
        FilePermission fp = new FilePermission(path, actions);
        assert fp.getActions().isEmpty() : "Actions should be empty";
    }

    static void testConstructorWithNullPath() {
        try {
            new FilePermission(null, "read");
            assert false : "Null path should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullActions() {
        String path = "/tmp/test";
        try {
            new FilePermission(path, null);
            // null actions are allowed, so no exception expected
        } catch (NullPointerException e) {
            assert false : "Null actions should not throw";
        }
    }
}
