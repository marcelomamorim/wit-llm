/*
 * @test
 * @summary Observable behavior of Manifest parsing, which exercises FastInputStream.peek() indirectly
 * @run main ManifestWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyStreamYieldsEmptyManifest();
        testMainAttributesAndEntryParsed();
    }

    static void testEmptyStreamYieldsEmptyManifest() throws IOException {
        Manifest m = new Manifest(new ByteArrayInputStream(new byte[0]));
        if (!m.getEntries().isEmpty()) {
            throw new RuntimeException("expected no entries for empty input");
        }
        if (m.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION) != null) {
            throw new RuntimeException("expected no Manifest-Version for empty input");
        }
    }

    static void testMainAttributesAndEntryParsed() throws IOException {
        String text = "Manifest-Version: 1.0\n\nName: a\nFoo: bar\n\n";
        Manifest m = new Manifest(new ByteArrayInputStream(text.getBytes("UTF-8")));
        if (!"1.0".equals(m.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION))) {
            throw new RuntimeException("unexpected Manifest-Version");
        }
        Attributes entry = m.getEntries().get("a");
        if (entry == null) {
            throw new RuntimeException("missing entry 'a'");
        }
        if (!"bar".equals(entry.getValue("Foo"))) {
            throw new RuntimeException("unexpected value for Foo");
        }
    }
}
