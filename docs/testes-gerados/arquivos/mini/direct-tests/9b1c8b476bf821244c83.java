/*
 * @test
 * @summary Test com.sun.java.util.jar.pack.UnpackerImpl.unpack(File, JarOutputStream) method
 * @run main UnpackerImplWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class UnpackerImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputFile();
        testNullOutputStream();
        testUnpackValidFile();
    }

    private static void testNullInputFile() {
        com.sun.java.util.jar.pack.UnpackerImpl unpacker = new com.sun.java.util.jar.pack.UnpackerImpl();
        JarOutputStream jos = null;
        try {
            jos = new JarOutputStream(new FileOutputStream(File.createTempFile("dummy", ".jar")));
            unpacker.unpack((File) null, jos);
            throw new RuntimeException("Expected NullPointerException for null input file");
        } catch (NullPointerException e) {
            if (!"null input".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        } finally {
            if (jos != null) {
                try { jos.close(); } catch (IOException ignored) {}
            }
        }
    }

    private static void testNullOutputStream() throws IOException {
        com.sun.java.util.jar.pack.UnpackerImpl unpacker = new com.sun.java.util.jar.pack.UnpackerImpl();
        File tempFile = File.createTempFile("dummy", ".pack");
        try {
            unpacker.unpack(tempFile, null);
            throw new RuntimeException("Expected NullPointerException for null output stream");
        } catch (NullPointerException e) {
            if (!"null output".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        } finally {
            tempFile.delete();
        }
    }

    private static void testUnpackValidFile() throws IOException {
        com.sun.java.util.jar.pack.UnpackerImpl unpacker = new com.sun.java.util.jar.pack.UnpackerImpl();

        // Create a minimal dummy pack file (empty file) to test unpack call
        File tempPackFile = File.createTempFile("testpack", ".pack");
        tempPackFile.deleteOnExit();

        File tempJarFile = File.createTempFile("testoutput", ".jar");
        tempJarFile.deleteOnExit();

        JarOutputStream jos = null;
        try {
            jos = new JarOutputStream(new FileOutputStream(tempJarFile));
            unpacker.unpack(tempPackFile, jos);
            // If no exception, test passes
        } finally {
            if (jos != null) {
                try { jos.close(); } catch (IOException ignored) {}
            }
            tempPackFile.delete();
            tempJarFile.delete();
        }
    }

}
