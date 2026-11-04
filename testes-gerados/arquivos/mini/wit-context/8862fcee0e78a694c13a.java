/*
 * @test
 * @summary Test FileTime.from(long, TimeUnit) method for null unit and valid inputs
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class FileTimeWitupTest {
    public static void main(String[] args) {
        testFromWithValidUnit();
        testFromWithNullUnit();
        System.out.println("FileTime.from tests passed");
    }

    private static void testFromWithValidUnit() {
        // Test with a valid TimeUnit and a positive value
        FileTime ft1 = FileTime.from(12345L, TimeUnit.SECONDS);
        if (ft1 == null) {
            throw new AssertionError("FileTime.from returned null for valid input");
        }

        // Test with a valid TimeUnit and a negative value
        FileTime ft2 = FileTime.from(-98765L, TimeUnit.MILLISECONDS);
        if (ft2 == null) {
            throw new AssertionError("FileTime.from returned null for valid negative input");
        }

        // Test with zero value
        FileTime ft3 = FileTime.from(0L, TimeUnit.NANOSECONDS);
        if (ft3 == null) {
            throw new AssertionError("FileTime.from returned null for zero value");
        }
    }

    private static void testFromWithNullUnit() {
        try {
            FileTime.from(1000L, null);
            throw new AssertionError("Expected NullPointerException not thrown when unit is null");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
