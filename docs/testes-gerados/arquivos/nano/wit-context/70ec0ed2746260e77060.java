/*
 * @test
 * @summary Test for Runtime.load(String filename) method to observe exception behavior.
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testLoadWithNullFilename();
        testLoadWithValidFilename();
    }

    private static void testLoadWithNullFilename() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.load(null);
            System.out.println("testLoadWithNullFilename: No exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testLoadWithNullFilename: Caught expected NullPointerException");
        } catch (Exception e) {
            System.out.println("testLoadWithNullFilename: Caught unexpected exception: " + e);
        }
    }

    private static void testLoadWithValidFilename() {
        Runtime runtime = Runtime.getRuntime();
        try {
            // Using a dummy filename; actual loading is not performed in this test
            runtime.load("dummyfile.txt");
            System.out.println("testLoadWithValidFilename: Method invoked without exception");
        } catch (Exception e) {
            System.out.println("testLoadWithValidFilename: Caught exception: " + e);
        }
    }
}
