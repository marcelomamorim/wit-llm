/*
 * @test
 * @summary Verify ProcessBuilder.Redirect.appendTo(File) null check and observable append redirect behavior
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        try {
            ProcessBuilder.Redirect.appendTo(null);
            throw new RuntimeException("expected NullPointerException for null file");
        } catch (NullPointerException expected) {
        }

        File target = new File("witup-append-target.txt");
        ProcessBuilder.Redirect redirect = ProcessBuilder.Redirect.appendTo(target);

        if (redirect.type() != ProcessBuilder.Redirect.Type.APPEND) {
            throw new RuntimeException("unexpected type: " + redirect.type());
        }
        if (!target.equals(redirect.file())) {
            throw new RuntimeException("unexpected file: " + redirect.file());
        }
        String text = redirect.toString();
        if (!text.startsWith("redirect to append to file")) {
            throw new RuntimeException("unexpected toString prefix: " + text);
        }
        if (!text.contains(target.toString())) {
            throw new RuntimeException("toString does not contain file path: " + text);
        }
    }
}
