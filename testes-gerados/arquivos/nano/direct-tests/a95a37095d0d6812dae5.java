/* @test    @summary Test constructor of DeflaterInputStream with valid parameters    @run main DeflaterInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidParameters();
        testConstructorWithNullInputStream();
        testConstructorWithNullDeflater();
        testConstructorWithInvalidBufferSize();
    }

    static void testConstructorWithValidParameters() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Deflater deflater = new Deflater();
        try {
            DeflaterInputStream dis = new DeflaterInputStream(bais, deflater, 1024);
            if (dis == null) {
                throw new AssertionError("Constructor returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testConstructorWithNullInputStream() {
        Deflater deflater = new Deflater();
        try {
            new DeflaterInputStream(null, deflater, 1024);
            throw new AssertionError("Expected NullPointerException for null input stream");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testConstructorWithNullDeflater() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            new DeflaterInputStream(bais, null, 1024);
            throw new AssertionError("Expected NullPointerException for null deflater");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testConstructorWithInvalidBufferSize() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Deflater deflater = new Deflater();
        try {
            new DeflaterInputStream(bais, deflater, 0);
            throw new AssertionError("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
