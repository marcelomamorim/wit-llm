/*
 * @test
 * @summary Test putSequence method of DerOutputStream for normal and edge cases
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPutSequenceWithEmptyArray();
        testPutSequenceWithSingleElement();
        testPutSequenceWithMultipleElements();
    }

    private static void testPutSequenceWithEmptyArray() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] emptySeq = new DerValue[0];
        dos.putSequence(emptySeq);
        byte[] result = dos.toByteArray();
        // The result should start with the SEQUENCE tag (0x30) and length 0
        if (result.length != 2 || result[0] != DerValue.tag_Sequence || result[1] != 0) {
            throw new RuntimeException("putSequence failed for empty array: " + toHex(result));
        }
    }

    private static void testPutSequenceWithSingleElement() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] seq = new DerValue[1];
        seq[0] = new DerValue(DerValue.tag_Integer, new byte[] {0x01});
        dos.putSequence(seq);
        byte[] result = dos.toByteArray();
        // The result should start with SEQUENCE tag
        if (result.length < 3 || result[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("putSequence failed for single element: " + toHex(result));
        }
    }

    private static void testPutSequenceWithMultipleElements() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] seq = new DerValue[2];
        seq[0] = new DerValue(DerValue.tag_Integer, new byte[] {0x01});
        seq[1] = new DerValue(DerValue.tag_Boolean, new byte[] {0xFF});
        dos.putSequence(seq);
        byte[] result = dos.toByteArray();
        if (result.length < 5 || result[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("putSequence failed for multiple elements: " + toHex(result));
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
