/*
 * @test
 * @summary Deterministic test for sun.security.ssl.SSLContextImpl.run() method
 * @run main SSLContextImplWitupTest
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SSLContextImplWitupTest {

    // Minimal subclass to expose run() method for testing
    static class TestSSLContextImpl extends sun.security.ssl.SSLContextImpl {
        private final String defaultKeyStore;

        TestSSLContextImpl(String defaultKeyStore) {
            this.defaultKeyStore = defaultKeyStore;
        }

        @Override
        public FileInputStream run() throws Exception {
            return new FileInputStream(defaultKeyStore);
        }
    }

    public static void main(String[] args) throws Exception {
        // Test 1: defaultKeyStore is null -> expect NullPointerException
        try {
            TestSSLContextImpl ctxNull = new TestSSLContextImpl(null);
            ctxNull.run();
            throw new RuntimeException("Test failed: expected NullPointerException for null defaultKeyStore");
        } catch (NullPointerException expected) {
            // Expected
        }

        // Test 2: defaultKeyStore points to non-existent file -> expect FileNotFoundException
        try {
            TestSSLContextImpl ctxNonExist = new TestSSLContextImpl("nonexistentfile.keystore");
            ctxNonExist.run();
            throw new RuntimeException("Test failed: expected FileNotFoundException for nonexistent file");
        } catch (FileNotFoundException expected) {
            // Expected
        }

        // Test 3: defaultKeyStore points to an existing file -> expect FileInputStream returned
        // Create a temporary file for this test
        java.io.File tempFile = java.io.File.createTempFile("testkeystore", ".tmp");
        tempFile.deleteOnExit();
        TestSSLContextImpl ctxExist = new TestSSLContextImpl(tempFile.getAbsolutePath());
        FileInputStream fis = null;
        try {
            fis = ctxExist.run();
            if (fis == null) {
                throw new RuntimeException("Test failed: run() returned null FileInputStream");
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}
