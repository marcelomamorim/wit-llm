/*
 * @test
 * @summary Tests the public ClassReader(InputStream) constructor for null and valid class-file streams
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main ClassReaderWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jdk.internal.org.objectweb.asm.ClassReader;

public class ClassReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullStreamThrowsIOException();
        testValidClassStreamIsAccepted();
    }

    static void testNullStreamThrowsIOException() {
        boolean thrown = false;
        try {
            new ClassReader((InputStream) null);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException for null stream");
        }
    }

    static void testValidClassStreamIsAccepted() throws IOException {
        InputStream in = ClassReaderWitupTest.class.getResourceAsStream("ClassReaderWitupTest.class");
        if (in == null) {
            throw new RuntimeException("class resource not found");
        }
        byte[] bytes;
        try {
            bytes = in.readAllBytes();
        } finally {
            in.close();
        }
        if (bytes.length == 0) {
            throw new RuntimeException("empty class resource");
        }
        ClassReader reader = new ClassReader(new ByteArrayInputStream(bytes));
        if (reader == null) {
            throw new RuntimeException("constructor returned null");
        }
    }
}
