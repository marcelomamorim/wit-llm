/*
 * @test
 * @summary Tests the single-argument ZipInputStream constructor: null input must throw NullPointerException and a non-null empty stream must yield no entries
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testEmptyStreamHasNoEntries();
    }

    static void testNullInputThrowsNPE() {
        try {
            new ZipInputStream(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            if (e.getMessage() == null || !e.getMessage().contains("in is null")) {
                throw new RuntimeException("Unexpected NPE message: " + e.getMessage(), e);
            }
        }
    }

    static void testEmptyStreamHasNoEntries() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]))) {
            if (zis.getNextEntry() != null) {
                throw new RuntimeException("Expected no ZIP entries in empty stream");
            }
        }
    }
}
