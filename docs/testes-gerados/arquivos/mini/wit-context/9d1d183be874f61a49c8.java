/*
 * @test
 * @summary Test jdk.internal.jrtfs.JrtFileSystem.position() method behavior
 * @run main JrtFileSystemWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtFileSystem;
import java.util.Collections;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a minimal JrtFileSystem instance for testing
        JrtFileSystemProviderStub provider = new JrtFileSystemProviderStub();
        JrtFileSystem fs = new JrtFileSystem(provider, Collections.emptyMap());

        // Test position() returns expected value
        long pos = fs.position();
        if (pos != 0L) {
            throw new AssertionError("Expected position() to return 0 but got " + pos);
        }

        // Test that position(long) throws UnsupportedOperationException as per WIT expath
        boolean threw = false;
        try {
            fs.position(10L);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected position(long) to throw UnsupportedOperationException");
        }

        System.out.println("Test passed.");
    }

    // Minimal stub for JrtFileSystemProvider to allow JrtFileSystem construction
    static class JrtFileSystemProviderStub extends jdk.internal.jrtfs.JrtFileSystemProvider {
        JrtFileSystemProviderStub() {
            super();
        }
    }
}
