/*
 * @test
 * @summary Test Process.children() method delegates to ProcessHandle.children()
 * @run main ProcessWitupTest
 */

import java.lang.Process;
import java.lang.ProcessHandle;
import java.util.stream.Stream;

public class ProcessWitupTest {
    public static void main(String[] args) throws Exception {
        Process p = new Process() {
            @Override
            public ProcessHandle toHandle() {
                return new ProcessHandle() {
                    @Override
                    public Stream<ProcessHandle> children() {
                        return Stream.of(this);
                    }

                    // Stub implementations for abstract methods
                    @Override
                    public long pid() { return 0; }
                    @Override
                    public ProcessHandle parent() { return null; }
                    @Override
                    public Stream<ProcessHandle> descendants() { return Stream.empty(); }
                    @Override
                    public boolean supportsNormalTermination() { return false; }
                    @Override
                    public boolean isAlive() { return true; }
                    @Override
                    public ProcessHandle.Info info() { return null; }
                    @Override
                    public boolean destroy() { return false; }
                    @Override
                    public boolean destroyForcibly() { return false; }
                    @Override
                    public CompletableFuture<ProcessHandle> onExit() { return null; }
                };
            }

            // Stub implementations for abstract methods of Process
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
            public Process destroyForcibly() { return this; }
            @Override
            public boolean isAlive() { return true; }
            @Override
            public ProcessHandle toHandle() { return null; }
        };

        Stream<ProcessHandle> children = p.children();
        if (children == null) {
            throw new AssertionError("children() returned null");
        }
        // Check that the stream contains exactly one element (the stub ProcessHandle)
        long count = children.count();
        if (count != 1) {
            throw new AssertionError("Expected 1 child ProcessHandle, got " + count);
        }
    }
}
