/*
 * @test
 * @summary Verify FileTime.from(long, TimeUnit) rejects a null unit and accepts a non-null unit
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        testNullUnitThrowsNullPointerException();
        testNonNullUnitReturnsFileTime();
    }

    static void testNullUnitThrowsNullPointerException() {
        try {
            FileTime.from(1L, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException when unit is null");
    }

    static void testNonNullUnitReturnsFileTime() {
        FileTime ft = FileTime.from(-5L, TimeUnit.SECONDS);
        if (ft == null) {
            throw new RuntimeException("from(long, TimeUnit) returned null for a non-null unit");
        }
    }
}
