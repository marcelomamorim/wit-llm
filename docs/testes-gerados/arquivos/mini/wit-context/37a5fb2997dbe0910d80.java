/*
 * @test
 * @summary Test sun.security.util.DerValue.toDerInputStream() behavior
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testValidTags();
        testInvalidTagThrows();
    }

    private static void testValidTags() throws IOException {
        // tag_Sequence = 48, tag_Set = 49
        DerValue seq = new DerValue((byte)48, new byte[] {0x05, 0x00});
        DerInputStream disSeq = seq.toDerInputStream();
        if (disSeq == null) {
            throw new RuntimeException("toDerInputStream returned null for tag_Sequence");
        }

        DerValue set = new DerValue((byte)49, new byte[] {0x05, 0x00});
        DerInputStream disSet = set.toDerInputStream();
        if (disSet == null) {
            throw new RuntimeException("toDerInputStream returned null for tag_Set");
        }
    }

    private static void testInvalidTagThrows() {
        // Use a tag different from 48 and 49
        DerValue val = new DerValue((byte)50, new byte[] {0x05, 0x00});
        try {
            val.toDerInputStream();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("toDerInputStream rejects tag type")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
