/* @test    @summary Testa o método static from(Instant instant) da classe java.nio.file.attribute.FileTime    @run main FileTimeWitupTest */

import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class FileTimeWitupTest {

    public static void main(String[] args) {
        testFromWithNullInstant();
        testFromWithValidInstant();
    }

    private static void testFromWithNullInstant() {
        boolean exceptionThrown = false;
        try {
            FileTime result = FileTime.from((Instant) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null Instant");
        }
    }

    private static void testFromWithValidInstant() {
        Instant now = Instant.now();
        FileTime ft = FileTime.from(now);
        if (ft == null) {
            throw new AssertionError("FileTime.from(Instant) returned null");
        }
        if (!ft.instant.equals(now)) {
            throw new AssertionError("FileTime instant does not match input Instant");
        }
    }
}
