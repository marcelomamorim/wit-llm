/*
 * @test
 * @summary Tests for java.util.jar.JarOutputStream constructors and methods
 * @run main JarOutputStreamWitupTest
 */

import java.io.*;
import java.util.jar.*;
import java.util.zip.*;

public class JarOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithNullManifest();
        testConstructorWithValidManifest();
        testPutNextEntryWithNullManifest();
    }

    private static void testConstructorWithNullManifest() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new JarOutputStream(baos, null);
            fail("Expected NullPointerException for null manifest");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            fail("Unexpected IOException: " + e);
        }
    }

    private static void testConstructorWithValidManifest() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Manifest manifest = new Manifest();
        try {
            JarOutputStream jos = new JarOutputStream(baos, manifest);
            jos.close();
        } catch (IOException e) {
            fail("IOException during valid constructor: " + e);
        }
    }

    private static void testPutNextEntryWithNullManifest() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JarOutputStream jos = new JarOutputStream(baos);
            ZipEntry entry = new ZipEntry("test.txt");
            jos.putNextEntry(entry);
            jos.closeEntry();
            jos.close();
        } catch (IOException e) {
            fail("IOException during putNextEntry: " + e);
        }
    }

    private static void fail(String message) {
        throw new AssertionError(message);
    }
}
