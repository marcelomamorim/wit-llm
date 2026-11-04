/*
 * @test
 * @summary Probes ClassLoader package-access behavior through defineClass on a prohibited java.* name
 * @run main ClassLoaderWitupTest
 */

public class ClassLoaderWitupTest {

    static class ProbeLoader extends ClassLoader {
        ProbeLoader() {
            super();
        }

        Class<?> define(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }

    public static void main(String[] args) throws Exception {
        ProbeLoader loader = new ProbeLoader();
        byte[] bytes = new byte[0];
        boolean thrown = false;
        try {
            loader.define("java.lang.WitupProbe", bytes);
        } catch (SecurityException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected SecurityException for prohibited package name");
        }
    }
}
