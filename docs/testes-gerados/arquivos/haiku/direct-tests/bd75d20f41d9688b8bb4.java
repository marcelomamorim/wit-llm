/*
 * @test
 * @summary Runtime.loadLibrary must signal failure with UnsatisfiedLinkError when the library cannot be found
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();

        boolean thrownByName = false;
        try {
            rt.loadLibrary("witup_nonexistent_lib_12345");
        } catch (UnsatisfiedLinkError e) {
            thrownByName = true;
        }
        if (!thrownByName) {
            throw new RuntimeException("Expected UnsatisfiedLinkError for missing library name");
        }

        boolean thrownByPath = false;
        try {
            rt.loadLibrary("/witup/nonexistent/path/libwitup_missing.so");
        } catch (UnsatisfiedLinkError e) {
            thrownByPath = true;
        }
        if (!thrownByPath) {
            throw new RuntimeException("Expected UnsatisfiedLinkError for missing absolute library path");
        }
    }
}
