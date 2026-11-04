/* @test    @summary Test descendants method of java.lang.Process    @run main ProcessWitupTest */

import java.lang.Process;
import java.lang.ProcessHandle;
import java.util.stream.Stream;

public class ProcessWitupTest {

    public static void main(String[] args) {
        testDescendantsReturnsStream();
    }

    public static void testDescendantsReturnsStream() {
        Process process = new DummyProcess();
        Stream<ProcessHandle> stream = process.descendants();
        if (stream == null) {
            throw new AssertionError("descendants() returned null");
        }
        // Consume stream to ensure no exceptions and verify behavior
        try {
            stream.forEach(ph -> {
                if (ph == null) {
                    throw new AssertionError("ProcessHandle in stream is null");
                }
            });
        } catch (Exception e) {
            throw new AssertionError("Exception while processing descendants stream", e);
        }
        System.out.println("testDescendantsReturnsStream passed.");
    }

    static class DummyProcess extends Process {
        @Override
        public OutputStream getOutputStream() { return null; }
        @Override
        public InputStream getInputStream() { return null; }
        @Override
        public InputStream getErrorStream() { return null; }
        @Override
        public int waitFor() { return 0; }
        @Override
        public int exitValue() { return 0; }
        @Override
        public void destroy() {}
        @Override
        public ProcessHandle toHandle() {
            return new DummyProcessHandle();
        }
    }

    static class DummyProcessHandle extends ProcessHandle {
        @Override
        public long pid() { return 1234; }
        @Override
        public Stream<ProcessHandle> descendants() {
            return Stream.of(new DummyProcessHandle());
        }
        @Override
        public Stream<ProcessHandle> children() { return Stream.empty(); }
        @Override
        public boolean isAlive() { return true; }
        @Override
        public void destroy() {}
        @Override
        public void destroyForcibly() {}
        @Override
        public boolean supportsNormalTermination() { return true; }
        @Override
        public boolean supportsForce() { return true; }
        @Override
        public java.util.Optional<ProcessHandle> parent() { return java.util.Optional.empty(); }
        @Override
        public java.util.Optional<ProcessHandle.Info> info() { return java.util.Optional.empty(); }
    }
}
