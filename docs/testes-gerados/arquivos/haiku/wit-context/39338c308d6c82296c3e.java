/*
 * @test
 * @summary Tests JarOutputStream constructor with null manifest and manifest written as first entry
 * @run main JarOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullManifestThrowsNPE();
        testManifestWrittenAsFirstEntry();
        testNoManifestYieldsNullManifest();
    }

    static void testNullManifestThrowsNPE() {
        try {
            new JarOutputStream(new ByteArrayOutputStream(), null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException e) {
            if (!"man".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        } catch (java.io.IOException e) {
            throw new AssertionError("unexpected IOException", e);
        }
    }

    static void testManifestWrittenAsFirstEntry() throws Exception {
        Manifest man = new Manifest();
        man.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JarOutputStream jos = new JarOutputStream(bos, man);
        jos.close();
        JarInputStream jis = new JarInputStream(new ByteArrayInputStream(bos.toByteArray()));
        try {
            Manifest read = jis.getManifest();
            if (read == null) {
                throw new AssertionError("manifest not found");
            }
            String v = read.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION);
            if (!"1.0".equals(v)) {
                throw new AssertionError("unexpected version: " + v);
            }
        } finally {
            jis.close();
        }
    }

    static void testNoManifestYieldsNullManifest() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JarOutputStream jos = new JarOutputStream(bos);
        jos.close();
        JarInputStream jis = new JarInputStream(new ByteArrayInputStream(bos.toByteArray()));
        try {
            if (jis.getManifest() != null) {
                throw new AssertionError("expected no manifest");
            }
        } finally {
            jis.close();
        }
    }
}
