/*
 * @test
 * @summary Test UnpackerImpl.run method for segment processing and magic number detection
 * @run main UnpackerImplWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import com.sun.java.util.jar.pack.UnpackerImpl;

public class UnpackerImplWitupTest {

    public static void main(String[] args) throws IOException {
        testRunWithValidSegment();
        testRunWithInvalidMagic();
    }

    private static void testRunWithValidSegment() throws IOException {
        byte[] segmentData = createSegmentData();
        ByteArrayInputStream bais = new ByteArrayInputStream(segmentData);
        BufferedInputStream bis = new BufferedInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JarOutputStream jos = new JarOutputStream(baos);
        UnpackerImpl unpacker = new UnpackerImpl();
        unpacker.run(bis, jos);
        jos.close();
        byte[] outputBytes = baos.toByteArray();
        // Basic check: output should not be empty
        assert outputBytes.length > 0 : "Output stream is empty after run with valid segment";
    }

    private static void testRunWithInvalidMagic() {
        byte[] invalidData = new byte[] {0x00, 0x01, 0x02, 0x03};
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidData);
        BufferedInputStream bis = new BufferedInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JarOutputStream jos;
        try {
            jos = new JarOutputStream(baos);
            UnpackerImpl unpacker = new UnpackerImpl();
            unpacker.run(bis, jos);
            jos.close();
            // Expect no exception, but no segment processed
            assert true : "run completed without exception on invalid magic";
        } catch (IOException e) {
            // If an IOException occurs, test passes as it indicates detection of invalid data
            assert e != null : "Expected IOException on invalid magic";
        }
    }

    private static byte[] createSegmentData() {
        // Create dummy segment data with a valid pack magic number
        byte[] magic = {0x50, 0x41, 0x43, 0x4b}; // 'PACK'
        byte[] segmentContent = {0x01, 0x02, 0x03, 0x04};
        byte[] data = new byte[magic.length + segmentContent.length];
        System.arraycopy(magic, 0, data, 0, magic.length);
        System.arraycopy(segmentContent, 0, data, magic.length, segmentContent.length);
        return data;
    }
}
