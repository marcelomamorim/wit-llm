/*
 * @test
 * @summary Deterministic tests for sun.text.IntHashtable.put(int,int) method
 * @run main IntHashtableWitupTest
 */

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testPutAndGet();
        testPutOverwrite();
        testPutWithDeletedKey();
    }

    private static void testPutAndGet() {
        sun.text.IntHashtable table = new sun.text.IntHashtable();
        int key = 12345;
        int value = 67890;
        table.put(key, value);
        int retrieved = table.get(key);
        if (retrieved != value) {
            throw new RuntimeException("testPutAndGet failed: expected " + value + " but got " + retrieved);
        }
        if (table.size() != 1) {
            throw new RuntimeException("testPutAndGet failed: expected size 1 but got " + table.size());
        }
    }

    private static void testPutOverwrite() {
        sun.text.IntHashtable table = new sun.text.IntHashtable();
        int key = 54321;
        int value1 = 11111;
        int value2 = 22222;
        table.put(key, value1);
        table.put(key, value2); // overwrite
        int retrieved = table.get(key);
        if (retrieved != value2) {
            throw new RuntimeException("testPutOverwrite failed: expected " + value2 + " but got " + retrieved);
        }
        if (table.size() != 1) {
            throw new RuntimeException("testPutOverwrite failed: expected size 1 but got " + table.size());
        }
    }

    private static void testPutWithDeletedKey() {
        sun.text.IntHashtable table = new sun.text.IntHashtable();
        int key = 100;
        int value = 200;
        table.put(key, value);
        table.remove(key);
        if (!table.isEmpty()) {
            throw new RuntimeException("testPutWithDeletedKey failed: expected empty after remove");
        }
        // Now put again with same key after deletion
        table.put(key, value);
        int retrieved = table.get(key);
        if (retrieved != value) {
            throw new RuntimeException("testPutWithDeletedKey failed: expected " + value + " but got " + retrieved);
        }
        if (table.size() != 1) {
            throw new RuntimeException("testPutWithDeletedKey failed: expected size 1 but got " + table.size());
        }
    }
}
