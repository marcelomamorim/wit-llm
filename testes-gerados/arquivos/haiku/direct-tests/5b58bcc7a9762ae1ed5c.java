/*
 * @test
 * @summary Verifies URLClassPath.Resource.getInputStream() returns the content of a resource from a directory URL
 * @modules java.base/jdk.internal.loader
 * @run main URLClassPathWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.security.AccessController;

import jdk.internal.loader.URLClassPath;

public class URLClassPathWitupTest {

    private static String readAll(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[256];
        int n;
        while ((n = in.read(buf)) != -1) {
            bos.write(buf, 0, n);
        }
        return new String(bos.toByteArray(), "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        File dir = new File("witup_urlcp_dir");
        dir.mkdirs();
        File f = new File(dir, "hello.txt");
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write("hello".getBytes("UTF-8"));
        }
        try {
            URL dirUrl = dir.toURI().toURL();
            URLClassPath ucp = new URLClassPath(new URL[]{dirUrl}, AccessController.getContext());

            URLClassPath.Resource r = ucp.getResource("hello.txt", true);
            if (r == null) {
                throw new RuntimeException("Resource hello.txt not found");
            }
            try (InputStream in = r.getInputStream()) {
                String content = readAll(in);
                if (!"hello".equals(content)) {
                    throw new RuntimeException("Unexpected content: " + content);
                }
            }

            URLClassPath.Resource r2 = ucp.getResource("hello.txt", true);
            if (r2 == null) {
                throw new RuntimeException("Second lookup returned null");
            }
            try (InputStream in2 = r2.getInputStream()) {
                String content2 = readAll(in2);
                if (!"hello".equals(content2)) {
                    throw new RuntimeException("Unexpected content on second read: " + content2);
                }
            }

            if (ucp.getResource("missing.txt", true) != null) {
                throw new RuntimeException("Missing resource should not be found");
            }
        } finally {
            f.delete();
            dir.delete();
        }
    }
}
