/*
 * @test
 * @summary URL.openStream returns the resource content for file URLs and throws IOException for missing files
 * @run main URLWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class URLWitupTest {
    public static void main(String[] args) throws Exception {
        Path tmp = Files.createTempFile("URLWitupTest", ".txt");
        try {
            String expected = "hello-openStream";
            Files.write(tmp, expected.getBytes(StandardCharsets.UTF_8));
            URL url = tmp.toUri().toURL();
            try (InputStream in = url.openStream()) {
                byte[] data = in.readAllBytes();
                String actual = new String(data, StandardCharsets.UTF_8);
                if (!expected.equals(actual)) {
                    throw new RuntimeException("Unexpected content: " + actual);
                }
            }

            Files.write(tmp, new byte[0]);
            try (InputStream in = tmp.toUri().toURL().openStream()) {
                if (in.read() != -1) {
                    throw new RuntimeException("Expected empty stream");
                }
            }
        } finally {
            Files.deleteIfExists(tmp);
        }

        Path missing = Files.createTempFile("URLWitupMissing", ".txt");
        Files.delete(missing);
        URL missingUrl = missing.toUri().toURL();
        try {
            InputStream in = missingUrl.openStream();
            in.close();
            throw new RuntimeException("Expected IOException for missing file");
        } catch (IOException expectedException) {
            // expected
        }
    }
}
