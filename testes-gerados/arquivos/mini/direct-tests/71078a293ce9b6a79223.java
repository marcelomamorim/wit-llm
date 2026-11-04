/*
 * @test
 * @summary Test FileTime.from(Instant) method behavior
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Objects;

public class FileTimeWitupTest {
    public static void main(String[] args) {
        testFromInstantNonNull();
        testFromInstantNull();
    }

    private static void testFromInstantNonNull() {
        Instant now = Instant.now();
        FileTime ft = FileTime.from(now);
        if (ft == null) {
            throw new AssertionError("FileTime.from returned null for non-null Instant");
        }
        // The returned FileTime should represent the same instant
        if (!Objects.equals(ft.toInstant(), now)) {
            throw new AssertionError("FileTime.from(Instant) does not preserve the instant value");
        }
    }

    private static void testFromInstantNull() {
        try {
            FileTime.from(null);
            throw new AssertionError("Expected NullPointerException when passing null to FileTime.from(Instant)");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
