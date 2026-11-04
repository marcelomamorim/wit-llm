/*
 * @test
 * @summary Test the constructor of DeflaterInputStream with a null InputStream.
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInputStream();
    }

    public static void testConstructorWithNullInputStream() {
        boolean exceptionThrown = false;
        try {
            new DeflaterInputStream(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException was not thrown");
        }
    }
}
