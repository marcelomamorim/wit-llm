/* @test    @summary Test putSequence method of DerOutputStream for correct encoding of sequences.    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutSequenceWithEmptyArray();
        testPutSequenceWithSingleElement();
        testPutSequenceWithMultipleElements();
    }

    static void testPutSequenceWithEmptyArray() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue[] seq = new DerValue[0];
        dos.putSequence(seq);
        byte[] result = dos.toByteArray();
        // Expect sequence tag (0x30), length 0
        assert result.length == 2 : "Expected length 2 for empty sequence";
        assert result[0] == 0x30 : "Expected sequence tag 0x30";
        assert result[1] == 0x00 : "Expected length byte 0x00";
    }

    static void testPutSequenceWithSingleElement() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue value = new DerValue();
        // encode a simple INTEGER value 5
        byte[] encodedInt = {0x02, 0x01, 0x05};
        value.encode(encodedInt);
        DerValue[] seq = {value};
        dos.putSequence(seq);
        byte[] result = dos.toByteArray();
        // Expect sequence tag (0x30), length 3, followed by encoded integer
        assert result.length == 5 : "Expected length 5 for single element sequence";
        assert result[0] == 0x30 : "Expected sequence tag 0x30";
        assert result[1] == 0x03 : "Expected length byte 0x03";
        assert result[2] == 0x02 : "Expected INTEGER tag 0x02";
        assert result[3] == 0x01 : "Expected length 0x01";
        assert result[4] == 0x05 : "Expected value 0x05";
    }

    static void testPutSequenceWithMultipleElements() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        DerValue val1 = new DerValue();
        byte[] enc1 = {0x02, 0x01, 0x01}; // INTEGER 1
        val1.encode(enc1);
        DerValue val2 = new DerValue();
        byte[] enc2 = {0x02, 0x01, 0x02}; // INTEGER 2
        val2.encode(enc2);
        DerValue[] seq = {val1, val2};
        dos.putSequence(seq);
        byte[] result = dos.toByteArray();
        // Expect sequence tag (0x30), length 6, followed by two encoded integers
        assert result.length == 8 : "Expected length 8 for two element sequence";
        assert result[0] == 0x30 : "Expected sequence tag 0x30";
        assert result[1] == 0x06 : "Expected length byte 0x06";
        // First integer
        assert result[2] == 0x02 : "Expected INTEGER tag 0x02";
        assert result[3] == 0x01 : "Expected length 0x01";
        assert result[4] == 0x01 : "Expected value 0x01";
        // Second integer
        assert result[5] == 0x02 : "Expected INTEGER tag 0x02";
        assert result[6] == 0x01 : "Expected length 0x01";
        assert result[7] == 0x02 : "Expected value 0x02";
    }
}
