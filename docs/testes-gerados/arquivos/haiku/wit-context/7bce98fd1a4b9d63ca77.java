/*
 * @test
 * @summary Verify that JrtFileSystemProvider.delete(null) throws NullPointerException
 * @modules java.base/jdk.internal.jrtfs
 * @run main JrtFileSystemProviderWitupTest
 */

import java.nio.file.Path;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemProviderWitupTest {
    public static void main(String[] args) throws Exception {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();
        boolean thrown = false;
        try {
            provider.delete((Path) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null path");
        }
    }
}
