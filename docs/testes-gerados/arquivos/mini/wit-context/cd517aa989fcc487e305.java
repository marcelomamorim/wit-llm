/*
 * @test
 * @summary Test ClassReader(InputStream) constructor for IOException on null InputStream
 * @run main ClassReaderWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import jdk.internal.org.objectweb.asm.ClassReader;

public class ClassReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithNullInputStream();
    }

    private static void testConstructorWithNullInputStream() {
        InputStream is = null;
        try {
            new ClassReader(is);
            throw new AssertionError("Expected IOException not thrown for null InputStream");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
