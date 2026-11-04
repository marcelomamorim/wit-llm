/* @test    @summary Test ProcessBuilder.appendTo method for correct Redirect creation    @run main ProcessBuilderWitupTest */

import java.io.File;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testAppendToWithValidFile();
        testAppendToWithNullFile();
    }

    static void testAppendToWithValidFile() {
        File file = new File("testfile.txt");
        java.lang.ProcessBuilder.Redirect redirect = java.lang.ProcessBuilder.appendTo(file);
        assert redirect != null : "Redirect should not be null";
        assert redirect.type() == java.lang.ProcessBuilder.Redirect.Type.APPEND : "Type should be APPEND";
        assert redirect.file().equals(file) : "File should match the input file";
        assert redirect.toString().contains("append") : "toString should contain 'append'";
        assert redirect.append() : "append() should return true";
    }

    static void testAppendToWithNullFile() {
        try {
            java.lang.ProcessBuilder.appendTo(null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
