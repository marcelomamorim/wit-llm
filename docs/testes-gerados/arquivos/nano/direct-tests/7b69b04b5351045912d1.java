/* @test    @summary Test IntHashtable.put method for basic functionality and rehashing behavior    @run main IntHashtableWitupTest */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testPutBasic();
        testPutRehash();
    }

    private static void testPutBasic() {
        IntHashtable ht = new IntHashtable();
        int initialSize = ht.size();
        ht.put(10, 100);
        int value = ht.get(10);
        if (value != 100) {
            throw new AssertionError("Expected value 100 for key 10, but got " + value);
        }
        if (ht.size() != initialSize + 1) {
            throw new AssertionError("Size should have increased by 1 after put.");
        }
    }

    private static void testPutRehash() {
        IntHashtable ht = new IntHashtable(1); // small initial size to force rehash
        int initialSize = ht.size();
        for (int i = 0; i < 20; i++) {
            ht.put(i, i * 10);
        }
        boolean allPresent = true;
        for (int i = 0; i < 20; i++) {
            if (ht.get(i) != i * 10) {
                allPresent = false;
                break;
            }
        }
        if (!allPresent) {
            throw new AssertionError("Not all keys found after rehashing.");
        }
        if (ht.size() != 20) {
            throw new AssertionError("Size should be 20 after inserting 20 elements.");
        }
    }
}
