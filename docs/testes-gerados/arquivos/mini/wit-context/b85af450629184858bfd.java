/*
 * @test
 * @summary Test Process.children() throws UnsupportedOperationException as per current implementation
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
            public java.io.OutputStream getOutputStream() {
                throw new UnsupportedOperationException();
            }

            @Override
            public java.io.InputStream getInputStream() {
                throw new UnsupportedOperationException();
            }

            @Override
            public java.io.InputStream getErrorStream() {
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

        boolean threw = false;
        try {
            Stream<ProcessHandle> children = p.children();
            // If no exception, fail
            System.err.println("ERROR: Expected UnsupportedOperationException not thrown");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            threw = true;
        } catch (Throwable t) {
            System.err.println("ERROR: Unexpected exception type: " + t);
            System.exit(1);
        }

        if (!threw) {
            System.err.println("ERROR: UnsupportedOperationException was not thrown");
            System.exit(1);
        }

        System.out.println("Test passed: UnsupportedOperationException thrown as expected from children()");
    }
}
