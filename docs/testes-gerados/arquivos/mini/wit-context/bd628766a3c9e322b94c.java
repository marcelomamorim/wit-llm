/*
 * @test
 * @summary Test jdk.internal.jrtfs.JrtFileSystemProvider.getFileSystem(URI) for valid and invalid URIs and permission checks
 * @run main JrtFileSystemProviderWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemProviderWitupTest {

    public static void main(String[] args) throws Exception {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();

        // Test valid URI with scheme "jrt" and path "/"
        URI validUri = new URI("jrt:/");
        try {
            FileSystem fs = provider.getFileSystem(validUri);
            if (fs == null) {
                throw new RuntimeException("getFileSystem returned null for valid URI");
            }
        } catch (SecurityException se) {
            // SecurityManager may prevent access; acceptable
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Unexpected IllegalArgumentException for valid URI", iae);
        }

        // Test URI with wrong scheme
        URI wrongSchemeUri = new URI("file:/");
        try {
            provider.getFileSystem(wrongSchemeUri);
            throw new RuntimeException("Expected IllegalArgumentException for wrong scheme URI");
        } catch (IllegalArgumentException expected) {
            // Expected
        } catch (SecurityException se) {
            // SecurityManager may prevent access before validation; acceptable
        }

        // Test URI with authority component
        URI authorityUri = new URI("jrt://authority/");
        try {
            provider.getFileSystem(authorityUri);
            throw new RuntimeException("Expected IllegalArgumentException for URI with authority");
        } catch (IllegalArgumentException expected) {
            // Expected
        } catch (SecurityException se) {
            // SecurityManager may prevent access before validation; acceptable
        }

        // Test URI with path not equal to "/"
        URI wrongPathUri = new URI("jrt:/notroot");
        try {
            provider.getFileSystem(wrongPathUri);
            throw new RuntimeException("Expected IllegalArgumentException for URI with wrong path");
        } catch (IllegalArgumentException expected) {
            // Expected
        } catch (SecurityException se) {
            // SecurityManager may prevent access before validation; acceptable
        }

        // Test URI with query component
        URI queryUri = new URI("jrt:/?query");
        try {
            provider.getFileSystem(queryUri);
            throw new RuntimeException("Expected IllegalArgumentException for URI with query");
        } catch (IllegalArgumentException expected) {
            // Expected
        } catch (SecurityException se) {
            // SecurityManager may prevent access before validation; acceptable
        }

        // Test URI with fragment component
        URI fragmentUri = new URI("jrt:/#fragment");
        try {
            provider.getFileSystem(fragmentUri);
            throw new RuntimeException("Expected IllegalArgumentException for URI with fragment");
        } catch (IllegalArgumentException expected) {
            // Expected
        } catch (SecurityException se) {
            // SecurityManager may prevent access before validation; acceptable
        }

        System.out.println("All tests passed");
    }
}
