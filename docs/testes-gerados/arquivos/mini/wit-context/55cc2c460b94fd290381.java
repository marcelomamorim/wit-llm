/*
 * @test
 * @summary Test ZipEntry copy constructor behavior including null argument
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.util.Objects;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testCopyConstructorWithValidEntry();
        testCopyConstructorWithNullEntry();
    }

    private static void testCopyConstructorWithValidEntry() {
        ZipEntry original = new ZipEntry("testEntry");
        // Set some fields via reflection is not allowed, so test observable behavior only
        ZipEntry copy = new ZipEntry(original);
        // Check that the name is copied correctly
        if (!Objects.equals(copy.getName(), original.getName())) {
            throw new AssertionError("Copy constructor did not copy name correctly");
        }
        // Check that the copy is a distinct object
        if (copy == original) {
            throw new AssertionError("Copy constructor returned the same instance");
        }
    }

    private static void testCopyConstructorWithNullEntry() {
        try {
            new ZipEntry((ZipEntry) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"entry".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
