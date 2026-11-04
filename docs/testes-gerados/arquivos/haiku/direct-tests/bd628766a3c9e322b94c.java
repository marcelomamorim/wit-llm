/*
 * @test
 * @summary Verifies that JrtFileSystemProvider.getFileSystem rejects URIs that fail checkUri with the expected IllegalArgumentException messages
 * @modules java.base/jdk.internal.jrtfs
 * @run main JrtFileSystemProviderWitupTest
 */

import java.net.URI;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemProviderWitupTest {

    public static void main(String[] args) throws Exception {
        JrtFileSystemProvider p = new JrtFileSystemProvider();

        expectIAE(p, "file:/", "URI does not match this provider");
        expectIAE(p, "jrt://host/", "Authority component present");
        expectIAE(p, "jrt:/a", "Path component should be '/'");
        expectIAE(p, "JRT:/a", "Path component should be '/'");
        expectIAE(p, "jrt:/?q=1", "Query component present");
        expectIAE(p, "jrt:/#frag", "Fragment component present");
        expectIAE(p, "jrt:opaque", "Path component is undefined");
    }

    static void expectIAE(JrtFileSystemProvider p, String uri, String expectedMsg) {
        try {
            p.getFileSystem(URI.create(uri));
        } catch (IllegalArgumentException e) {
            if (!expectedMsg.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for " + uri + ": " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("No IllegalArgumentException for " + uri);
    }
}
