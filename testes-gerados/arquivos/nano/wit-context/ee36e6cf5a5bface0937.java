/* @test    @summary Test for Scanner(ReadableByteChannel, Charset) constructor handling null charset    @run main ScannerWitupTest */

import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNullCharsetThrowsNullPointerException();
        testValidChannelAndCharset();
    }

    private static void testNullCharsetThrowsNullPointerException() {
        ReadableByteChannel channel = new ByteArrayInputStream(new byte[0]).getChannel();
        try {
            new Scanner(channel, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testValidChannelAndCharset() {
        byte[] data = "test".getBytes();
        ReadableByteChannel channel = new ByteArrayInputStream(data).getChannel();
        Charset charset = StandardCharsets.UTF_8;
        Scanner scanner = null;
        try {
            scanner = new Scanner(channel, charset);
            if (scanner == null) {
                throw new AssertionError("Scanner instance is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
