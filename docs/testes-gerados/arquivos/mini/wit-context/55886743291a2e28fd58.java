/*
 * @test
 * @summary Test java.lang.Process.descendants() method for expected UnsupportedOperationException
 * @run main ProcessWitupTest
 */

import java.lang.Process;
import java.lang.ProcessHandle;
import java.util.stream.Stream;

public class ProcessWitupTest {
    public static void main(String[] args) {
        Process p = new Process() {
            @Override
            public ProcessHandle toHandle() {
                throw new UnsupportedOperationException();
            }

            @Override
            public OutputStream getOutputStream() {
                throw new UnsupportedOperationException();
            }

            @Override
            public InputStream getInputStream() {
                throw new UnsupportedOperationException();
            }

            @Override
            public InputStream getErrorStream() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int waitFor() throws InterruptedException {
                throw new UnsupportedOperationException();
            }

            @Override
            public int exitValue() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void destroy() {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            Stream<ProcessHandle> descendants = p.descendants();
            descendants.count(); // force terminal operation
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from descendants()");
        }
    }
}
