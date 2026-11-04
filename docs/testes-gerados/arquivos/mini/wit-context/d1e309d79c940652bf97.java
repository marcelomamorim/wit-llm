/*
 * @test
 * @summary Test that JrtFileSystem.newWatchService() throws UnsupportedOperationException
 * @run main JrtFileSystemWitupTest
 */

import java.nio.file.WatchService;
import jdk.internal.jrtfs.JrtFileSystem;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) {
        JrtFileSystem fs = null;
        try {
            // We cannot instantiate JrtFileSystem directly due to lack of public constructors,
            // so we create an anonymous subclass to call the method under test.
            fs = new JrtFileSystem(null, null) {
                // no override needed
            };
        } catch (Exception e) {
            // ignore instantiation exceptions for this test
        }

        if (fs == null) {
            // If instantiation failed, test the exception by subclassing and overriding newWatchService
            // to call super.newWatchService() which throws UnsupportedOperationException.
            try {
                new JrtFileSystem(null, null) {
                    public WatchService testNewWatchService() {
                        return super.newWatchService();
                    }
                }.testNewWatchService();
                throw new AssertionError("Expected UnsupportedOperationException not thrown");
            } catch (UnsupportedOperationException expected) {
                // expected
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception: " + e);
            }
            return;
        }

        try {
            fs.newWatchService();
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
