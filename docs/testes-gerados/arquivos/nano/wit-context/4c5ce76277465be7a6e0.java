/*
 * @test
 * @summary Tests for DeflaterInputStream constructor with InputStream and Deflater
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithNullInput();
        testConstructorWithNullDeflater();
    }

    private static void testConstructorWithNullInput() {
        try {
            new DeflaterInputStream(null, new Deflater());
            System.err.println("Failed: Expected NullPointerException for null InputStream");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithNullDeflater() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        try {
            new DeflaterInputStream(bais, null);
            System.err.println("Failed: Expected NullPointerException for null Deflater");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
