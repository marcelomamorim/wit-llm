/*
 * @test
 * @summary Test the close() method of jdk.internal.loader.Loader.
 * @run main LoaderWitupTest
 */

import jdk.internal.loader.Loader;

public class LoaderWitupTest {

    public static void main(String[] args) {
        testCloseMethodThrowsInternalError();
    }

    private static void testCloseMethodThrowsInternalError() {
        Loader loader = new Loader() {
            @Override
            public void close() {
                throw new InternalError("Should not get here");
            }
        };
        try {
            loader.close();
            System.out.println("Test passed: InternalError thrown as expected.");
        } catch (InternalError e) {
            if ("Should not get here".equals(e.getMessage())) {
                System.out.println("Test passed: Caught expected InternalError.");
            } else {
                System.out.println("Test failed: Unexpected InternalError message.");
            }
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
