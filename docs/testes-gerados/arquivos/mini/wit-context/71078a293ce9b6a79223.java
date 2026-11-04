/*
 * @test
 * @summary Test FileTime.from(Instant) method behavior including null argument
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class FileTimeWitupTest {
    public static void main(String[] args) {
        testFromInstantNonNull();
        testFromInstantNull();
        System.out.println("FileTimeWitupTest passed.");
    }

    private static void testFromInstantNonNull() {
        Instant now = Instant.now();
        FileTime ft = FileTime.from(now);
        if (ft == null) {
            throw new AssertionError("FileTime.from(Instant) returned null");
        }
        // The returned FileTime should represent the same instant
        if (!ft.toInstant().equals(now)) {
            throw new AssertionError("FileTime.from(Instant) does not preserve instant value");
        }
    }

    private static void testFromInstantNull() {
        try {
            FileTime.from(null);
            throw new AssertionError("Expected NullPointerException for null instant argument");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
