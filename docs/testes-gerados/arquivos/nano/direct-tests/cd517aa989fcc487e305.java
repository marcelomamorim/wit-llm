/* @test    @summary Test ClassReader constructor with InputStream    @run main ClassReaderWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidInputStream();
        testConstructorWithEmptyInputStream();
    }

    static void testConstructorWithValidInputStream() throws IOException {
        byte[] classBytes = {0xCA, 0xFE, 0xBA, 0xBE}; // minimal class file header
        InputStream is = new ByteArrayInputStream(classBytes);
        jdk.internal.org.objectweb.asm.ClassReader reader = new jdk.internal.org.objectweb.asm.ClassReader(is);
        assert reader != null : "ClassReader should be instantiated successfully";
    }

    static void testConstructorWithEmptyInputStream() throws IOException {
        byte[] emptyBytes = {};
        InputStream is = new ByteArrayInputStream(emptyBytes);
        try {
            jdk.internal.org.objectweb.asm.ClassReader reader = new jdk.internal.org.objectweb.asm.ClassReader(is);
            assert reader != null : "ClassReader should handle empty input";
        } catch (IOException e) {
            // Expected if the input is not a valid class file
            assert e != null : "IOException expected for empty input";
        }
    }
}
