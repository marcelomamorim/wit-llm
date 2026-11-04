/*
 * @test
 * @summary Verify IntHashtable.get returns stored values, updated values, and the default value for absent or removed keys
 * @modules java.base/sun.text
 * @run main IntHashtableWitupTest
 */
import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        IntHashtable t = new IntHashtable();
        check(t.get(5) == 0, "absent key on empty table should return default 0");

        t.put(5, 50);
        check(t.get(5) == 50, "get after put should return stored value");

        t.put(5, 51);
        check(t.get(5) == 51, "get after overwrite should return new value");
        check(t.size() == 1, "overwrite should not change size");

        t.setDefaultValue(-7);
        check(t.get(6) == -7, "absent key should return new default value");
        check(t.get(5) == 51, "present key should keep its value after default change");

        t.remove(5);
        check(t.get(5) == -7, "removed key should return default value");
        check(t.isEmpty(), "table should be empty after removing only key");

        IntHashtable b = new IntHashtable(10);
        for (int i = 1; i <= 500; i++) {
            b.put(i, i * 3);
        }
        check(b.size() == 500, "size should match number of distinct keys");
        for (int i = 1; i <= 500; i++) {
            check(b.get(i) == i * 3, "value mismatch after bulk insert for key " + i);
        }
        check(b.get(1000) == 0, "absent key after bulk insert should return default 0");

        for (int i = 2; i <= 500; i += 2) {
            b.remove(i);
        }
        check(b.size() == 250, "size should drop after removals");
        for (int i = 2; i <= 500; i += 2) {
            check(b.get(i) == 0, "removed even key should return default for key " + i);
        }
        for (int i = 1; i <= 499; i += 2) {
            check(b.get(i) == i * 3, "odd key should keep value for key " + i);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
