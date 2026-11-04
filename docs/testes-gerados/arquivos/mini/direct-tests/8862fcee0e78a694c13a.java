/*
 * @test
 * @summary Test java.nio.file.attribute.FileTime.from(long, TimeUnit) method
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class FileTimeWitupTest {
    public static void main(String[] args) {
        testFromWithValidUnit();
        testFromWithNullUnit();
    }

    private static void testFromWithValidUnit() {
        FileTime ft1 = FileTime.from(1000L, TimeUnit.MILLISECONDS);
        if (ft1 == null) {
            throw new AssertionError("FileTime.from returned null for valid input");
        }
        FileTime ft2 = FileTime.from(-500L, TimeUnit.SECONDS);
        if (ft2 == null) {
            throw new AssertionError("FileTime.from returned null for negative value");
        }
        // Check that the returned objects are distinct
        if (ft1.equals(ft2)) {
            throw new AssertionError("FileTime.from returned equal objects for different inputs");
        }
    }

    private static void testFromWithNullUnit() {
        try {
            FileTime.from(1000L, null);
            throw new AssertionError("Expected NullPointerException for null TimeUnit");
        } catch (NullPointerException e) {
            // Expected
            if (!"unit".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + e.getMessage());
            }
        }
    }
}
