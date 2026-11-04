/*
 * @test
 * @summary Tests for java.util.zip.DeflaterInputStream constructor behavior and exception paths
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorNullInput();
        testConstructorNullDeflater();
        testConstructorInvalidBufferSize();
    }

    private static void testConstructorNullInput() {
        try {
            new DeflaterInputStream(null, new Deflater(), 512);
            System.err.println("Failed: Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null InputStream: " + e);
        }
    }

    private static void testConstructorNullDeflater() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        try {
            new DeflaterInputStream(bais, null, 512);
            System.err.println("Failed: Expected NullPointerException for null Deflater");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null Deflater: " + e);
        }
    }

    private static void testConstructorInvalidBufferSize() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        try {
            new DeflaterInputStream(bais, new Deflater(), 0);
            System.err.println("Failed: Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for buffer size 0: " + e);
        }
        try {
            new DeflaterInputStream(bais, new Deflater(), -10);
            System.err.println("Failed: Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for negative buffer size: " + e);
        }
    }
}
