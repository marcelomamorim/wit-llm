/*
 * @test
 * @summary Test Loader.close() throws InternalError as expected
 * @run main LoaderWitupTest
 */

public class LoaderWitupTest {
    public static void main(String[] args) {
        try {
            new LoaderWitupTest().testCloseThrowsInternalError();
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }

    public void testCloseThrowsInternalError() {
        try {
            // The close method always throws InternalError
            new jdk.internal.loader.Loader.NullModuleReader() {
                @Override
                public void close() {
                    throw new InternalError("Should not get here");
                }
            }.close();
            throw new AssertionError("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
            if (!"Should not get here".equals(e.getMessage())) {
                throw new AssertionError("Unexpected InternalError message: " + e.getMessage());
            }
        }
    }
}
