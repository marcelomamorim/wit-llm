/*
 * @test
 * @summary Verify Manifest stream parsing through the public InputStream constructor, which exercises the internal buffered read path
 * @run main ManifestWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestWitupTest {

    private static final String CONTENT =
        "Manifest-Version: 1.0\r\nCreated-By: jtreg\r\n\r\n" +
        "Name: a/b\r\nX-Key: v\r\n\r\n";

    static class OneByteStream extends InputStream {
        private final byte[] data;
        private int pos;

        OneByteStream(byte[] data) {
            this.data = data;
        }

        @Override
        public int read() throws IOException {
            return pos < data.length ? (data[pos++] & 0xff) : -1;
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] data = CONTENT.getBytes("UTF-8");

        Manifest m = new Manifest(new ByteArrayInputStream(data));
        verifyContent(m);

        Manifest chunked = new Manifest(new OneByteStream(data));
        verifyContent(chunked);

        Manifest empty = new Manifest(new ByteArrayInputStream(new byte[0]));
        check(empty.getMainAttributes().size() == 0, "empty main attributes expected");
        check(empty.getEntries().isEmpty(), "empty entries expected");
    }

    private static void verifyContent(Manifest m) {
        Attributes main = m.getMainAttributes();
        check("1.0".equals(main.getValue("Manifest-Version")), "Manifest-Version mismatch");
        check("jtreg".equals(main.getValue("Created-By")), "Created-By mismatch");
        check(m.getEntries().size() == 1, "expected exactly one entry");
        Attributes entry = m.getEntries().get("a/b");
        check(entry != null, "entry a/b missing");
        check("v".equals(entry.getValue("X-Key")), "X-Key mismatch");
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
