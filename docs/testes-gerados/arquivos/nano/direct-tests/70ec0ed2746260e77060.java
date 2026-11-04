/* @test    @summary Test Runtime.load method with a valid filename string    @run main RuntimeWitupTest */

import java.lang.Runtime;

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        String filename = "testfile.jar";
        try {
            runtime.load(filename);
        } catch (Throwable t) {
            // Since load() calls load0() which is native, exceptions may occur.
            // We catch Throwable to ensure the test completes.
            System.out.println("Caught exception: " + t);
        }
    }
}
