/*
 * @test
 * @summary Deterministic tests for UnpackerImpl.run(BufferedInputStream, JarOutputStream)
 * @run main UnpackerImplWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import com.sun.java.util.jar.pack.UnpackerImpl;

public class UnpackerImplWitupTest {

    public static void main(String[] args) throws IOException {
        testRunWithEmptyStream();
        testRunWithNonPackMagicStream();
    }

    private static void testRunWithEmptyStream() throws IOException {
        UnpackerImpl unpacker = new UnpackerImpl();
        byte[] emptyData = new byte[0];
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(emptyData));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JarOutputStream out = new JarOutputStream(baos);

        // The run method should return normally without throwing IOException
        // when input stream is empty (no segments to unpack).
        unpacker.run(in, out);

        out.close();
    }

    private static void testRunWithNonPackMagicStream() throws IOException {
        UnpackerImpl unpacker = new UnpackerImpl();
        // Provide input stream with bytes that do not match Pack magic
        byte[] nonPackMagic = new byte[] {0x00, 0x01, 0x02, 0x03};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(nonPackMagic));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JarOutputStream out = new JarOutputStream(baos);

        // The run method should return normally without throwing IOException
        // when the first segment magic is not Pack magic (loop breaks immediately).
        unpacker.run(in, out);

        out.close();
    }
}
