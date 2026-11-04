/*
 * @test
 * @summary Witup-generated checks for the JarInputStream(InputStream) constructor and its observable behavior
 * @run main JarInputStreamWitupTest
 */

import java.io.*;
import java.util.jar.*;

public class JarInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyStreamHasNoManifest();
        testManifestIsReadFromJar();
        testVerifyConstructorIsEquivalent();
        testUnderlyingIOExceptionPropagates();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] jarWithManifest() throws IOException {
        Manifest mf = new Manifest();
        mf.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JarOutputStream jos = new JarOutputStream(bos, mf);
        jos.close();
        return bos.toByteArray();
    }

    static void testEmptyStreamHasNoManifest() throws IOException {
        JarInputStream jis = new JarInputStream(new ByteArrayInputStream(new byte[0]));
        try {
            check(jis.getManifest() == null, "empty input must not yield a manifest");
            check(jis.getNextEntry() == null, "empty input must have no entries");
        } finally {
            jis.close();
        }
    }

    static void testManifestIsReadFromJar() throws IOException {
        JarInputStream jis = new JarInputStream(new ByteArrayInputStream(jarWithManifest()));
        try {
            Manifest m = jis.getManifest();
            check(m != null, "manifest must be read from a jar with META-INF/MANIFEST.MF");
            check("1.0".equals(m.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION)),
                  "Manifest-Version must be preserved");
        } finally {
            jis.close();
        }
    }

    static void testVerifyConstructorIsEquivalent() throws IOException {
        byte[] jar = jarWithManifest();
        JarInputStream defaultStream = new JarInputStream(new ByteArrayInputStream(jar));
        JarInputStream verifyTrue = new JarInputStream(new ByteArrayInputStream(jar), true);
        JarInputStream verifyFalse = new JarInputStream(new ByteArrayInputStream(jar), false);
        try {
            check(defaultStream.getManifest() != null, "single-arg constructor must read manifest");
            check(verifyTrue.getManifest() != null, "verify=true must read manifest");
            check(verifyFalse.getManifest() != null, "verify=false must read manifest");
            check("1.0".equals(defaultStream.getManifest().getMainAttributes()
                    .getValue(Attributes.Name.MANIFEST_VERSION)),
                  "single-arg constructor must match two-arg behavior");
        } finally {
            defaultStream.close();
            verifyTrue.close();
            verifyFalse.close();
        }
    }

    static void testUnderlyingIOExceptionPropagates() {
        boolean thrown = false;
        try {
            new JarInputStream(new FailingInputStream());
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "I/O failure of the underlying stream must propagate as IOException");
    }

    static class FailingInputStream extends InputStream {
        @Override
        public int read() throws IOException {
            throw new IOException("injected failure");
        }
    }
}
