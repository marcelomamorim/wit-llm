/*
 * @test
 * @summary Tests for sun.security.util.DerOutputStream.putSequence(DerValue[])
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutSequenceNormal();
        testPutSequenceEmpty();
        testPutSequenceNull();
        testPutSequenceLarge();
    }

    static void testPutSequenceNormal() throws IOException {
        DerValue dv1 = new DerValue(DerValue.tag_Integer, new byte[] {0x01});
        DerValue dv2 = new DerValue(DerValue.tag_Integer, new byte[] {0x02});
        DerValue[] seq = {dv1, dv2};
        DerOutputStream dos = new DerOutputStream();
        dos.putSequence(seq);
        byte[] result = dos.toByteArray();
        // Basic check: result should start with sequence tag and contain encoded values
        if (result.length == 0 || result[0] != DerValue.tag_Sequence) {
            throw new AssertionError("Failed: Sequence encoding does not start with sequence tag");
        }
    }

    static void testPutSequenceEmpty() throws IOException {
        DerValue[] seq = {};
        DerOutputStream dos = new DerOutputStream();
        dos.putSequence(seq);
        byte[] result = dos.toByteArray();
        if (result.length == 0 || result[0] != DerValue.tag_Sequence) {
            throw new AssertionError("Failed: Empty sequence encoding does not start with sequence tag");
        }
    }

    static void testPutSequenceNull() {
        try {
            DerOutputStream dos = new DerOutputStream();
            dos.putSequence(null);
            throw new AssertionError("Expected NullPointerException for null sequence");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    static void testPutSequenceLarge() throws IOException {
        int size = 10000;
        DerValue[] seq = new DerValue[size];
        for (int i = 0; i < size; i++) {
            seq[i] = new DerValue(DerValue.tag_Integer, new byte[] {0x01});
        }
        DerOutputStream dos = new DerOutputStream();
        dos.putSequence(seq);
        byte[] result = dos.toByteArray();
        if (result.length == 0 || result[0] != DerValue.tag_Sequence) {
            throw new AssertionError("Failed: Large sequence encoding does not start with sequence tag");
        }
    }
}
