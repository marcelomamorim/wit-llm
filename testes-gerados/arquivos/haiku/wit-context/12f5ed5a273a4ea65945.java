/*
 * @test
 * @summary Observable behavior of Manifest parsing, round trip and copy constructor (exercises the Manifest.read path via public API)
 * @run main ManifestWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Manifest parse(String text) throws IOException {
        return new Manifest(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) throws Exception {
        String text = "Manifest-Version: 1.0\r\nMain-Class: Foo\r\n\r\n"
                    + "Name: a/b.txt\r\nX-Key: value\r\n\r\n";

        Manifest m = parse(text);
        check("1.0".equals(m.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION)),
              "Manifest-Version not parsed");
        check("Foo".equals(m.getMainAttributes().getValue("Main-Class")),
              "Main-Class not parsed");
        check(m.getEntries().size() == 1, "expected one entry");
        Attributes entry = m.getEntries().get("a/b.txt");
        check(entry != null, "entry a/b.txt missing");
        check("value".equals(entry.getValue("X-Key")), "entry attribute not parsed");

        Manifest empty = parse("");
        check(empty.getMainAttributes().isEmpty(), "empty manifest should have no main attributes");
        check(empty.getEntries().isEmpty(), "empty manifest should have no entries");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        m.write(bos);
        Manifest reread = new Manifest(new ByteArrayInputStream(bos.toByteArray()));
        check("Foo".equals(reread.getMainAttributes().getValue("Main-Class")),
              "round trip lost main attribute");
        check(reread.getEntries().size() == 1, "round trip lost entry");
        check("value".equals(reread.getEntries().get("a/b.txt").getValue("X-Key")),
              "round trip lost entry attribute");

        Manifest copy = new Manifest(m);
        check("Foo".equals(copy.getMainAttributes().getValue("Main-Class")),
              "copy lost main attribute");
        check(copy.getEntries().size() == 1, "copy lost entry");
        check("value".equals(copy.getEntries().get("a/b.txt").getValue("X-Key")),
              "copy lost entry attribute");
    }
}
