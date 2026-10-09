/* @test    @summary Test getUTCTime method of DerInputBuffer for valid and invalid inputs    @run main DerInputBufferWitupTest_P2acb1c0fd43f */

import sun.security.util.DerInputBuffer;
import java.io.IOException;
import java.util.Date;

public class DerInputBufferWitupTest_P2acb1c0fd43f {

    public static void main(String[] args) {
        testValidUTCTime();
        testShortRead();
        testInvalidLengthTooSmall();
        testInvalidLengthTooLarge();
    }

    static void testValidUTCTime() {
        byte[] data = new byte[] {
            0x17, 0x0D, 0x32, 0x30, 0x30, 0x39, 0x30, 0x31, 0x30, 0x31, 0x30, 0x30, 0x5A
        };
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            Date date = buffer.getUTCTime(data.length);
            if (date == null) {
                throw new AssertionError("Expected non-null Date");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    static void testShortRead() {
        byte[] data = new byte[] {
            0x17, 0x0D, 0x32, 0x30, 0x30, 0x39, 0x30, 0x31, 0x30, 0x31, 0x30, 0x30, 0x5A
        };
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            buffer.getUTCTime(data.length + 1);
            throw new AssertionError("Expected IOException for short read");
        } catch (IOException e) {
            if (!e.getMessage().contains("short read")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    static void testInvalidLengthTooSmall() {
        byte[] data = new byte[] {
            0x17, 0x0A, 0x32, 0x30, 0x30, 0x39, 0x30, 0x31, 0x30, 0x31, 0x30
        };
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            buffer.getUTCTime(data.length);
            throw new AssertionError("Expected IOException for length < 11");
        } catch (IOException e) {
            if (!e.getMessage().contains("length error")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    static void testInvalidLengthTooLarge() {
        byte[] data = new byte[] {
            0x17, 0x12, 0x32, 0x30, 0x30, 0x39, 0x30, 0x31, 0x30, 0x31, 0x30, 0x30, 0x5A, 0x00, 0x00, 0x00, 0x00
        };
        DerInputBuffer buffer = new DerInputBuffer(data);
        try {
            buffer.getUTCTime(data.length);
            throw new AssertionError("Expected IOException for length > 17");
        } catch (IOException e) {
            if (!e.getMessage().contains("length error")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
