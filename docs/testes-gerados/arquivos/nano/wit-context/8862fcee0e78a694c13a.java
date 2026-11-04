/*
 * @test
 * @summary Test for FileTime.from(long, TimeUnit) method, including null argument handling.
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        testFromWithValidArguments();
        testFromWithNullTimeUnit();
    }

    static void testFromWithValidArguments() {
        long value = 123456789L;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        FileTime ft = FileTime.from(value, unit);
        if (ft == null) {
            throw new AssertionError("FileTime.from should not return null for valid arguments");
        }
        System.out.println("testFromWithValidArguments passed");
    }

    static void testFromWithNullTimeUnit() {
        long value = 987654321L;
        try {
            FileTime ft = FileTime.from(value, null);
            throw new AssertionError("Expected NullPointerException when passing null TimeUnit");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("testFromWithNullTimeUnit passed");
        }
    }
}
