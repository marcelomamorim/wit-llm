/*
 * @test
 * @summary Test that JrtFileSystem.getUserPrincipalLookupService() throws UnsupportedOperationException
 * @run main JrtFileSystemWitupTest
 */

import jdk.internal.jrtfs.JrtFileSystem;
import java.nio.file.attribute.UserPrincipalLookupService;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) throws Exception {
        JrtFileSystem fs = new JrtFileSystem(null, null);
        try {
            UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
