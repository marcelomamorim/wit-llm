/*
 * @test
 * @summary Witup-based checks for Runtime.loadLibrary exception paths
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();

        try {
            rt.loadLibrary(null);
            throw new RuntimeException("NullPointerException expected for null libname");
        } catch (NullPointerException expected) {
        }

        try {
            rt.loadLibrary("witupNoSuchLibrary");
            throw new RuntimeException("UnsatisfiedLinkError expected for missing library");
        } catch (UnsatisfiedLinkError expected) {
        }

        try {
            rt.loadLibrary("dir" + java.io.File.separatorChar + "witupLib");
            throw new RuntimeException("UnsatisfiedLinkError expected for separator in libname");
        } catch (UnsatisfiedLinkError expected) {
        }
    }
}
