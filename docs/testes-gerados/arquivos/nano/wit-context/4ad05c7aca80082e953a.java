/*
 * @test
 * @summary Tests for sun.text.IntHashtable.get(int key)
 * @run main IntHashtableWitupTest
 */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testGetWithExistingKey();
        testGetWithDeletedKey();
        testGetWithNonexistentKey();
    }

    private static void testGetWithExistingKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(10, 100);
        int result = ht.get(10);
        if (result != 100) {
            throw new AssertionError("Expected 100, got " + result);
        }
    }

    private static void testGetWithDeletedKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(20, 200);
        ht.remove(20);
        int result = ht.get(20);
        if (result != ht.getDefaultValue()) {
            throw new AssertionError("Expected default value, got " + result);
        }
    }

    private static void testGetWithNonexistentKey() {
        IntHashtable ht = new IntHashtable();
        int result = ht.get(999);
        if (result != ht.getDefaultValue()) {
            throw new AssertionError("Expected default value, got " + result);
        }
    }
}
