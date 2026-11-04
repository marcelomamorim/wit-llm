/*
 * @test
 * @summary Checks Files.readString(Path) null handling and UTF-8 round trip on the current checkout
 * @run main FilesWitupTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        testNullPathThrowsNullPointerException();
        testReadStringUtf8RoundTrip();
    }

    private static void testNullPathThrowsNullPointerException() throws IOException {
        try {
            Files.readString(null);
            throw new AssertionError("Expected NullPointerException for null path");
        } catch (NullPointerException expected) {
        }
    }

    private static void testReadStringUtf8RoundTrip() throws IOException {
        String expected = "h\u00e9llo";
        Path p = Files.createTempFile("witup", ".txt");
        try {
            Files.write(p, expected.getBytes(StandardCharsets.UTF_8));
            String actual = Files.readString(p);
            if (!expected.equals(actual)) {
                throw new AssertionError("Unexpected content: " + actual);
            }
        } finally {
            Files.deleteIfExists(p);
        }
    }
}
