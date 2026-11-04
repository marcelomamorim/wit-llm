/*
 * @test
 * @summary Round-trip a jar through Pack200 public API to exercise the Package data structure behind addBytes
 * @run main PackageWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;

public class PackageWitupTest {

    private static final String ENTRY_NAME = "a/b.txt";
    private static final byte[] PAYLOAD = "hello pack200".getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("pkgwitup");
        Path jar = dir.resolve("in.jar");
        try {
            try (JarOutputStream jos = new JarOutputStream(Files.newOutputStream(jar))) {
                jos.putNextEntry(new JarEntry(ENTRY_NAME));
                jos.write(PAYLOAD);
                jos.closeEntry();
            }

            ByteArrayOutputStream packed = new ByteArrayOutputStream();
            try (JarFile jf = new JarFile(jar.toFile())) {
                Pack200.newPacker().pack(jf, packed);
            }

            ByteArrayOutputStream unpackedJar = new ByteArrayOutputStream();
            try (JarOutputStream jos = new JarOutputStream(unpackedJar)) {
                Pack200.newUnpacker().unpack(new ByteArrayInputStream(packed.toByteArray()), jos);
            }

            byte[] found = null;
            try (JarInputStream jis = new JarInputStream(new ByteArrayInputStream(unpackedJar.toByteArray()))) {
                JarEntry e;
                while ((e = jis.getNextJarEntry()) != null) {
                    if (ENTRY_NAME.equals(e.getName())) {
                        found = readAll(jis);
                    }
                }
            }

            if (found == null) {
                throw new RuntimeException("entry not found after round-trip: " + ENTRY_NAME);
            }
            if (!java.util.Arrays.equals(PAYLOAD, found)) {
                throw new RuntimeException("payload mismatch after round-trip");
            }
        } finally {
            Files.deleteIfExists(jar);
            Files.deleteIfExists(dir);
        }
    }

    private static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while ((n = in.read(buf)) != -1) {
            bos.write(buf, 0, n);
        }
        return bos.toByteArray();
    }
}
