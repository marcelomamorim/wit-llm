/* @test    @summary Test DerOutputStream.putTruncatedUnalignedBitString for normal behavior and OutOfMemoryError handling    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import sun.security.util.BitArray;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testPutTruncatedUnalignedBitStringNormal();
        testPutTruncatedUnalignedBitStringOutOfMemory();
    }

    private static void testPutTruncatedUnalignedBitStringNormal() throws IOException {
        BitArray bitArray = new BitArray(8);
        // Initialize bitArray with some data
        for (int i = 0; i < 8; i++) {
            bitArray.setBit(i, (i % 2 == 0));
        }
        DerOutputStream dos = new DerOutputStream();
        dos.putTruncatedUnalignedBitString(bitArray);
        // Verify that the output contains expected data
        byte[] result = dos.toByteArray();
        // Basic check: result should not be empty and should contain the tag and length
        if (result.length < 2) {
            throw new AssertionError("Output too short");
        }
        // Check that the first byte is a valid tag (e.g., 0x03 for BIT STRING)
        if (result[0] != 0x03) {
            throw new AssertionError("Unexpected tag: " + result[0]);
        }
        // Check that length matches remaining bytes
        int length = result[1] & 0xFF;
        if (length != result.length - 2) {
            throw new AssertionError("Length mismatch");
        }
        System.out.println("testPutTruncatedUnalignedBitStringNormal passed.");
    }

    private static void testPutTruncatedUnalignedBitStringOutOfMemory() {
        BitArray bitArray = new BitArray(8);
        // Fill bitArray with data
        for (int i = 0; i < 8; i++) {
            bitArray.setBit(i, true);
        }
        DerOutputStream dos = new DerOutputStream() {
            @Override
            public void ensureCapacity(int minCapacity) {
                // Simulate OutOfMemoryError when capacity exceeds a threshold
                if (minCapacity > 10) {
                    throw new OutOfMemoryError("Simulated OOM for testing");
                }
                super.ensureCapacity(minCapacity);
            }
        };
        boolean threw = false;
        try {
            dos.putTruncatedUnalignedBitString(bitArray);
        } catch (OutOfMemoryError e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected OutOfMemoryError was not thrown");
        }
        System.out.println("testPutTruncatedUnalignedBitStringOutOfMemory passed.");
    }
}
