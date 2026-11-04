/*
 * @test
 * @summary Verifies FileOutputStream(File) creates, truncates and writes files, and throws FileNotFoundException for a missing parent directory
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        File tmp = File.createTempFile("fosWitup", ".tmp");
        try {
            try (FileOutputStream out = new FileOutputStream(tmp)) {
                out.write("abc".getBytes(StandardCharsets.US_ASCII));
            }
            check(readAscii(tmp), "abc");

            try (FileOutputStream out = new FileOutputStream(tmp)) {
                out.write('x');
            }
            check(readAscii(tmp), "x");
        } finally {
            tmp.delete();
        }

        File dir = tmp.getAbsoluteFile().getParentFile();
        File fresh = new File(dir, "witup-fresh-" + System.nanoTime() + ".tmp");
        try {
            new FileOutputStream(fresh).close();
            if (!fresh.exists()) {
                throw new RuntimeException("file was not created");
            }
            if (fresh.length() != 0L) {
                throw new RuntimeException("expected empty file, length=" + fresh.length());
            }
        } finally {
            fresh.delete();
        }

        File missing = new File(dir, "no-such-dir-witup" + File.separator + "out.bin");
        boolean thrown = false;
        try {
            new FileOutputStream(missing);
        } catch (FileNotFoundException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("FileNotFoundException expected for missing parent directory");
        }
    }

    private static String readAscii(File f) throws Exception {
        return new String(Files.readAllBytes(f.toPath()), StandardCharsets.US_ASCII);
    }

    private static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected \"" + expected + "\" but was \"" + actual + "\"");
        }
    }
}
