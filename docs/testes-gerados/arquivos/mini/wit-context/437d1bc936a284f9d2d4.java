/*
 * @test
 * @summary Test sun.security.util.DerValue.getBitString(boolean) for correct behavior and exception throwing
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.DerInputBuffer;
import java.io.IOException;
import java.io.ByteArrayInputStream;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBitStringWithInvalidTagThrows();
        testGetBitStringWithValidBitStringTag();
        testGetBitStringWithTagImplicitTrue();
    }

    private static void testGetBitStringWithInvalidTagThrows() {
        // Construct DerValue with tag != tag_BitString (3) and tagImplicit == false
        // Expect IOException
        byte[] data = {0x01, 0x01, 0x00}; // BOOLEAN tag (0x01), length 1, value 0
        DerValue dv = new DerValue(data);
        try {
            dv.getBitString(false);
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testGetBitStringWithValidBitStringTag() throws IOException {
        // Construct DerValue with tag == tag_BitString (3) and tagImplicit == false
        // Should return non-null byte array
        byte[] bitStringData = {0x03, 0x02, 0x00, (byte)0xA0}; // BIT STRING tag, length 2, unused bits 0, bits 0xA0
        DerValue dv = new DerValue(bitStringData);
        byte[] result = dv.getBitString(false);
        if (result == null || result.length == 0) {
            throw new RuntimeException("getBitString returned null or empty array for valid bit string");
        }
    }

    private static void testGetBitStringWithTagImplicitTrue() throws IOException {
        // Construct DerValue with any tag and tagImplicit == true
        // Should delegate to buffer.getBitString() without tag check
        byte[] bitStringData = {0x05, 0x00}; // NULL tag, length 0
        DerValue dv = new DerValue(bitStringData);
        // forcibly set tag to something other than 3
        dv.tag = 0x05; // NULL
        // Should not throw IOException even though tag != 3
        byte[] result = dv.getBitString(true);
        if (result == null) {
            throw new RuntimeException("getBitString returned null for tagImplicit true");
        }
    }
}