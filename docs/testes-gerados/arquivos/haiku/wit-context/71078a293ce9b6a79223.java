/*
 * @test
 * @summary Verifies FileTime.from(Instant) returns the converted value and rejects null with NullPointerException
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class FileTimeWitupTest {
    public static void main(String[] args) {
        Instant instant = Instant.ofEpochSecond(1234567890L, 123456789L);
        FileTime ft = FileTime.from(instant);
        if (ft == null) {
            throw new RuntimeException("from(Instant) returned null");
        }
        if (!instant.equals(ft.toInstant())) {
            throw new RuntimeException("toInstant() does not match the source Instant");
        }
        if (ft.toMillis() != 1234567890123L) {
            throw new RuntimeException("unexpected millis: " + ft.toMillis());
        }
        FileTime epoch = FileTime.from(Instant.EPOCH);
        if (epoch.toMillis() != 0L) {
            throw new RuntimeException("epoch millis expected 0 but was " + epoch.toMillis());
        }
        try {
            FileTime.from((Instant) null);
            throw new RuntimeException("NullPointerException expected for null instant");
        } catch (NullPointerException expected) {
        }
    }
}
