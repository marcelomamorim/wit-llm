/*
 * @test
 * @summary Verify that Process.pid() delegates to toHandle().pid() and returns a stable value
 * @run main ProcessWitupTest
 */

import java.io.InputStream;
import java.io.OutputStream;

public class ProcessWitupTest {

    static final class StubProcess extends Process {
        @Override
        public OutputStream getOutputStream() {
            return null;
        }

        @Override
        public InputStream getInputStream() {
            return null;
        }

        @Override
        public InputStream getErrorStream() {
            return null;
        }

        @Override
        public int waitFor() throws InterruptedException {
            return 0;
        }

        @Override
        public int exitValue() {
            return 0;
        }

        @Override
        public void destroy() {
        }

        @Override
        public ProcessHandle toHandle() {
            return ProcessHandle.current();
        }
    }

    public static void main(String[] args) throws Exception {
        Process p = new StubProcess();
        long expected = ProcessHandle.current().pid();

        long first = p.pid();
        if (first != expected) {
            throw new RuntimeException("pid() expected " + expected + " but was " + first);
        }

        long second = p.pid();
        if (second != first) {
            throw new RuntimeException("pid() not stable: " + first + " vs " + second);
        }

        if (first <= 0) {
            throw new RuntimeException("pid() should be positive but was " + first);
        }
    }
}
