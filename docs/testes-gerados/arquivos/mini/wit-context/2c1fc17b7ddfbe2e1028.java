/*
 * @test
 * @summary Test ISO10126Padding.unpad method for correct padding index and error conditions
 * @run main ISO10126PaddingWitupTest
 */

import com.sun.crypto.provider.ISO10126Padding;
import javax.crypto.ShortBufferException;

public class ISO10126PaddingWitupTest {

    public static void main(String[] args) throws Exception {
        ISO10126Padding padding = new ISO10126Padding(8);

        // Test unpad with null input returns 0
        if (padding.unpad(null, 0, 0) != 0) {
            throw new RuntimeException("unpad(null,0,0) should return 0");
        }

        // Test unpad with len == 0 returns 0
        byte[] data = new byte[16];
        if (padding.unpad(data, 0, 0) != 0) {
            throw new RuntimeException("unpad with len=0 should return 0");
        }

        // Prepare a buffer with valid padding: last byte = padding length
        // Fill with arbitrary data
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        int padLen = 8;
        int off = 8;
        int len = 8;
        // Set last byte to padLen
        data[off + len - 1] = (byte) padLen;

        // unpad should return start index of padding
        int start = padding.unpad(data, off, len);
        if (start != off + len - padLen) {
            throw new RuntimeException("unpad did not return correct padding start index");
        }

        // Test unpad with invalid padding value (0) returns -1
        data[off + len - 1] = 0;
        if (padding.unpad(data, off, len) != -1) {
            throw new RuntimeException("unpad with padding value 0 should return -1");
        }

        // Test unpad with invalid padding value greater than blockSize returns -1
        data[off + len - 1] = (byte) (padding.blockSize + 1);
        if (padding.unpad(data, off, len) != -1) {
            throw new RuntimeException("unpad with padding value > blockSize should return -1");
        }

        // Test unpad with start < off returns -1
        data[off + len - 1] = (byte) (len + 1); // padValue > len, so start < off
        if (padding.unpad(data, off, len) != -1) {
            throw new RuntimeException("unpad with start < off should return -1");
        }

        // Test unpad with off + len causing overflow triggers ArithmeticException
        boolean caught = false;
        try {
            padding.unpad(data, Integer.MAX_VALUE, 10);
        } catch (ArithmeticException e) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("unpad should throw ArithmeticException on integer overflow");
        }
    }
}
