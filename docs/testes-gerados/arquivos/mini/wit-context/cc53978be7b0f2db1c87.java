/*
 * @test
 * @summary Test that JrtFileSystem.getUserPrincipalLookupService() throws UnsupportedOperationException
 * @run main JrtFileSystemWitupTest
 */

import jdk.internal.jrtfs.JrtFileSystem;
import java.nio.file.attribute.UserPrincipalLookupService;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) {
        JrtFileSystem fs = null;
        try {
            // We cannot instantiate JrtFileSystem directly due to lack of public constructors,
            // so we test the method by subclassing anonymously to call the method.
            fs = new JrtFileSystem(null, null) {
                // no override
            };
        } catch (Throwable t) {
            // Ignore instantiation failure, test the method directly via anonymous subclass
        }

        try {
            if (fs != null) {
                fs.getUserPrincipalLookupService();
                throw new AssertionError("Expected UnsupportedOperationException not thrown");
            } else {
                // If we cannot instantiate, test by subclass with overridden method to call super
                new JrtFileSystem(null, null) {
                    public UserPrincipalLookupService testGetUserPrincipalLookupService() {
                        return super.getUserPrincipalLookupService();
                    }
                }.testGetUserPrincipalLookupService();
                throw new AssertionError("Expected UnsupportedOperationException not thrown");
            }
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
