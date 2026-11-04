/* @test    @summary Testa o construtor BufferedWriter(Writer out) com entrada válida e inválida    @run main BufferedWriterWitupTest */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

public class BufferedWriterWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidWriter();
        testConstructorWithNullWriter();
    }

    private static void testConstructorWithValidWriter() {
        StringWriter stringWriter = new StringWriter();
        try {
            BufferedWriter bw = new BufferedWriter(stringWriter);
            if (bw == null) {
                throw new AssertionError("BufferedWriter should not be null with valid Writer");
            }
            bw.close();
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in valid constructor test", e);
        }
    }

    private static void testConstructorWithNullWriter() {
        try {
            BufferedWriter bw = new BufferedWriter((java.io.Writer) null);
            if (bw == null) {
                throw new AssertionError("BufferedWriter should not be null even with null Writer");
            }
            bw.close();
        } catch (NullPointerException e) {
            // Expected, as passing null to BufferedWriter constructor throws NullPointerException
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in null Writer test", e);
        }
    }
}
