/*
 * @test
 * @summary Test Process.pid() throws UnsupportedOperationException as per current implementation
 * @run main ProcessWitupTest
 */

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

            @Override
            public Process destroyForcibly() {
                throw new UnsupportedOperationException();
            }

            @Override
            public java.util.concurrent.CompletableFuture<Process> onExit() {
                throw new UnsupportedOperationException();
            }
        };

        boolean threw = false;
        try {
            p.pid();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException from pid()");
        }
    }
}
