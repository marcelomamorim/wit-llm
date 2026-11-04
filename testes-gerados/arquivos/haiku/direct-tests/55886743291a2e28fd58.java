/*
 * @test
 * @summary Verify Process.descendants() returns a usable stream for a started process and no descendants after exit
 * @run main ProcessWitupTest
 */

import java.io.File;
import java.util.stream.Stream;

public class ProcessWitupTest {
    public static void main(String[] args) throws Exception {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        ProcessBuilder pb = new ProcessBuilder(javaBin, "-version");
        pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
        pb.redirectError(ProcessBuilder.Redirect.DISCARD);
        Process p = pb.start();
        try {
            Stream<ProcessHandle> live = p.descendants();
            if (live == null) {
                throw new AssertionError("descendants() returned null");
            }
            live.count();

            int exit = p.waitFor();
            if (exit != 0) {
                throw new AssertionError("unexpected exit code: " + exit);
            }

            long after = p.descendants().count();
            if (after != 0) {
                throw new AssertionError("expected no descendants after exit, got: " + after);
            }
        } finally {
            p.destroy();
        }
    }
}
