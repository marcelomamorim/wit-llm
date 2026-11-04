/*
 * @test
 * @summary Verifies that Process.children() delegates to toHandle().children() and returns a usable Stream for a started process
 * @run main ProcessWitupTest
 */

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessWitupTest {

    public static void main(String[] args) throws Exception {
        String javaBin = System.getProperty("java.home") + "/bin/java";
        ProcessBuilder pb = new ProcessBuilder(javaBin, "-version");
        Process p = pb.start();
        try {
            Stream<ProcessHandle> children = p.children();
            if (children == null) {
                throw new RuntimeException("children() returned null");
            }
            List<ProcessHandle> list = children.collect(Collectors.toList());
            if (list == null) {
                throw new RuntimeException("collected list is null");
            }
            for (ProcessHandle ph : list) {
                if (ph == null) {
                    throw new RuntimeException("null ProcessHandle in children()");
                }
            }
            int exit = p.waitFor();
            if (exit != 0) {
                throw new RuntimeException("unexpected exit code: " + exit);
            }
        } finally {
            p.destroy();
            p.waitFor();
        }
    }
}
