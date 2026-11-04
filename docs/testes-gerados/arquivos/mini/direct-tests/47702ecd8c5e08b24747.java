/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putSequence method
 * @run main sun.security.util.DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    static class DummyDerValue extends DerValue {
        DummyDerValue() throws IOException {
            super(DerValue.tag_Integer); // use any valid tag
        }
        @Override
        public void encode(DerOutputStream out) throws IOException {
            // encode a fixed integer value 5
            out.putInteger(5);
        }
    }

    public static void main(String[] args) throws Exception {
        DerOutputStream derOut = new DerOutputStream();

        // Test with empty array
        DerValue[] emptySeq = new DerValue[0];
        derOut.putSequence(emptySeq);
        byte[] resultEmpty = derOut.toByteArray();
        if (resultEmpty.length == 0) {
            throw new RuntimeException("putSequence with empty array produced empty output, expected DER SEQUENCE header");
        }

        // Test with one element
        derOut = new DerOutputStream();
        DerValue[] oneSeq = new DerValue[] { new DummyDerValue() };
        derOut.putSequence(oneSeq);
        byte[] resultOne = derOut.toByteArray();
        if (resultOne.length == 0) {
            throw new RuntimeException("putSequence with one element produced empty output");
        }

        // Test with multiple elements
        derOut = new DerOutputStream();
        DerValue[] multiSeq = new DerValue[] { new DummyDerValue(), new DummyDerValue() };
        derOut.putSequence(multiSeq);
        byte[] resultMulti = derOut.toByteArray();
        if (resultMulti.length == 0) {
            throw new RuntimeException("putSequence with multiple elements produced empty output");
        }

        // Basic sanity checks on output bytes
        // The first byte should be the SEQUENCE tag (0x30)
        if (resultOne[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("putSequence output does not start with SEQUENCE tag");
        }

        if (resultMulti[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("putSequence output does not start with SEQUENCE tag for multiple elements");
        }

        System.out.println("All putSequence tests passed.");
    }
}
