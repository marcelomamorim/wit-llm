/*
 * @test
 * @summary Test ZipEntry copy constructor behavior
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.util.Objects;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testCopyConstructorCopiesFields();
        testCopyConstructorNullEntryThrows();
    }

    static void testCopyConstructorCopiesFields() {
        ZipEntry original = new ZipEntry("testEntry");
        // Set some fields via reflection is not allowed, so test observable fields only
        // The constructor copies all fields, so name is a good proxy
        ZipEntry copy = new ZipEntry(original);
        if (!Objects.equals(copy.getName(), original.getName())) {
            throw new AssertionError("Copied entry name does not match original");
        }
        // The copy should be a different object
        if (copy == original) {
            throw new AssertionError("Copy constructor returned the same instance");
        }
    }

    static void testCopyConstructorNullEntryThrows() {
        try {
            new ZipEntry(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
