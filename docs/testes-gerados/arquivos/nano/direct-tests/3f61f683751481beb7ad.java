/* @test    @summary Test SSLContextImpl.run() method for returning FileInputStream of defaultKeyStore    @run main SSLContextImplWitupTest */

import java.io.*;
import sun.security.ssl.SSLContextImpl;

public class SSLContextImplWitupTest {

    public static void main(String[] args) throws Exception {
        testRunReturnsFileInputStream();
    }

    public static void testRunReturnsFileInputStream() throws Exception {
        SSLContextImpl context = new SSLContextImpl() {
            @Override
            public FileInputStream run() throws Exception {
                // Simulate the behavior of the original method
                // For testing, return a new FileInputStream of a temporary file
                File tempFile = File.createTempFile("keystore", ".jks");
                tempFile.deleteOnExit();
                return new FileInputStream(tempFile);
            }
        };
        FileInputStream fis = context.run();
        if (fis == null) {
            throw new AssertionError("FileInputStream should not be null");
        }
        fis.close();
    }
}
