/*
 * @test
 * @summary Test BitSet.previousSetBit(int) for boundary and exception cases
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    public static void main(String[] args) {
        BitSet bs = new BitSet();

        // Test fromIndex == -1 returns -1
        if (bs.previousSetBit(-1) != -1) {
            throw new AssertionError("previousSetBit(-1) should return -1");
        }

        // Test fromIndex < -1 throws IndexOutOfBoundsException
        try {
            bs.previousSetBit(-2);
            throw new AssertionError("previousSetBit(-2) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test fromIndex == 0 with empty BitSet returns -1
        if (bs.previousSetBit(0) != -1) {
            throw new AssertionError("previousSetBit(0) on empty BitSet should return -1");
        }

        // Set some bits and test previousSetBit behavior
        bs.set(0);
        bs.set(63);
        bs.set(64);
        bs.set(130);

        // previousSetBit from index beyond highest set bit returns highest set bit
        int highestSet = bs.previousSetBit(200);
        if (highestSet != 130) {
            throw new AssertionError("previousSetBit(200) should return 130 but got " + highestSet);
        }

        // previousSetBit from exact set bit index returns that index
        if (bs.previousSetBit(64) != 64) {
            throw new AssertionError("previousSetBit(64) should return 64");
        }

        // previousSetBit from index between set bits returns closest lower set bit
        if (bs.previousSetBit(129) != 63) {
            throw new AssertionError("previousSetBit(129) should return 63");
        }

        // previousSetBit from index 0 returns 0 if bit 0 is set
        if (bs.previousSetBit(0) != 0) {
            throw new AssertionError("previousSetBit(0) should return 0");
        }

        // previousSetBit from index 1 returns 0 if bit 0 is set
        if (bs.previousSetBit(1) != 0) {
            throw new AssertionError("previousSetBit(1) should return 0");
        }

        // previousSetBit from index 62 returns 0 if bit 0 is set and no bits between
        if (bs.previousSetBit(62) != 0) {
            throw new AssertionError("previousSetBit(62) should return 0");
        }

        System.out.println("All tests passed.");
    }
}
