/*
 * @test
 * @summary Checks that JrtFileSystemProvider.getFileSystem(URI) accepts a valid jrt:/ URI and rejects URIs with an invalid scheme, authority, path, query or fragment
 * @modules java.base/jdk.internal.jrtfs
 * @run main JrtFileSystemProviderWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemProviderWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();

        FileSystem fs = provider.getFileSystem(URI.create("jrt:/"));
        if (fs == null) {
            failures++;
            System.err.println("getFileSystem(jrt:/) returned null");
        }

        FileSystem fsUpper = provider.getFileSystem(URI.create("JRT:/"));
        if (fsUpper == null) {
            failures++;
            System.err.println("getFileSystem(JRT:/) returned null");
        }

        expectIllegalArgument(provider, "file:/");
        expectIllegalArgument(provider, "jrt://host/");
        expectIllegalArgument(provider, "jrt:/foo");
        expectIllegalArgument(provider, "jrt:/?q=1");
        expectIllegalArgument(provider, "jrt:/#frag");

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }

    private static void expectIllegalArgument(JrtFileSystemProvider provider, String uriString) {
        try {
            provider.getFileSystem(URI.create(uriString));
            failures++;
            System.err.println("Expected IllegalArgumentException for " + uriString);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
