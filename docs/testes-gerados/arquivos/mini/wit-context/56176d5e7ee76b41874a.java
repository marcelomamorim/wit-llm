/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getGeneralString() for expected IOException and normal behavior
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidGeneralString();
        testIOExceptionOnEmptyBuffer();
    }

    private static void testValidGeneralString() throws IOException {
        // Construct a valid DER encoding for GeneralString tag with ASCII content "Test"
        // DER encoding: tag (0x1B), length (4), content (ASCII bytes)
        byte[] validGeneralString = new byte[] { DerValue.tag_GeneralString, 4, 'T', 'e', 's', 't' };
        DerInputStream dis = new DerInputStream(validGeneralString);
        String result = dis.getGeneralString();
        if (!"Test".equals(result)) {
            throw new RuntimeException("Expected 'Test' but got '" + result + "'");
        }
    }

    private static void testIOExceptionOnEmptyBuffer() {
        // Provide empty buffer to cause IOException due to no data to read
        byte[] empty = new byte[0];
        try {
            DerInputStream dis = new DerInputStream(empty);
            dis.getGeneralString();
            throw new RuntimeException("Expected IOException not thrown on empty buffer");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
