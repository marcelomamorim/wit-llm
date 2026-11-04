/*
 * @test
 * @summary Observable parsing behavior of Manifest(InputStream), which drives the peek() lookahead used by read()
 * @run main ManifestWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestWitupTest {

    public static void main(String[] args) throws IOException {
        testEmptyStream();
        testMainAttributeParsed();
        testEntryParsed();
        testCopyConstructorPreservesMainAttributes();
    }

    static void testEmptyStream() throws IOException {
        Manifest m = new Manifest(new ByteArrayInputStream(new byte[0]));
        check(m.getMainAttributes().size() == 0, "empty stream: main attributes must be empty");
        check(m.getEntries().isEmpty(), "empty stream: entries must be empty");
    }

    static void testMainAttributeParsed() throws IOException {
        byte[] data = "Manifest-Version: 1.0\r\n\r\n".getBytes("US-ASCII");
        Manifest m = new Manifest(new ByteArrayInputStream(data));
        String v = m.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION);
        check("1.0".equals(v), "main attribute Manifest-Version expected 1.0 but was " + v);
    }

    static void testEntryParsed() throws IOException {
        String s = "Manifest-Version: 1.0\r\n\r\nName: a/b\r\nFoo: bar\r\n\r\n";
        Manifest m = new Manifest(new ByteArrayInputStream(s.getBytes("US-ASCII")));
        Attributes a = m.getEntries().get("a/b");
        check(a != null, "entry a/b must be present");
        check("bar".equals(a.getValue("Foo")), "entry a/b attribute Foo expected bar");
    }

    static void testCopyConstructorPreservesMainAttributes() throws IOException {
        byte[] data = "Manifest-Version: 1.0\r\n\r\n".getBytes("US-ASCII");
        Manifest original = new Manifest(new ByteArrayInputStream(data));
        Manifest copy = new Manifest(original);
        String v = copy.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION);
        check("1.0".equals(v), "copy must preserve main attribute Manifest-Version");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
