/*
 * @test
 * @summary Verify ProcessBuilder.Redirect.to(File) type, file, toString and null handling
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        File f = new File("witup-out.txt");
        ProcessBuilder.Redirect r = ProcessBuilder.Redirect.to(f);
        if (r.type() != ProcessBuilder.Redirect.Type.WRITE) {
            throw new AssertionError("unexpected type: " + r.type());
        }
        if (!f.equals(r.file())) {
            throw new AssertionError("unexpected file: " + r.file());
        }
        String expected = "redirect to write to file \"" + f + "\"";
        if (!expected.equals(r.toString())) {
            throw new AssertionError("unexpected toString: " + r.toString());
        }
        try {
            ProcessBuilder.Redirect.to(null);
            throw new AssertionError("NullPointerException expected for null file");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
