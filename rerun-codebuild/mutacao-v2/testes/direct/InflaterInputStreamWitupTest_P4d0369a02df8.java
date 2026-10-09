/*
 * @test
 * @summary Test InflaterInputStream.reset() throws IOException as expected
 * @run main InflaterInputStreamWitupTest_P4d0369a02df8
 */

import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class InflaterInputStreamWitupTest_P4d0369a02df8 {
    public static void main(String[] args) throws Exception {
        byte[] dummyData = new byte[] {0x78, 0x9c, 0x03, 0x00, 0x00, 0x00, 0x00, 0x01};
        ByteArrayInputStream bais = new ByteArrayInputStream(dummyData);
        InflaterInputStream iis = new InflaterInputStream(bais, new Inflater());
        try {
            iis.reset();
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
