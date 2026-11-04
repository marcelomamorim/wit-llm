/* @test    @summary Test unpad method of ISO10126Padding for various scenarios    @run main ISO10126PaddingWitupTest */

import com.sun.crypto.provider.ISO10126Padding;

public class ISO10126PaddingWitupTest {

    public static void main(String[] args) {
        testUnpadValidPadding();
        testUnpadNullInput();
        testUnpadZeroLength();
        testUnpadInvalidPaddingValue();
        testUnpadPaddingStartBeforeOffset();
    }

    static void testUnpadValidPadding() {
        byte[] buffer = new byte[10];
        int offset = 2;
        int length = 5;
        // Simulate padding: last byte indicates padding length
        buffer[offset + length - 1] = 3; // padding length
        buffer[offset + length - 2] = 0x00;
        buffer[offset + length - 3] = 0x00;
        int result = new ISO10126Padding(8).unpad(buffer, offset, length);
        assert result == offset + length - 3 : "Valid padding should return correct start index";
    }

    static void testUnpadNullInput() {
        int result = new ISO10126Padding(8).unpad(null, 0, 5);
        assert result == 0 : "Null input should return 0";
    }

    static void testUnpadZeroLength() {
        byte[] buffer = new byte[10];
        int result = new ISO10126Padding(8).unpad(buffer, 0, 0);
        assert result == 0 : "Zero length should return 0";
    }

    static void testUnpadInvalidPaddingValue() {
        byte[] buffer = new byte[10];
        int offset = 1;
        int length = 4;
        buffer[offset + length - 1] = 0; // invalid padding
        int result = new ISO10126Padding(8).unpad(buffer, offset, length);
        assert result == -1 : "Invalid padding value should return -1";
    }

    static void testUnpadPaddingStartBeforeOffset() {
        byte[] buffer = new byte[10];
        int offset = 5;
        int length = 3;
        buffer[offset + length - 1] = 2; // padding length
        int result = new ISO10126Padding(8).unpad(buffer, offset, length);
        assert result == -1 : "Padding start before offset should return -1";
    }
}
