/*
 * @test
 * @summary Checks Process.descendants() delegation to toHandle() and its default UnsupportedOperationException
 * @run main ProcessWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

public class ProcessWitupTest {

    static class StubProcess extends Process {
        private final boolean overrideHandle;

        StubProcess(boolean overrideHandle) {
            this.overrideHandle = overrideHandle;
        }

        @Override
        public OutputStream getOutputStream() {
            return new ByteArrayOutputStream();
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(new byte[0]);
        }

        @Override
        public InputStream getErrorStream() {
            return new ByteArrayInputStream(new byte[0]);
        }

        @Override
        public int waitFor() {
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
            if (overrideHandle) {
                return ProcessHandle.current();
            }
            return super.toHandle();
        }
    }

    public static void main(String[] args) {
        testDefaultToHandleThrowsFromDescendants();
        testDescendantsDelegatesToOverriddenToHandle();
    }

    static void testDefaultToHandleThrowsFromDescendants() {
        StubProcess p = new StubProcess(false);
        boolean thrown = false;
        try {
            Stream<ProcessHandle> s = p.descendants();
            if (s == null) {
                throw new RuntimeException("descendants() returned null");
            }
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from descendants()");
        }
    }

    static void testDescendantsDelegatesToOverriddenToHandle() {
        StubProcess p = new StubProcess(true);
        Stream<ProcessHandle> s = p.descendants();
        if (s == null) {
            throw new RuntimeException("descendants() returned null when toHandle() is overridden");
        }
    }
}
