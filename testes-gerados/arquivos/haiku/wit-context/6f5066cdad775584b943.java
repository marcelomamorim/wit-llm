/*
 * @test
 * @summary URL.getContent() delegates to the connection opened by the URL's handler and returns the connection's content
 * @run main URLWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class URLWitupTest {

    static final byte[] PAYLOAD = {'w', 'i', 't'};

    static class PayloadHandler extends URLStreamHandler {
        @Override
        protected URLConnection openConnection(URL u) {
            return new URLConnection(u) {
                @Override
                public void connect() {
                }

                @Override
                public String getContentType() {
                    return "application/x-witup-test";
                }

                @Override
                public InputStream getInputStream() {
                    return new ByteArrayInputStream(PAYLOAD);
                }
            };
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("witup", "example.test", -1, "/resource", new PayloadHandler());
        Object content = url.getContent();
        check(content instanceof InputStream, "getContent() should return an InputStream for unknown type");
        byte[] read = readAll((InputStream) content);
        check(read.length == PAYLOAD.length, "unexpected content length: " + read.length);
        for (int i = 0; i < PAYLOAD.length; i++) {
            check(read[i] == PAYLOAD[i], "unexpected content byte at index " + i);
        }
    }
}
