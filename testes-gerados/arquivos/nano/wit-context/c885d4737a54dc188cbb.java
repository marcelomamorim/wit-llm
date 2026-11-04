/* @test    @summary Test for BitSet.set(int, boolean) method to verify exception paths and behavior.    @run main BitSetWitupTest */

import java.util.BitSet;

public class BitSetWitupTest {

    public static void main(String[] args) {
        testSetWithNegativeIndex();
        testSetWithValidIndex();
    }

    private static void testSetWithNegativeIndex() {
        BitSet bs = new BitSet();
        int invalidIndex = -1;
        boolean value = true;
        try {
            bs.set(invalidIndex, value);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for negative index");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testSetWithValidIndex() {
        BitSet bs = new BitSet();
        int validIndex = 0;
        boolean value = false;
        try {
            bs.set(validIndex, value);
            if (!bs.get(validIndex)) {
                System.out.println("PASS: Bit at index 0 correctly set to false");
            } else {
                System.out.println("FAIL: Bit at index 0 should be false after setting to false");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
