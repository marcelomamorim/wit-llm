/* @test    @summary Test FileTime.from(Instant instant) method for correctness    @run main FileTimeWitupTest */

import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        testFromInstantWithCurrentTime();
        testFromInstantWithEpoch();
        testFromInstantWithFutureTime();
    }

    private static void testFromInstantWithCurrentTime() {
        Instant now = Instant.now();
        FileTime ft = FileTime.from(now);
        assert ft != null : "FileTime from current instant should not be null";
        assert ft.instant.equals(now) : "FileTime instant should match the input instant";
    }

    private static void testFromInstantWithEpoch() {
        Instant epoch = Instant.ofEpochSecond(0);
        FileTime ft = FileTime.from(epoch);
        assert ft != null : "FileTime from epoch instant should not be null";
        assert ft.instant.equals(epoch) : "FileTime instant should match the epoch";
    }

    private static void testFromInstantWithFutureTime() {
        Instant future = Instant.ofEpochSecond(4102444800L); // Year 2100
        FileTime ft = FileTime.from(future);
        assert ft != null : "FileTime from future instant should not be null";
        assert ft.instant.equals(future) : "FileTime instant should match the future instant";
    }
}
