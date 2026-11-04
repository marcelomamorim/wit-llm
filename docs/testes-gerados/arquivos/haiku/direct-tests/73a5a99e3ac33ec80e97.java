/*
 * @test
 * @summary Verifies ProcessBuilder.Redirect.appendTo type, file, toString and null handling
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        File f = new File("witup_append_target.log");
        ProcessBuilder.Redirect r = ProcessBuilder.Redirect.appendTo(f);

        check(r.type() == ProcessBuilder.Redirect.Type.APPEND, "type must be APPEND");
        check(f.equals(r.file()), "file() must return the same file");

        String expected = "redirect to append to file \"" + f + "\"";
        check(expected.equals(r.toString()), "unexpected toString: " + r.toString());

        ProcessBuilder pb = new ProcessBuilder("true");
        pb.redirectOutput(r);
        ProcessBuilder.Redirect out = pb.redirectOutput();
        check(out.type() == ProcessBuilder.Redirect.Type.APPEND, "redirectOutput type must be APPEND");
        check(f.equals(out.file()), "redirectOutput file mismatch");

        boolean thrown = false;
        try {
            ProcessBuilder.Redirect.appendTo(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "appendTo(null) must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
