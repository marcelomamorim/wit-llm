/* @test    @summary Test Runtime.loadLibrary with a valid library name    @run main RuntimeWitupTest */

import java.lang.Runtime;

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        try {
            // Attempt to load a standard library, e.g., "java" (which is typically available)
            runtime.loadLibrary("java");
            System.out.println("loadLibrary("java") executed without exception.");
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Expected exception caught: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("SecurityException caught: " + e.getMessage());
        }
    }
}
