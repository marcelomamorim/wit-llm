/*
 * @test
 * @summary Verify public Pack200 unpacker rejects null input and null output arguments
 * @run main UnpackerImplWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;

public class UnpackerImplWitupTest {

    public static void main(String[] args) throws Exception {
        Pack200.Unpacker unpacker = Pack200.newUnpacker();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JarOutputStream jos = new JarOutputStream(bos);

        try {
            unpacker.unpack(null, jos);
            throw new AssertionError("expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }

        try {
            unpacker.unpack(new ByteArrayInputStream(new byte[0]), null);
            throw new AssertionError("expected NullPointerException for null output");
        } catch (NullPointerException expected) {
        }

        jos.close();
    }
}
