/*
 * @test
 * @summary Checks public File-based unpack argument validation and missing-file behavior of UnpackerImpl
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main UnpackerImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.jar.JarOutputStream;

import com.sun.java.util.jar.pack.UnpackerImpl;

public class UnpackerImplWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void expectException(Class<? extends Throwable> type, String message, Action action) throws Exception {
        try {
            action.run();
        } catch (Throwable t) {
            if (!type.isInstance(t)) {
                throw new AssertionError("Unexpected exception type: " + t, t);
            }
            if (message != null && !message.equals(t.getMessage())) {
                throw new AssertionError("Unexpected message: " + t.getMessage());
            }
            return;
        }
        throw new AssertionError("Expected " + type.getName());
    }

    public static void main(String[] args) throws Exception {
        UnpackerImpl unpacker = new UnpackerImpl();
        JarOutputStream jos = new JarOutputStream(new ByteArrayOutputStream());
        File nonExistent = new File("witup-missing-file-7f3a9c1e.pack");

        expectException(NullPointerException.class, "null input",
                () -> unpacker.unpack((File) null, jos));

        expectException(NullPointerException.class, "null output",
                () -> unpacker.unpack(nonExistent, null));

        expectException(FileNotFoundException.class, null,
                () -> unpacker.unpack(nonExistent, jos));

        jos.close();
    }
}
