/* @test    @summary Test DerOutputStream.putSet method for correct DER encoding behavior    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutSetWithSingleElement();
        testPutSetWithMultipleElements();
        testPutSetWithEmptyArray();
    }

    static void testPutSetWithSingleElement() throws IOException {
        DerValue value = new DerValue(DerValue.tag_Integer, new byte[] {0x01});
        DerValue[] set = new DerValue[] { value };
        DerOutputStream dos = new DerOutputStream();
        dos.putSet(set);
        byte[] result = dos.toByteArray();
        // Expected: 0x31 (SET tag), length, and encoded value
        assert result[0] == (byte)0x31 : "Expected SET tag 0x31";
        int length = result[1] & 0xFF;
        assert length == 3 : "Expected length 3";
        assert result[2] == (byte)0x02 : "Expected INTEGER tag 0x02";
        assert result[3] == (byte)0x01 : "Expected value 0x01";
    }

    static void testPutSetWithMultipleElements() throws IOException {
        DerValue val1 = new DerValue(DerValue.tag_Integer, new byte[] {0x01});
        DerValue val2 = new DerValue(DerValue.tag_Integer, new byte[] {0x02});
        DerValue[] set = new DerValue[] { val1, val2 };
        DerOutputStream dos = new DerOutputStream();
        dos.putSet(set);
        byte[] result = dos.toByteArray();
        // Check that the result starts with SET tag
        assert result[0] == (byte)0x31 : "Expected SET tag 0x31";
        int length = result[1] & 0xFF;
        // Length should be 5 (1 byte for tag, 1 for length, 1 for first int, 1 for second int)
        assert length == 5 : "Expected length 5";
        // Check first element
        assert result[2] == (byte)0x02 : "Expected INTEGER tag 0x02";
        assert result[3] == (byte)0x01 : "Expected first value 0x01";
        // Check second element
        assert result[4] == (byte)0x02 : "Expected second value 0x02";
    }

    static void testPutSetWithEmptyArray() throws IOException {
        DerValue[] set = new DerValue[0];
        DerOutputStream dos = new DerOutputStream();
        dos.putSet(set);
        byte[] result = dos.toByteArray();
        // Expect only the SET tag and length 0
        assert result[0] == (byte)0x31 : "Expected SET tag 0x31";
        int length = result[1] & 0xFF;
        assert length == 0 : "Expected length 0";
        assert result.length == 2 : "Expected total length 2";
    }
}
