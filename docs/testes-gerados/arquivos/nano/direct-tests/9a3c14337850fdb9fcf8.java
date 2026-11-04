/* @test    @summary Test putBoolean method of DerOutputStream for true and false values    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutBooleanTrue();
        testPutBooleanFalse();
    }

    static void testPutBooleanTrue() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putBoolean(true);
        byte[] result = dos.toByteArray();
        // Expected: { tag_Boolean, length=1, value=0xff }
        assert result.length == 3 : "Result length should be 3";
        assert result[0] == DerOutputStream.tag_Boolean : "First byte should be tag_Boolean";
        assert result[1] == 1 : "Second byte (length) should be 1";
        assert result[2] == (byte) 0xff : "Third byte (value) should be 0xff";
    }

    static void testPutBooleanFalse() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        dos.putBoolean(false);
        byte[] result = dos.toByteArray();
        // Expected: { tag_Boolean, length=1, value=0 }
        assert result.length == 3 : "Result length should be 3";
        assert result[0] == DerOutputStream.tag_Boolean : "First byte should be tag_Boolean";
        assert result[1] == 1 : "Second byte (length) should be 1";
        assert result[2] == 0 : "Third byte (value) should be 0";
    }
}
