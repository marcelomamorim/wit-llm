/* @test    @summary Test DerOutputStream.putTag method for various inputs    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutTagWithAllInputs();
        testPutTagWithEdgeCases();
    }

    private static void testPutTagWithAllInputs() {
        DerOutputStream dos = new DerOutputStream();
        byte tagClass = 0x40; // context-specific class
        boolean form = true;
        byte val = 0x02;
        dos.putTag(tagClass, form, val);
        byte[] result = dos.toByteArray();
        byte expectedTag = (byte)(tagClass | val | 0x20);
        assert result.length == 1 : "Result length should be 1";
        assert result[0] == expectedTag : "Tag byte mismatch";
    }

    private static void testPutTagWithEdgeCases() {
        DerOutputStream dos = new DerOutputStream();
        byte tagClass = (byte)0xFF; // maximum byte value
        boolean form = false;
        byte val = (byte)0x80; // high bit set
        dos.putTag(tagClass, form, val);
        byte[] result = dos.toByteArray();
        byte expectedTag = (byte)(tagClass | val);
        assert result.length == 1 : "Result length should be 1";
        assert result[0] == expectedTag : "Tag byte mismatch in edge case";
    }
}
