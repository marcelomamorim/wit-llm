/* @test    @summary Test for ClassReader constructor with InputStream, especially null input handling    @run main ClassReaderWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassReaderWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInputStream();
        testConstructorWithValidInputStream();
    }

    private static void testConstructorWithNullInputStream() {
        try {
            new jdk.internal.org.objectweb.asm.ClassReader((InputStream) null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null InputStream");
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }

    private static void testConstructorWithValidInputStream() {
        byte[] dummyBytes = {0x00, 0x01, 0x02};
        InputStream is = new ByteArrayInputStream(dummyBytes);
        try {
            jdk.internal.org.objectweb.asm.ClassReader cr = new jdk.internal.org.objectweb.asm.ClassReader(is);
            if (cr != null) {
                System.out.println("ClassReader instantiated successfully with valid InputStream");
            } else {
                System.err.println("ClassReader instance is null");
            }
        } catch (IOException e) {
            System.err.println("IOException during instantiation: " + e);
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
