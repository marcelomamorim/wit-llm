/*
 * @test
 * @summary Witup-generated checks for FileTime.to(TimeUnit)
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        check(FileTime.from(5, TimeUnit.SECONDS).to(TimeUnit.MILLISECONDS) == 5000L, "seconds to millis");
        check(FileTime.from(5000, TimeUnit.MILLISECONDS).to(TimeUnit.SECONDS) == 5L, "millis to seconds");
        check(FileTime.from(-2, TimeUnit.SECONDS).to(TimeUnit.MILLISECONDS) == -2000L, "negative value");
        check(FileTime.from(Instant.ofEpochSecond(5, 500000000L)).to(TimeUnit.MILLISECONDS) == 5500L, "instant to millis");
        check(FileTime.from(Instant.ofEpochSecond(5, 500000000L)).to(TimeUnit.SECONDS) == 5L, "instant truncation to seconds");
        check(FileTime.from(Instant.MAX).to(TimeUnit.MILLISECONDS) == Long.MAX_VALUE, "instant saturation");
        expectNullPointer(new Runnable() {
            public void run() {
                FileTime.from(1, TimeUnit.SECONDS).to(null);
            }
        });
        expectNullPointer(new Runnable() {
            public void run() {
                FileTime.from(Instant.ofEpochSecond(1)).to(null);
            }
        });
        expectNullPointer(new Runnable() {
            public void run() {
                FileTime.from(1, null);
            }
        });
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNullPointer(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }
}
