/*
 * @test
 * @summary Test JrtFileSystem.close() behavior including UnsupportedOperationException when not closable
 * @run main JrtFileSystemWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtFileSystem;
import java.util.Collections;
import java.util.Map;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        testCloseThrowsIfNotClosable();
        testCloseCallsCleanupIfClosable();
    }

    private static void testCloseThrowsIfNotClosable() {
        // Create a JrtFileSystem with isClosable = false by passing null env
        JrtFileSystemProviderStub provider = new JrtFileSystemProviderStub();
        JrtFileSystem fs = new JrtFileSystem(provider, null);
        if (fs.isOpen() == false) {
            throw new AssertionError("FileSystem should be open initially");
        }
        try {
            fs.close();
            throw new AssertionError("Expected UnsupportedOperationException when close called on non-closable fs");
        } catch (UnsupportedOperationException expected) {
            // expected
        } catch (IOException ioe) {
            throw new AssertionError("Unexpected IOException thrown: " + ioe);
        }
    }

    private static void testCloseCallsCleanupIfClosable() throws IOException {
        // Create a JrtFileSystem with isClosable = true by passing non-null env
        JrtFileSystemProviderStub provider = new JrtFileSystemProviderStub();
        JrtFileSystem fs = new JrtFileSystem(provider, Collections.emptyMap());
        if (!fs.isOpen()) {
            throw new AssertionError("FileSystem should be open initially");
        }
        fs.close();
        if (fs.isOpen()) {
            throw new AssertionError("FileSystem should be closed after close()");
        }
        if (!provider.cleanupCalled) {
            throw new AssertionError("cleanup() should be called on close()");
        }
    }

    // Minimal stub to allow construction and observe cleanup call
    static class JrtFileSystemProviderStub extends jdk.internal.jrtfs.JrtFileSystemProvider {
        boolean cleanupCalled = false;

        @Override
        void cleanup() {
            cleanupCalled = true;
            super.cleanup(); // call super to keep behavior if any
        }
    }
}
