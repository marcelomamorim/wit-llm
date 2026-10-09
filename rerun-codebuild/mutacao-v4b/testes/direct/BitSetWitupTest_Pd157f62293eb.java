/* @test
   @summary Verifies the behavior of BitSet.clear(int, int).
   @run main BitSetWitupTest_Pd157f62293eb
 */
import java.util.BitSet;
public class BitSetWitupTest_Pd157f62293eb {
    public static void main(String[] args) {
        BitSet bs = new BitSet();
        // Set bits from 0 to 10
        bs.set(0, 11);
        // Clear bits from 3 to 7
        bs.clear(3, 8);
        // Verify bits 0 to 2 are still set
        if (!bs.get(0) || !bs.get(2)) {
            throw new AssertionError("Bits 0 to 2 should be set");
        }
        // Verify bits 3 to 7 are cleared
        for (int i = 3; i <= 7; i++) {
            if (bs.get(i)) {
                throw new AssertionError("Bit " + i + " should be cleared");
            }
        }
        // Verify bits 8 to 10 are still set
        for (int i = 8; i <= 10; i++) {
            if (!bs.get(i)) {
                throw new AssertionError("Bit " + i + " should be set");
            }
        }
        // Clear a range that exceeds current size
        bs.clear(15, 20);
        // Confirm bits 15 to 19 are cleared (should be no exception)
        for (int i = 15; i <= 19; i++) {
            if (bs.get(i)) {
                throw new AssertionError("Bit " + i + " should be cleared");
            }
        }
        // Clear a range with fromIndex == toIndex (no change)
        bs.clear(5, 5); // should do nothing
        if (!bs.get(0) || !bs.get(10)) {
            throw new AssertionError("Bits 0 and 10 should remain set");
        }
    }
}
