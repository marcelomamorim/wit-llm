/* @test    @summary Test for ZipEntry constructor with null argument to verify NullPointerException is thrown.    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
    }

    public static void testConstructorWithNull() {
        try {
            new ZipEntry((ZipEntry) null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
