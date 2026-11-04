/*
 * @test
 * @summary Test Manifest.readLine(byte[]) method for boundary and error conditions
 * @run main ManifestWitupTest
 */

import java.util.jar.Manifest;
import java.io.IOException;

public class ManifestWitupTest {
    public static void main(String[] args) throws Exception {
        Manifest manifest = new Manifest();
        // The readLine method is package-private in Manifest.FastInputStream, not accessible here.
        // We test the public Manifest.readLine(byte[]) method indirectly by creating a subclass to expose it.
        TestFastInputStream fis = new TestFastInputStream(new byte[0]);

        // Test 1: Passing empty byte array should return -1 (EOF) or 0 depending on implementation
        byte[] emptyBuffer = new byte[0];
        try {
            int result = fis.readLine(emptyBuffer);
            if (result != -1 && result != 0) {
                throw new RuntimeException("Unexpected result for empty buffer: " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException unexpected for empty buffer", e);
        }

        // Test 2: Passing null buffer should throw NullPointerException
        try {
            fis.readLine(null);
            throw new RuntimeException("Expected NullPointerException for null buffer");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Expected NullPointerException, got IOException", e);
        }

        // Test 3: Passing buffer with length > 0 but internal state simulating count - pos <= 0 triggers outOfBounds
        // We simulate this by creating a subclass with overridden fields
        TestFastInputStream fis2 = new TestFastInputStream(new byte[10]);
        fis2.count = 0;
        fis2.pos = 0;
        try {
            fis2.readLine(new byte[5]);
            throw new RuntimeException("Expected IndexOutOfBoundsException due to invalid internal state");
        } catch (IndexOutOfBoundsException iobe) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Expected IndexOutOfBoundsException, got IOException", e);
        }

        System.out.println("ManifestWitupTest passed");
    }

    // Subclass to expose the package-private FastInputStream and its readLine method
    static class TestFastInputStream extends Manifest.FastInputStream {
        TestFastInputStream(byte[] data) {
            super(new java.io.ByteArrayInputStream(data));
        }

        @Override
        public int readLine(byte[] b) throws IOException {
            return super.readLine(b);
        }

        // Expose fields to simulate internal state for test
        int count = super.count;
        int pos = super.pos;

        // Override fill to do nothing to keep count and pos stable
        @Override
        protected void fill() throws IOException {
            // do nothing
        }
    }
}
