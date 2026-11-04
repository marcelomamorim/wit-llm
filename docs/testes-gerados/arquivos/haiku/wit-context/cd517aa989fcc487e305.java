/*
 * @test
 * @summary Verifies that ClassReader(InputStream) rejects a null stream with IOException
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ClassReaderWitupTest
 */

import java.io.IOException;
import jdk.internal.org.objectweb.asm.ClassReader;

public class ClassReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputStreamThrowsIOException();
    }

    static void testNullInputStreamThrowsIOException() {
        boolean thrown = false;
        try {
            new ClassReader((java.io.InputStream) null);
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for null InputStream");
        }
    }
}
