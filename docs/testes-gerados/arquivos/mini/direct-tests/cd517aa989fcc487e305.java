/*
 * @test
 * @summary Test ClassReader constructor with InputStream
 * @run main ClassReaderWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import jdk.internal.org.objectweb.asm.ClassReader;

public class ClassReaderWitupTest {
    public static void main(String[] args) throws IOException {
        // Minimal valid class file header (magic + minor + major + constant pool count)
        byte[] minimalClass = new byte[] {
            (byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE, // magic
            0, 0, // minor version
            0, 52, // major version (Java 8)
            0, 1  // constant pool count (1 means no entries)
        };

        ByteArrayInputStream bais = new ByteArrayInputStream(minimalClass);
        ClassReader cr = new ClassReader(bais);

        // Check that ClassReader instance is created
        if (cr == null) {
            throw new AssertionError("ClassReader instance is null");
        }

        // Test with empty InputStream to provoke IOException
        try {
            new ClassReader(new ByteArrayInputStream(new byte[0]));
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
