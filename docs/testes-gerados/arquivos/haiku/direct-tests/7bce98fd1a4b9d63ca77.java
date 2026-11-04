/*
 * @test
 * @summary Verify JrtFileSystemProvider.delete rejects a path that does not belong to the jrt provider and does not succeed silently
 * @modules java.base/jdk.internal.jrtfs
 * @run main JrtFileSystemProviderWitupTest
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemProviderWitupTest {
    public static void main(String[] args) throws Exception {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();
        if (!"jrt".equals(provider.getScheme())) {
            throw new AssertionError("unexpected scheme: " + provider.getScheme());
        }
        Path foreign = Paths.get("nonexistent-file-for-witup.tmp");
        boolean failed = false;
        try {
            provider.delete(foreign);
        } catch (IOException | RuntimeException e) {
            failed = true;
        }
        if (!failed) {
            throw new AssertionError("delete on a non-jrt path must not succeed silently");
        }
    }
}
