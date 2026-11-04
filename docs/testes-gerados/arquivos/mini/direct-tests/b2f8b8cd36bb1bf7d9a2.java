/*
 * @test
 * @summary Test DeflaterInputStream.reset() throws IOException
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DeflaterInputStream dis = new DeflaterInputStream(bais);
        try {
            dis.reset();
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
