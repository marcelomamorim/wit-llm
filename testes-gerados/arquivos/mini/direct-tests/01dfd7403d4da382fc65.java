/*
 * @test
 * @summary Test Process.info() method delegates to ProcessHandle.info()
 * @run main ProcessWitupTest
 */

public class ProcessWitupTest {
    public static void main(String[] args) throws Exception {
        Process p = new Process() {
            @Override
            public ProcessHandle toHandle() {
                return new ProcessHandle() {
                    @Override
                    public Info info() {
                        return new Info() {
                            @Override
                            public java.util.Optional<String> command() {
                                return java.util.Optional.of("testCommand");
                            }
                            @Override
                            public java.util.Optional<String[]> arguments() {
                                return java.util.Optional.empty();
                            }
                            @Override
                            public java.util.Optional<java.time.Instant> startInstant() {
                                return java.util.Optional.empty();
                            }
                            @Override
                            public java.util.Optional<java.time.Duration> totalCpuDuration() {
                                return java.util.Optional.empty();
                            }
                            @Override
                            public java.util.Optional<String> user() {
                                return java.util.Optional.empty();
                            }
                        };
                    }
                    // Other ProcessHandle methods can throw UnsupportedOperationException
                    @Override
                    public long pid() { throw new UnsupportedOperationException(); }
                    @Override
                    public java.util.Optional<ProcessHandle> parent() { throw new UnsupportedOperationException(); }
                    @Override
                    public java.util.stream.Stream<ProcessHandle> children() { throw new UnsupportedOperationException(); }
                    @Override
                    public java.util.stream.Stream<ProcessHandle> descendants() { throw new UnsupportedOperationException(); }
                    @Override
                    public boolean isAlive() { throw new UnsupportedOperationException(); }
                    @Override
                    public boolean supportsNormalTermination() { throw new UnsupportedOperationException(); }
                    @Override
                    public boolean destroy() { throw new UnsupportedOperationException(); }
                    @Override
                    public boolean destroyForcibly() { throw new UnsupportedOperationException(); }
                    @Override
                    public java.util.concurrent.CompletableFuture<ProcessHandle> onExit() { throw new UnsupportedOperationException(); }
                    @Override
                    public int compareTo(ProcessHandle other) { throw new UnsupportedOperationException(); }
                };
            }
        };

        java.lang.ProcessHandle.Info info = p.info();
        if (!info.command().isPresent() || !"testCommand".equals(info.command().get())) {
            throw new AssertionError("Process.info() did not delegate correctly to ProcessHandle.info()");
        }
    }
}
