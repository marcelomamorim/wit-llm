/*
 * @test
 * @summary Test Scanner constructor with ReadableByteChannel and Charset
 * @run main ScannerWitupTest
 */

import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidChannelAndCharset();
        testConstructorWithNullChannel();
        testConstructorWithNullCharset();
    }

    static void testConstructorWithValidChannelAndCharset() {
        try {
            String data = "test data";
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ReadableByteChannel channel = Channels.newChannel(bais);
            Charset charset = StandardCharsets.UTF_8;
            Scanner scanner = new Scanner(channel, charset);
            // Since the constructor does not read data, just verify no exception thrown
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed valid constructor test", e);
        }
    }

    static void testConstructorWithNullChannel() {
        try {
            Charset charset = StandardCharsets.UTF_8;
            Scanner scanner = new Scanner((ReadableByteChannel) null, charset);
            throw new AssertionError("Expected NullPointerException for null channel");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullCharset() {
        try {
            String data = "test data";
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ReadableByteChannel channel = Channels.newChannel(bais);
            Scanner scanner = new Scanner(channel, null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
