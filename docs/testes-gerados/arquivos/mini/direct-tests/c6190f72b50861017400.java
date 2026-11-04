/*
 * @test
 * @summary Test UnpackerImpl.run method for segment unpacking and loop termination
 * @run main UnpackerImplWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.jar.JarEntry;
import com.sun.java.util.jar.pack.UnpackerImpl;

public class UnpackerImplWitupTest {

    public static void main(String[] args) throws Exception {
        UnpackerImpl unpacker = new UnpackerImpl();

        // Prepare a dummy packed stream that does NOT start with pack magic to test loop exit
        byte[] noPackMagic = new byte[] {0x00, 0x01, 0x02, 0x03};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(noPackMagic));

        // Prepare a JarOutputStream writing to a ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JarOutputStream out = new JarOutputStream(baos);

        // Run unpacker.run and expect it to exit loop after first segment (no pack magic)
        unpacker.run(in, out);

        // Close output stream
        out.close();

        // If no exception thrown, test passes
        System.out.println("Test passed: run method completed without exceptions.");
    }
}
