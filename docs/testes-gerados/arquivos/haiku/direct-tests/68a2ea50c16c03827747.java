/*
 * @test
 * @summary Basic observable behavior of Runtime.addShutdownHook: registration returns normally and a registered hook runs at JVM exit
 * @run main RuntimeWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

public class RuntimeWitupTest {

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && "child".equals(args[0])) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("HOOK_RAN")));
            return;
        }

        Runtime rt = Runtime.getRuntime();
        if (rt != Runtime.getRuntime()) {
            throw new AssertionError("getRuntime() must return the same instance");
        }

        rt.addShutdownHook(new Thread(() -> { }));

        Thread unstarted = new Thread(() -> { });
        rt.addShutdownHook(unstarted);

        String cp = System.getProperty("test.classes", System.getProperty("java.class.path"));
        String javaExe = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        ProcessBuilder pb = new ProcessBuilder(javaExe, "-cp", cp, "RuntimeWitupTest", "child");
        pb.redirectErrorStream(true);
        Process p = pb.start();
        String out = readAll(p.getInputStream());
        int exit = p.waitFor();

        if (exit != 0) {
            throw new AssertionError("child exited with " + exit + ": " + out);
        }
        if (!out.contains("HOOK_RAN")) {
            throw new AssertionError("registered shutdown hook did not run; output: " + out);
        }
    }

    private static String readAll(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int n;
        while ((n = in.read(buf)) != -1) {
            bos.write(buf, 0, n);
        }
        return new String(bos.toByteArray(), "UTF-8");
    }
}
