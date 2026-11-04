/*
 * @test
 * @summary WITUP-derived checks for UnpackerImpl.unpack(File, JarOutputStream) argument validation
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main UnpackerImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import com.sun.java.util.jar.pack.UnpackerImpl;

public class UnpackerImplWitupTest {

    public static void main(String[] args) throws Exception {
        UnpackerImpl unpacker = new UnpackerImpl();

        JarOutputStream out = new JarOutputStream(new ByteArrayOutputStream());
        try {
            unpacker.unpack((File) null, out);
            throw new AssertionError("expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }

        File existing = new File("witup-missing-input-" + System.nanoTime() + ".pack");
        JarOutputStream out2 = new JarOutputStream(new ByteArrayOutputStream());
        try {
            unpacker.unpack(new File("witup-null-output-" + System.nanoTime() + ".pack"), null);
            throw new AssertionError("expected NullPointerException for null output");
        } catch (NullPointerException expected) {
        }

        try {
            unpacker.unpack(existing, out2);
            throw new AssertionError("expected IOException for missing input file");
        } catch (IOException expected) {
        }
    }
}
