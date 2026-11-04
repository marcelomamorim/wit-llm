/*
 * @test
 * @summary Witup-derived checks for File(String pathname): null throws NullPointerException, and valid inputs are accepted and normalized observably.
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {

    public static void main(String[] args) {
        testNullPathnameThrowsNPE();
        testEmptyPathnameIsAccepted();
        testNameIsObservableAfterNormalization();
        testSimpleRelativePathIsAccepted();
    }

    static void testNullPathnameThrowsNPE() {
        try {
            new File((String) null);
            throw new RuntimeException("Expected NullPointerException for null pathname");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyPathnameIsAccepted() {
        File f = new File("");
        if (!f.getPath().equals("")) {
            throw new RuntimeException("Empty pathname should yield empty path, got: " + f.getPath());
        }
    }

    static void testNameIsObservableAfterNormalization() {
        File f = new File("dir/x.txt");
        if (!f.getName().equals("x.txt")) {
            throw new RuntimeException("Unexpected name: " + f.getName());
        }
    }

    static void testSimpleRelativePathIsAccepted() {
        File f = new File("relative");
        if (!f.getName().equals("relative")) {
            throw new RuntimeException("Unexpected name: " + f.getName());
        }
    }
}
