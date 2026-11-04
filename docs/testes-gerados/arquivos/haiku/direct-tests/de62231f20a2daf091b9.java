/*
 * @test
 * @summary Verify JavaRuntimeURLConnection.getInputStream returns class file bytes for a jrt: resource
 * @run main JavaRuntimeURLConnectionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class JavaRuntimeURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] first = read("jrt:/java.base/java/lang/Object.class");
        if (first.length < 4) {
            throw new RuntimeException("resource too short: " + first.length);
        }
        if ((first[0] & 0xFF) != 0xCA || (first[1] & 0xFF) != 0xFE
                || (first[2] & 0xFF) != 0xBA || (first[3] & 0xFF) != 0xBE) {
            throw new RuntimeException("unexpected class file magic");
        }
        byte[] second = read("jrt:/java.base/java/lang/Object.class");
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("repeated getInputStream returned different bytes");
        }
    }

    private static byte[] read(String spec) throws IOException {
        URLConnection c = new URL(spec).openConnection();
        try (InputStream in = c.getInputStream()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        }
    }
}
