/*
 * @test
 * @summary Test for UnpackerImpl.unpack(File, JarOutputStream) null argument handling
 * @run main UnpackerImplWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;

public class UnpackerImplWitupTest {

    public static void main(String[] args) throws IOException {
        testUnpackNullFile();
        testUnpackNullOutputStream();
    }

    private static void testUnpackNullFile() {
        try (JarOutputStream out = new JarOutputStream(new FileOutputStream("dummy.jar"))) {
            com.sun.java.util.jar.pack.UnpackerImpl instance = new com.sun.java.util.jar.pack.UnpackerImpl();
            instance.unpack(null, out);
            System.err.println("Failed: Expected NullPointerException for null File argument.");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        }
    }

    private static void testUnpackNullOutputStream() {
        File dummyFile = new File("dummy.jar");
        try {
            com.sun.java.util.jar.pack.UnpackerImpl instance = new com.sun.java.util.jar.pack.UnpackerImpl();
            instance.unpack(dummyFile, null);
            System.err.println("Failed: Expected NullPointerException for null OutputStream argument.");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        }
    }
}
