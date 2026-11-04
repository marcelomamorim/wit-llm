/*
 * @test
 * @summary Exercises manifest line reading through the public Manifest(InputStream) constructor, including continuation lines, entries, CRLF and a long-value round trip
 * @run main ManifestWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestWitupTest {

    public static void main(String[] args) throws Exception {
        testSimpleMainAttribute();
        testContinuationLine();
        testEntries();
        testCrLfLineEndings();
        testLongValueRoundTrip();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Manifest parse(String text) throws IOException {
        return new Manifest(new ByteArrayInputStream(text.getBytes("UTF-8")));
    }

    static void testSimpleMainAttribute() throws Exception {
        Manifest m = parse("Manifest-Version: 1.0\nCreated-By: test\n\n");
        check("1.0".equals(m.getMainAttributes().getValue("Manifest-Version")),
              "Manifest-Version mismatch");
        check("test".equals(m.getMainAttributes().getValue("Created-By")),
              "Created-By mismatch");
        check(m.getEntries().isEmpty(), "entries should be empty");
    }

    static void testContinuationLine() throws Exception {
        Manifest m = parse("Created-By: a\n b\n\n");
        check("ab".equals(m.getMainAttributes().getValue("Created-By")),
              "continuation line not joined: " + m.getMainAttributes().getValue("Created-By"));
    }

    static void testEntries() throws Exception {
        Manifest m = parse("Manifest-Version: 1.0\n\nName: foo.txt\nFoo: bar\n\n");
        Attributes a = m.getEntries().get("foo.txt");
        check(a != null, "entry foo.txt missing");
        check("bar".equals(a.getValue("Foo")), "entry attribute mismatch");
        check(m.getEntries().size() == 1, "entry count mismatch");
    }

    static void testCrLfLineEndings() throws Exception {
        Manifest m = parse("Manifest-Version: 1.0\r\nCreated-By: crlf\r\n\r\n");
        check("crlf".equals(m.getMainAttributes().getValue("Created-By")),
              "CRLF value mismatch");
    }

    static void testLongValueRoundTrip() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            sb.append('x');
        }
        sb.append('y');
        String longValue = sb.toString();

        Manifest original = new Manifest();
        original.getMainAttributes().putValue("Manifest-Version", "1.0");
        original.getMainAttributes().putValue("Long-Attr", longValue);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        original.write(bos);

        Manifest copy = new Manifest(new ByteArrayInputStream(bos.toByteArray()));
        check(longValue.equals(copy.getMainAttributes().getValue("Long-Attr")),
              "long value did not survive round trip");
        check("1.0".equals(copy.getMainAttributes().getValue("Manifest-Version")),
              "version lost in round trip");
    }
}
