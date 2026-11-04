/*
 * @test
 * @summary Black-box checks of the public IntHashtable API: put overwrite, many keys with rehash, remove, default value and equals
 * @modules java.base/sun.text
 * @run main IntHashtableWitupTest
 */
import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyTable();
        testPutAndGetOverwrite();
        testManyKeysTriggerRehash();
        testRemove();
        testDefaultValue();
        testEquals();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyTable() {
        IntHashtable t = new IntHashtable();
        check(t.size() == 0, "new table size must be 0");
        check(t.isEmpty(), "new table must be empty");

        IntHashtable sized = new IntHashtable(100);
        check(sized.size() == 0, "sized table size must be 0");
        check(sized.isEmpty(), "sized table must be empty");
    }

    static void testPutAndGetOverwrite() {
        IntHashtable t = new IntHashtable();
        t.put(5, 50);
        check(t.size() == 1, "size after first put must be 1");
        check(!t.isEmpty(), "table must not be empty after put");
        check(t.get(5) == 50, "get(5) must return 50");

        t.put(5, 60);
        check(t.size() == 1, "overwrite must not change size");
        check(t.get(5) == 60, "get(5) must return overwritten value 60");
    }

    static void testManyKeysTriggerRehash() {
        IntHashtable t = new IntHashtable();
        for (int i = 0; i < 1000; i++) {
            t.put(i, i * 2);
        }
        check(t.size() == 1000, "size must be 1000 after 1000 distinct puts");
        for (int i = 0; i < 1000; i++) {
            check(t.get(i) == i * 2, "value lost after rehash for key " + i);
        }
    }

    static void testRemove() {
        IntHashtable t = new IntHashtable();
        t.setDefaultValue(-7);
        for (int i = 1; i <= 10; i++) {
            t.put(i, i + 100);
        }
        t.remove(3);
        check(t.size() == 9, "size must decrease after remove");
        check(t.get(3) == -7, "removed key must return default value");
        check(t.get(4) == 104, "other keys must survive remove");

        t.remove(3);
        check(t.size() == 9, "removing absent key must not change size");

        t.remove(999);
        check(t.size() == 9, "removing never-inserted key must not change size");
    }

    static void testDefaultValue() {
        IntHashtable t = new IntHashtable();
        t.setDefaultValue(-1);
        check(t.getDefaultValue() == -1, "getDefaultValue must return value set");
        check(t.get(42) == -1, "absent key must return default value");

        t.put(42, 7);
        check(t.get(42) == 7, "present key must return its value, not default");
    }

    static void testEquals() {
        IntHashtable a = new IntHashtable();
        IntHashtable b = new IntHashtable();
        a.put(1, 2);
        b.put(1, 2);
        check(a.equals(b), "tables with same content must be equal");
        check(b.equals(a), "equality must be symmetric for same content");

        b.put(3, 4);
        check(!a.equals(b), "tables with different size must not be equal");
        check(!b.equals(a), "tables with different size must not be equal (reversed)");
    }
}
