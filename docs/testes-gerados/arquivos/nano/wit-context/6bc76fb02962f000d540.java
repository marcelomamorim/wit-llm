/*
 * @test
 * @summary Test for ZipEntry constructor with null and long name
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.util.Objects;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testNullName();
        testLongName();
    }

    public static void testNullName() {
        try {
            new ZipEntry(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testLongName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 70000; i++) {
            sb.append('a');
        }
        String longName = sb.toString();
        try {
            new ZipEntry(longName);
            throw new AssertionError("Expected IllegalArgumentException for long name");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
