/*
 * @test
 * @summary Verify JavaRuntimeURLConnection.getInputStream returns the bytes of a jrt resource
 * @run main JavaRuntimeURLConnectionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class JavaRuntimeURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testResourceStreamStartsWithClassMagic();
        testResourceStreamIsDeterministic();
    }

    static void testResourceStreamStartsWithClassMagic() throws IOException {
        URL url = new URL("jrt:/java.base/java/lang/Object.class");
        URLConnection conn = url.openConnection();
        try (InputStream in = conn.getInputStream()) {
            check(in != null, "getInputStream returned null");
            int b0 = in.read();
            int b1 = in.read();
            int b2 = in.read();
            int b3 = in.read();
            check(b0 == 0xCA && b1 == 0xFE && b2 == 0xBA && b3 == 0xBE,
                  "unexpected class file magic");
        }
    }

    static void testResourceStreamIsDeterministic() throws IOException {
        byte[] first = readAll(new URL("jrt:/java.base/java/lang/Object.class")
                .openConnection().getInputStream());
        byte[] second = readAll(new URL("jrt:/java.base/java/lang/Object.class")
                .openConnection().getInputStream());
        check(first.length > 0, "resource content is empty");
        check(java.util.Arrays.equals(first, second),
              "two reads of the same resource differ");
    }

    static byte[] readAll(InputStream in) throws IOException {
        try (InputStream s = in) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = s.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
