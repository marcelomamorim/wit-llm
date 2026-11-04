/*
 * @test
 * @summary Verifies that the public FilePermission(String, String) constructor stores the path and canonical actions
 * @run main FilePermissionWitupTest
 */

public class FilePermissionWitupTest {

    public static void main(String[] args) {
        FilePermission p = new FilePermission("/tmp/witup-test.txt", "read");
        check("/tmp/witup-test.txt".equals(p.getName()), "name for single action");
        check("read".equals(p.getActions()), "actions for single action");

        FilePermission q = new FilePermission("/tmp/witup-test.txt", "write,read");
        check("read,write".equals(q.getActions()), "actions must be canonicalized to read,write");

        FilePermission all = new FilePermission("<<ALL FILES>>", "read,write,execute,delete,readlink");
        check("<<ALL FILES>>".equals(all.getName()), "name for ALL FILES token");
        check(all.getActions().contains("read") && all.getActions().contains("write")
                && all.getActions().contains("execute") && all.getActions().contains("delete")
                && all.getActions().contains("readlink"), "all actions must be preserved");

        FilePermission same1 = new FilePermission("/tmp/witup-test.txt", "read,write");
        FilePermission same2 = new FilePermission("/tmp/witup-test.txt", "write,read");
        check(same1.equals(same2), "permissions with same path and action set must be equal");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("FAILED: " + message);
        }
    }
}
