/*
 * @test
 * @summary WITUP-derived checks for Manifest parsing through public APIs
 * @run main ManifestWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestWitupTest {

    public static void main(String[] args) throws IOException {
        byte[] data = ("Manifest-Version: 1.0\r\nMain-Class: Foo\r\n\r\n"
                + "Name: a/b\r\nX-Key: v\r\n\r\n").getBytes(StandardCharsets.UTF_8);

        Manifest m = new Manifest(new ByteArrayInputStream(data));
        Attributes main = m.getMainAttributes();
        check("1.0".equals(main.getValue("Manifest-Version")), "Manifest-Version not parsed");
        check("Foo".equals(main.getValue("Main-Class")), "Main-Class not parsed");

        Attributes entry = m.getEntries().get("a/b");
        check(entry != null, "entry a/b missing");
        check("v".equals(entry.getValue("X-Key")), "entry attribute not parsed");

        Manifest empty = new Manifest(new ByteArrayInputStream(new byte[0]));
        check(empty.getMainAttributes().isEmpty(), "empty input should give empty main attributes");
        check(empty.getEntries().isEmpty(), "empty input should give no entries");

        Manifest copy = new Manifest(m);
        check("Foo".equals(copy.getMainAttributes().getValue("Main-Class")), "copy lost main attributes");
        check(copy.getEntries().get("a/b") != null, "copy lost entries");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
