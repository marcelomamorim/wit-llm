/*
 * @test
 * @summary Verifies OpenFileInputStreamAction constructors and run() opening a FileInputStream
 * @modules java.base/sun.security.action
 * @run main OpenFileInputStreamActionWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import sun.security.action.OpenFileInputStreamAction;

public class OpenFileInputStreamActionWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("ofisa", ".bin");
        f.deleteOnExit();
        byte[] data = {1, 2, 3, 42};
        try (FileOutputStream out = new FileOutputStream(f)) {
            out.write(data);
        }

        // String constructor: run() opens the named file
        try (FileInputStream in = new OpenFileInputStreamAction(f.getPath()).run()) {
            check(readAll(in), data, "String constructor");
        }

        // File constructor: run() opens the same file
        try (FileInputStream in = new OpenFileInputStreamAction(f).run()) {
            check(readAll(in), data, "File constructor");
        }

        // Missing file: run() propagates FileNotFoundException from FileInputStream
        File missing = new File(f.getPath() + ".missing");
        boolean notFound = false;
        try {
            new OpenFileInputStreamAction(missing.getPath()).run();
        } catch (FileNotFoundException e) {
            notFound = true;
        }
        if (!notFound) {
            throw new RuntimeException("Expected FileNotFoundException for missing file");
        }

        // Null String: File(String) throws NullPointerException in the constructor
        boolean npe = false;
        try {
            new OpenFileInputStreamAction((String) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null filename");
        }
    }

    private static byte[] readAll(FileInputStream in) throws IOException {
        byte[] buf = new byte[16];
        int total = 0;
        int n;
        while ((n = in.read(buf, total, buf.length - total)) != -1) {
            total += n;
            if (total == buf.length) {
                byte[] bigger = new byte[buf.length * 2];
                System.arraycopy(buf, 0, bigger, 0, total);
                buf = bigger;
            }
        }
        byte[] result = new byte[total];
        System.arraycopy(buf, 0, result, 0, total);
        return result;
    }

    private static void check(byte[] actual, byte[] expected, String label) {
        if (actual.length != expected.length) {
            throw new RuntimeException(label + ": length mismatch " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new RuntimeException(label + ": mismatch at index " + i);
            }
        }
    }
}
