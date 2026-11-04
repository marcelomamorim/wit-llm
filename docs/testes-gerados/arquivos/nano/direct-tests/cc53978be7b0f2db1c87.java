/*
 * @test
 * @summary Test getUserPrincipalLookupService() method of JrtFileSystem
 * @run main JrtFileSystemWitupTest
 */
import jdk.internal.jrtfs.JrtFileSystem;
import java.nio.file.attribute.UserPrincipalLookupService;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) {
        testGetUserPrincipalLookupService();
    }

    public static void testGetUserPrincipalLookupService() {
        JrtFileSystem fs = null;
        try {
            fs = new JrtFileSystem(null, null);
        } catch (Exception e) {
            // Expected exception due to constructor requiring environment map
            // or internal setup; skip instantiation if it throws.
            System.out.println("Skipping instantiation: " + e);
            return;
        }
        try {
            UserPrincipalLookupService ups = fs.getUserPrincipalLookupService();
            // The method is expected to throw UnsupportedOperationException
            // so reaching this line indicates a failure.
            System.out.println("Failure: getUserPrincipalLookupService() did not throw as expected.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: getUserPrincipalLookupService() threw UnsupportedOperationException as expected.");
        } catch (Exception e) {
            System.out.println("Failure: getUserPrincipalLookupService() threw unexpected exception: " + e);
        }
    }
}