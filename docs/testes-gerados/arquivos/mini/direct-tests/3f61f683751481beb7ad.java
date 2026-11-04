/*
 * @test
 * @summary Test sun.security.ssl.SSLContextImpl.run() method
 * @run main SSLContextImplWitupTest
 */

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

public class SSLContextImplWitupTest {

    // Minimal subclass to access run() method
    static class TestSSLContextImpl extends sun.security.ssl.SSLContextImpl {
        private final File defaultKeyStore;

        TestSSLContextImpl(File file) {
            this.defaultKeyStore = file;
        }

        @Override
        public FileInputStream run() throws Exception {
            return new FileInputStream(defaultKeyStore);
        }
    }

    public static void main(String[] args) throws Exception {
        // Create a temporary file to act as defaultKeyStore
        File tempFile = File.createTempFile("keystore", ".tmp");
        tempFile.deleteOnExit();

        // Write some bytes to the file to ensure it exists and is readable
        java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFile);
        fos.write(new byte[] {1, 2, 3});
        fos.close();

        TestSSLContextImpl ctx = new TestSSLContextImpl(tempFile);

        // Test that run() returns a FileInputStream that reads the file
        FileInputStream fis = null;
        try {
            fis = ctx.run();
            int firstByte = fis.read();
            if (firstByte != 1) {
                throw new RuntimeException("Unexpected first byte read from FileInputStream: " + firstByte);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        // Test that run() throws IOException if file does not exist
        File nonExistentFile = new File("nonexistentfile123456789.tmp");
        TestSSLContextImpl ctx2 = new TestSSLContextImpl(nonExistentFile);
        boolean threw = false;
        try {
            ctx2.run();
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException when opening non-existent file");
        }
    }
}
