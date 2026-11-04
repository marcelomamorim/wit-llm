/* @test    @summary Test for sun.text.IntHashtable putInternal method to verify behavior with key values */
/* @run main IntHashtableWitupTest */

import sun.text.IntHashtable;

public class IntHashtableWitupTest {

    public static void main(String[] args) {
        testPutInternalWithDeletedKey();
        testPutInternalWithNormalKey();
    }

    private static void testPutInternalWithDeletedKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(1, 10);
        ht.remove(1);
        try {
            ht.putInternal(1, 20);
            System.out.println("testPutInternalWithDeletedKey passed");
        } catch (IllegalArgumentException e) {
            System.out.println("testPutInternalWithDeletedKey failed: " + e);
        }
    }

    private static void testPutInternalWithNormalKey() {
        IntHashtable ht = new IntHashtable();
        ht.put(2, 15);
        try {
            ht.putInternal(2, 25);
            if (ht.get(2) == 25) {
                System.out.println("testPutInternalWithNormalKey passed");
            } else {
                System.out.println("testPutInternalWithNormalKey failed: value mismatch");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("testPutInternalWithNormalKey failed: " + e);
        }
    }
}
