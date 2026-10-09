/* @test    @summary Test UUID.node() method for time-based UUIDs    @run main UUIDWitupTest_P9016fa6e1858 */

import java.util.UUID;

public class UUIDWitupTest_P9016fa6e1858 {
    public static void main(String[] args) {
        // Create a time-based UUID (version 1) with specific mostSigBits and leastSigBits
        long mostSigBits = 0x0000000000000001L;
        long leastSigBits = 0x0000FFFFFFFFFFFFL; // node bits
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Verify that node() returns the leastSigBits & 0x0000FFFFFFFFFFFFL
        long nodeValue = uuid.node();
        if (nodeValue != (leastSigBits & 0x0000FFFFFFFFFFFFL)) {
            throw new AssertionError("node() returned " + nodeValue + ", expected " + (leastSigBits & 0x0000FFFFFFFFFFFFL));
        }
        // Also test that calling node() on a non-time-based UUID throws UnsupportedOperationException
        long msbNonTime = 0x2000000000000000L; // version 2 or other
        UUID nonTimeUUID = new UUID(msbNonTime, leastSigBits);
        try {
            nonTimeUUID.node();
            throw new AssertionError("Expected UnsupportedOperationException for non-time-based UUID");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
