/* @test    @summary Test getUnallocatedSpace throws UnsupportedOperationException    @run main JrtFileStoreWitupTest */

import java.io.IOException;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) {
        testGetUnallocatedSpaceThrowsException();
    }

    public static void testGetUnallocatedSpaceThrowsException() {
        class DummyJrtPath {
            public java.nio.file.FileSystem getFileSystem() {
                return new DummyFileSystem();
            }
        }
        class DummyFileSystem extends java.nio.file.FileSystem {
            @Override
            public boolean isReadOnly() {
                return false;
            }
            @Override
            public String toString() {
                return "dummyJrtfs";
            }
            // Other methods are not used in this test
            @Override public void close() throws IOException {}
            @Override public boolean isOpen() { return true; }
            @Override public boolean isReadOnly() { return false; }
            @Override public java.util.Iterator<java.nio.file.Path> getRootDirectories() { return null; }
            @Override public java.util.List<java.nio.file.Path> getFileStores() { return null; }
            @Override public java.nio.file.Path getPath(String first, String... more) { return null; }
            @Override public java.nio.file.PathMatcher getPathMatcher(String syntaxAndPattern) { return null; }
            @Override public java.nio.file.FileSystemProvider provider() { return null; }
            @Override public java.util.Set<String> supportedFileAttributeViews() { return null; }
        }
        class DummyJrtFileStore extends jdk.internal.jrtfs.JrtFileStore {
            DummyJrtFileStore(JdkPath path) { super(path); }
        }
        class JdkPath {
            public java.nio.file.FileSystem getFileSystem() {
                return new DummyFileSystem();
            }
        }
        JdkPath path = new JdkPath();
        jdk.internal.jrtfs.JrtFileStore store = new jdk.internal.jrtfs.JrtFileStore(path) {
            @Override
            public long getUnallocatedSpace() throws IOException {
                throw new UnsupportedOperationException("getUnallocatedSpace");
            }
        };
        try {
            store.getUnallocatedSpace();
            System.out.println("Test failed: exception not thrown");
        } catch (UnsupportedOperationException e) {
            assert e.getMessage().equals("getUnallocatedSpace") : "Unexpected message: " + e.getMessage();
            System.out.println("Test passed: UnsupportedOperationException thrown as expected");
        } catch (Exception e) {
            System.out.println("Test failed: unexpected exception: " + e);
        }
    }
}
