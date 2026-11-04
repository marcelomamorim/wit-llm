/* @test    @summary Test for ProcessBuilder.from(File) method handling null input    @run main ProcessBuilderWitupTest */

import java.io.File;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testFromNullFile();
    }

    public static void testFromNullFile() {
        try {
            java.lang.ProcessBuilder.Redirect result = java.lang.ProcessBuilder.from(null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Exceção esperada, teste passa
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
