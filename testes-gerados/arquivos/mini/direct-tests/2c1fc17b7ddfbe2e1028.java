/*
 * @test
 * @summary Test com.sun.crypto.provider.ISO10126Padding.unpad method behavior
 * @run main ISO10126PaddingWitupTest
 */

import com.sun.crypto.provider.ISO10126Padding;

public class ISO10126PaddingWitupTest {
    public static void main(String[] args) throws Exception {
        ISO10126Padding padding = new ISO10126Padding(8);

        // Test null input returns 0
        if (padding.unpad(null, 0, 0) != 0) {
            throw new RuntimeException("Failed: null input with len=0 should return 0");
        }

        // Test len=0 returns 0 even if input non-null
        byte[] data = new byte[8];
        if (padding.unpad(data, 0, 0) != 0) {
            throw new RuntimeException("Failed: len=0 should return 0");
        }

        // Test valid padding: last byte = padding length (1 to blockSize)
        // Prepare data with padding length 4
        int padLen = 4;
        int off = 0;
        int len = 8;
        byte[] padded = new byte[len];
        // Fill last byte with padLen
        padded[len - 1] = (byte) padLen;
        int start = padding.unpad(padded, off, len);
        if (start != (off + len - padLen)) {
            throw new RuntimeException("Failed: valid padding start index incorrect");
        }

        // Test padValue < 1 returns -1
        padded[len - 1] = 0; // padValue = 0
        if (padding.unpad(padded, off, len) != -1) {
            throw new RuntimeException("Failed: padValue < 1 should return -1");
        }

        // Test padValue > blockSize returns -1
        padded[len - 1] = (byte) (padding.blockSize + 1); // padValue > blockSize
        if (padding.unpad(padded, off, len) != -1) {
            throw new RuntimeException("Failed: padValue > blockSize should return -1");
        }

        // Test start < off returns -1
        // Set padValue larger than len
        padded[len - 1] = (byte) (len + 1);
        if (padding.unpad(padded, off, len) != -1) {
            throw new RuntimeException("Failed: start < off should return -1");
        }

        System.out.println("All tests passed.");
    }
}
