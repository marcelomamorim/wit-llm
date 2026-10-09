/* @test
   @summary Test the node() method of UUID for time-based UUIDs.
   @run main UUIDWitupTest_Pdd48d32c65b6
 */
import java.util.UUID;
public class UUIDWitupTest_Pdd48d32c65b6 {
    public static void main(String[] args) {
        // Create a UUID with version 1 (time-based)
        // Since the constructor is private, we simulate a time-based UUID by mocking or reflection if needed.
        // However, as per constraints, we test only observable behavior.
        // Here, we assume the existence of a UUID with version 1.
        // For demonstration, we create a UUID with known leastSigBits where node() can be called.
        // Note: In real tests, you'd use a factory or a known UUID.
        // For simplicity, we create a UUID with version 1 bits set.
        long mostSigBits = 0x0000000000001000L; // version 1 in bits 12-15
        long leastSigBits = 0x0000FFFFFFFFFFFFL; // maximum node value
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Verify that node() returns the leastSigBits masked appropriately
        long nodeValue = uuid.node();
        if (nodeValue != (leastSigBits & 0x0000FFFFFFFFFFFFL)) {
            throw new AssertionError("node() returned unexpected value: " + nodeValue);
        }
        // Test that calling node() on non-time-based UUID throws exception
        // Create a UUID with version != 1
        long msbNonTime = 0x0000000000002000L; // version 2
        UUID nonTimeUUID = new UUID(msbNonTime, leastSigBits);
        boolean exceptionThrown = false;
        try {
            nonTimeUUID.node();
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected exception was not thrown");
        }
    }
}
