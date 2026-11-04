/*
 * @test
 * @summary Test jdk.internal.jrtfs.JrtFileSystemProvider.getFileSystem(URI) method
 * @run main JrtFileSystemProviderWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemProviderWitupTest {
    public static void main(String[] args) throws Exception {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();

        // Test valid URI with scheme 'jrt' and path '/'
        URI validUri = new URI("jrt:/");
        FileSystem fs = null;
        try {
            fs = provider.getFileSystem(validUri);
            if (fs == null) {
                throw new RuntimeException("getFileSystem returned null for valid URI");
            }
        } catch (SecurityException se) {
            // SecurityManager may prevent access; test passes if exception thrown
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Unexpected IllegalArgumentException for valid URI", iae);
        }

        // Test URI with wrong scheme
        try {
            URI wrongScheme = new URI("file:/");
            provider.getFileSystem(wrongScheme);
            throw new RuntimeException("Expected IllegalArgumentException for wrong scheme URI");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (SecurityException se) {
            // SecurityManager may throw first
        }

        // Test URI with authority component
        try {
            URI withAuthority = new URI("jrt://authority/");
            provider.getFileSystem(withAuthority);
            throw new RuntimeException("Expected IllegalArgumentException for URI with authority");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (SecurityException se) {
            // SecurityManager may throw first
        }

        // Test URI with path not equal to "/"
        try {
            URI wrongPath = new URI("jrt:/notroot");
            provider.getFileSystem(wrongPath);
            throw new RuntimeException("Expected IllegalArgumentException for URI with wrong path");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (SecurityException se) {
            // SecurityManager may throw first
        }

        // Test URI with query component
        try {
            URI withQuery = new URI("jrt:/?query");
            provider.getFileSystem(withQuery);
            throw new RuntimeException("Expected IllegalArgumentException for URI with query");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (SecurityException se) {
            // SecurityManager may throw first
        }

        // Test URI with fragment component
        try {
            URI withFragment = new URI("jrt:/#fragment");
            provider.getFileSystem(withFragment);
            throw new RuntimeException("Expected IllegalArgumentException for URI with fragment");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (SecurityException se) {
            // SecurityManager may throw first
        }
    }
}
