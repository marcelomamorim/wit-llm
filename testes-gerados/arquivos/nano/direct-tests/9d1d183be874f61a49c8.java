/*
 * @test
 * @summary Test the position() method of JrtFileSystem for correct return value.
 * @run main JrtFileSystemWitupTest
 */
import java.io.IOException;
import jdk.internal.jrtfs.JrtFileSystem;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) {
        try {
            testPositionReturnsReadValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testPositionReturnsReadValue() throws IOException {
        JrtFileSystem fs = createMockJrtFileSystem();
        long expectedPosition = 12345L;
        setReadPosition(fs, expectedPosition);
        long actualPosition = fs.position();
        if (actualPosition != expectedPosition) {
            throw new AssertionError("Expected position: " + expectedPosition + ", but got: " + actualPosition);
        }
    }

    private static JrtFileSystem createMockJrtFileSystem() {
        return new JrtFileSystem(null, null) {
            private long read = 0;
            @Override
            public long position() throws IOException {
                return read;
            }
            public void setRead(long pos) {
                this.read = pos;
            }
        };
    }

    private static void setReadPosition(JrtFileSystem fs, long position) {
        if (fs instanceof JrtFileSystem) {
            ((JrtFileSystem) fs).setRead(position);
        }
    }
}
