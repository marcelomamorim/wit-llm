/* @test    @summary Test for IntHashtable.get() method to ensure correct value retrieval.    @run main IntHashtableWitupTest */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testGetExistingKey();
        testGetNonExistingKey();
        testGetWithDefaultValue();
    }

    private static void testGetExistingKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(10, 100);
        int result = ht.get(10);
        if (result != 100) {
            throw new AssertionError("Expected 100, but got " + result);
        }
    }

    private static void testGetNonExistingKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(20, 200);
        int result = ht.get(30); // key 30 not inserted
        int defaultVal = ht.getDefaultValue();
        if (result != defaultVal) {
            throw new AssertionError("Expected default value " + defaultVal + ", but got " + result);
        }
    }

    private static void testGetWithDefaultValue() {
        IntHashtable ht = new IntHashtable();
        ht.setDefaultValue(-1);
        ht.put(40, 400);
        int result = ht.get(50); // key 50 not inserted
        if (result != -1) {
            throw new AssertionError("Expected -1, but got " + result);
        }
    }
}
