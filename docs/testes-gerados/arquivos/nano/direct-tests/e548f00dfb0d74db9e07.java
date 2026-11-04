/* @test    @summary Test the available() method of DeflaterInputStream for correct behavior.    @run main DeflaterInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testAvailableReturnsOneWhenNotEOF();
        testAvailableReturnsZeroWhenEOF();
    }

    private static void testAvailableReturnsOneWhenNotEOF() throws IOException {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Deflater deflater = new Deflater();
        try (DeflaterInputStream dis = new DeflaterInputStream(bais, deflater)) {
            int available = dis.available();
            if (available != 1) {
                throw new AssertionError("Expected available() to return 1, but got " + available);
            }
        }
    }

    private static void testAvailableReturnsZeroWhenEOF() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Deflater deflater = new Deflater();
        try (DeflaterInputStream dis = new DeflaterInputStream(bais, deflater)) {
            // Simulate reaching EOF by reading until end
            while (dis.read() != -1) {
                // read until EOF
            }
            int available = dis.available();
            if (available != 0) {
                throw new AssertionError("Expected available() to return 0 after EOF, but got " + available);
            }
        }
    }
}
