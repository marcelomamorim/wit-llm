/*
 * @test
 * @summary Test Process.descendants() method delegates to ProcessHandle.descendants()
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
                    public Stream<ProcessHandle> descendants() {
                        return Stream.of(this);
                    }

                    // Stub implementations for abstract methods
                    @Override
                    public long pid() { return 0; }
                    @Override
                    public ProcessHandle.Info info() { return null; }
                    @Override
                    public boolean isAlive() { return true; }
                    @Override
                    public boolean supportsNormalTermination() { return false; }
                    @Override
                    public boolean destroy() { return false; }
                    @Override
                    public boolean destroyForcibly() { return false; }
                    @Override
                    public ProcessHandle parent() { return null; }
                    @Override
                    public Stream<ProcessHandle> children() { return Stream.empty(); }
                    @Override
                    public Stream<ProcessHandle> descendants() { return Stream.of(this); }
                    @Override
                    public CompletableFuture<ProcessHandle> onExit() { return null; }
                };
            }

            // Other abstract methods of Process can be left unimplemented for this test
        };

        Stream<ProcessHandle> descendantsStream = p.descendants();
        if (descendantsStream == null) {
            throw new AssertionError("descendants() returned null");
        }

        // Check that the stream contains exactly one element, the stub ProcessHandle
        long count = descendantsStream.count();
        if (count != 1) {
            throw new AssertionError("descendants() stream size expected 1 but was " + count);
        }

        System.out.println("Test passed: descendants() returns expected stream");
    }
}
