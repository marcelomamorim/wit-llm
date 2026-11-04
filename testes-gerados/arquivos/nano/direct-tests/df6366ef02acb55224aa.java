/*
 * @test
 * @summary Test getAttribute method of JrtFileStore throws UnsupportedOperationException for any attribute
 * @run main JrtFileStoreWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtFileStore;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) {
        testGetAttributeThrows();
    }

    public static void testGetAttributeThrows() {
        JrtFileStore store = createJrtFileStore();
        String[] attributes = {"someAttribute", "anotherAttribute", "yetAnother"};
        for (String attr : attributes) {
            try {
                store.getAttribute(attr);
                throw new AssertionError("Expected UnsupportedOperationException for attribute: " + attr);
            } catch (UnsupportedOperationException e) {
                // Expected exception, test passes for this attribute
            } catch (IOException e) {
                throw new AssertionError("Unexpected IOException for attribute: " + attr, e);
            }
        }
    }

    private static JrtFileStore createJrtFileStore() {
        // Create a dummy JrtPath object with minimal implementation to instantiate JrtFileStore
        // Since JrtPath is not provided, we simulate with a minimal mock or dummy class
        class DummyJrtPath {
            public java.nio.file.FileSystem getFileSystem() {
                return new DummyFileSystem();
            }
        }
        class DummyFileSystem extends java.nio.file.FileSystem {
            @Override
            public String toString() {
                return "dummyJrtfs";
            }
            @Override
            public boolean isReadOnly() {
                return false;
            }
            @Override
            public java.nio.file.Path getPath(String first, String... more) {
                return null;
            }
            @Override
            public java.util.Iterator<java.nio.file.Path> getRootDirectories() {
                return null;
            }
            @Override
            public java.util.Iterator<java.nio.file.FileStore> getFileStores() {
                return null;
            }
            @Override
            public java.nio.file.PathMatcher getPathMatcher(String syntaxAndPattern) {
                return null;
            }
            @Override
            public java.nio.file.UserPrincipalLookupService getUserPrincipalLookupService() {
                return null;
            }
            @Override
            public java.nio.file.WatchService newWatchService() throws IOException {
                return null;
            }
        }
        class DummyJrtPath {
            public java.nio.file.FileSystem getFileSystem() {
                return new DummyFileSystem();
            }
        }
        DummyJrtPath dummyPath = new DummyJrtPath();
        return new JrtFileStore(dummyPath);
    }
}
