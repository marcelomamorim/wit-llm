/*
 * @test
 * @summary Test ZipFile(File file, int mode) constructor for argument validation and exception paths
 * @run main ZipFileWitupTest
 */

import java.util.zip.ZipFile;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ZipFileWitupTest {
    public static void main(String[] args) throws Exception {
        testNullFile();
        testInvalidMode();
        testValidMode();
    }

    private static void testNullFile() {
        try {
            new ZipFile((File) null, 1);
            throw new RuntimeException("Expected NullPointerException for null file");
        } catch (NullPointerException expected) {
            // Expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException for null file", ioe);
        }
    }

    private static void testInvalidMode() {
        File dummyFile = new File("doesnotmatter.zip");
        // Modes that are invalid: mode == 0 or mode has bits other than 0x1 or 0x4
        int[] invalidModes = {0, 2, 3, 8, 16, 5, 6, 7, 15};
        for (int mode : invalidModes) {
            try {
                new ZipFile(dummyFile, mode);
                throw new RuntimeException("Expected IllegalArgumentException for invalid mode: " + mode);
            } catch (IllegalArgumentException expected) {
                // Expected
            } catch (IOException ioe) {
                throw new RuntimeException("Unexpected IOException for invalid mode: " + mode, ioe);
            } catch (NullPointerException npe) {
                // Defensive: if dummyFile is null, but it is not
                throw new RuntimeException("Unexpected NullPointerException for invalid mode: " + mode, npe);
            }
        }
    }

    private static void testValidMode() throws IOException {
        // Valid modes: mode & 0x1 == 1 and mode & ~(0x1 | 0x4) == 0
        // i.e. mode == 1 or mode == 5 (1 | 4)
        File dummyFile = new File("doesnotmatter.zip");
        int[] validModes = {1, 5};
        for (int mode : validModes) {
            try {
                ZipFile zf = new ZipFile(dummyFile, mode);
                zf.close();
            } catch (NullPointerException npe) {
                // Defensive: dummyFile is not null, so NPE unexpected
                throw new RuntimeException("Unexpected NullPointerException for valid mode: " + mode, npe);
            } catch (IllegalArgumentException iae) {
                throw new RuntimeException("Unexpected IllegalArgumentException for valid mode: " + mode, iae);
            }
        }
    }
}
