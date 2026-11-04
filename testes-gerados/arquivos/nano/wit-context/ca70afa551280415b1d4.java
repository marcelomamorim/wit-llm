/* @test    @summary Test DerInputStream.getSequence for normal and exceptional behavior    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testGetSequenceNormal();
        testGetSequenceInvalidTag();
        testGetSequenceEOF();
        testGetSequenceIndexOutOfBounds();
    }

    static void testGetSequenceNormal() throws Exception {
        byte[] data = {0x30, 0x03, 0x02, 0x01, 0x05}; // DER encoding for sequence of integer 5
        DerInputStream dis = new DerInputStream(data);
        DerValue[] values = dis.getSequence(0);
        if (values == null || values.length != 1) {
            throw new AssertionError("Expected one DerValue");
        }
        if (values[0].tag != DerValue.tag_Sequence) {
            throw new AssertionError("Tag mismatch");
        }
    }

    static void testGetSequenceInvalidTag() {
        byte[] data = {0x01, 0x01, 0x00}; // Not a sequence tag
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getSequence(0);
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected
        }
    }

    static void testGetSequenceEOF() {
        byte[] data = {}; // Empty data
        try {
            DerInputStream dis = new DerInputStream(data);
            dis.getSequence(0);
            throw new AssertionError("Expected IOException for EOF");
        } catch (IOException e) {
            // Expected
        }
    }

    static void testGetSequenceIndexOutOfBounds() {
        byte[] data = {0x30, 0x00}; // Empty sequence
        try {
            DerInputStream dis = new DerInputStream(data);
            DerValue[] values = dis.getSequence(0);
            if (values.length != 0) {
                throw new AssertionError("Expected empty DerValue array");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
