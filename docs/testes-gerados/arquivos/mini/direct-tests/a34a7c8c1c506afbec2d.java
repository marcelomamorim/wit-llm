/*
 * @test
 * @summary Test Loader.close() throws InternalError as expected
 * @run main LoaderWitupTest
 */

public class LoaderWitupTest {
    public static void main(String[] args) {
        jdk.internal.loader.Loader loader = new jdk.internal.loader.Loader((java.util.Collection)java.util.Collections.emptyList(), null);
        try {
            loader.close();
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // expected
        }
    }
}
