/* @test    @summary Test DerOutputStream.putTag for various inputs and behaviors    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutTagBasic();
        testPutTagWithFormTrue();
        testPutTagWithFormFalse();
        testPutTagMemoryErrorSimulation();
    }

    static void testPutTagBasic() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        byte tagClass = 0x40; // context-specific class
        boolean form = false;
        byte val = 0x02; // INTEGER tag
        dos.putTag(tagClass, form, val);
        byte[] result = dos.toByteArray();
        // Expected: tag = (byte)(tagClass | val) = 0x42
        assert result.length == 1 : "Result length should be 1";
        assert result[0] == (byte)(tagClass | val) : "Tag byte mismatch";
    }

    static void testPutTagWithFormTrue() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        byte tagClass = 0x80; // context-specific class
        boolean form = true;
        byte val = 0x01; // BOOLEAN tag
        dos.putTag(tagClass, form, val);
        byte[] result = dos.toByteArray();
        // Expected: tag = (byte)(tagClass | val) | 0x20
        byte expectedTag = (byte)(tagClass | val | 0x20);
        assert result.length == 1 : "Result length should be 1";
        assert result[0] == expectedTag : "Tag byte with form=true mismatch";
    }

    static void testPutTagWithFormFalse() throws IOException {
        DerOutputStream dos = new DerOutputStream();
        byte tagClass = 0x00; // universal class
        boolean form = false;
        byte val = 0x10; // some tag
        dos.putTag(tagClass, form, val);
        byte[] result = dos.toByteArray();
        // Expected: tag = (byte)(tagClass | val)
        assert result.length == 1 : "Result length should be 1";
        assert result[0] == (byte)(tagClass | val) : "Tag byte mismatch";
    }

    static void testPutTagMemoryErrorSimulation() {
        // Simulate OutOfMemoryError by overriding write method
        DerOutputStream dos = new DerOutputStream() {
            @Override
            public void write(int b) {
                throw new OutOfMemoryError("Simulated OOM") ;
            }
        };
        try {
            dos.putTag((byte)0x00, false, (byte)0x00);
            assert false : "Expected OutOfMemoryError was not thrown";
        } catch (OutOfMemoryError e) {
            assert e.getMessage().equals("Simulated OOM") : "Unexpected OutOfMemoryError message";
        }
    }
}
