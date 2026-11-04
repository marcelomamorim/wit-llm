/*
 * @test
 * @summary Runtime.load must reject missing libraries with UnsatisfiedLinkError
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        String missing = new java.io.File(System.getProperty("java.io.tmpdir"), "no-such-witup-lib-12345.so").getAbsolutePath();
        expectUnsatisfiedLink(rt, missing);
        expectUnsatisfiedLink(rt, "relative-no-such-witup-lib.so");
    }

    private static void expectUnsatisfiedLink(Runtime rt, String name) {
        try {
            rt.load(name);
        } catch (UnsatisfiedLinkError e) {
            return;
        }
        throw new RuntimeException("UnsatisfiedLinkError not thrown for: " + name);
    }
}
