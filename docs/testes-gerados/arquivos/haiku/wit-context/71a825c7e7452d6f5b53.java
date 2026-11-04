/*
 * @test
 * @summary Witup-derived checks for Runtime.exec(String[]) argument handling and IOException on missing program
 * @run main RuntimeWitupTest
 */

import java.io.IOException;

public class RuntimeWitupTest {
    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.getRuntime();

        String[] nullCmd = null;
        boolean npe = false;
        try {
            rt.exec(nullCmd);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("exec(null String[]) must throw NullPointerException");
        }

        String[] missing = new String[] { "/nonexistent/witup-cmd-xyz-4711" };
        boolean io = false;
        try {
            rt.exec(missing);
        } catch (IOException e) {
            io = true;
        }
        if (!io) {
            throw new AssertionError("exec with a nonexistent program must throw IOException");
        }
    }
}
