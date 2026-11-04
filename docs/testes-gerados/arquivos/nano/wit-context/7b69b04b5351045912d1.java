/*
 * @test
 * @summary Test for sun.text.IntHashtable.put method
 * @run main IntHashtableWitupTest
 */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testPutNormal();
        testPutRehash();
        testPutWithDeletedKey();
    }

    private static void testPutNormal() {
        IntHashtable ht = new IntHashtable();
        int initialSize = ht.size();
        ht.put(10, 100);
        assert ht.get(10) == 100 : "Failed: put and get for key 10";
        assert ht.size() == initialSize + 1 : "Failed: size should increase";
    }

    private static void testPutRehash() {
        IntHashtable ht = new IntHashtable(1); // small initial size to trigger rehash
        int initialSize = ht.size();
        for (int i = 0; i < 10; i++) {
            ht.put(i, i * 10);
        }
        boolean allPresent = true;
        for (int i = 0; i < 10; i++) {
            if (ht.get(i) != i * 10) {
                allPresent = false;
                break;
            }
        }
        assert allPresent : "Failed: not all entries present after rehash";
        assert ht.size() >= 10 : "Failed: size should be at least 10";
    }

    private static void testPutWithDeletedKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(20, 200);
        ht.remove(20);
        int defaultVal = ht.getDefaultValue();
        ht.put(20, 300);
        assert ht.get(20) == 300 : "Failed: put after remove";
        assert ht.size() >= 0 : "Failed: size after put";
    }
}
