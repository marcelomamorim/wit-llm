/*
 * @test
 * @summary DerOutputStream.putTruncatedUnalignedBitString must fail with NullPointerException for a null BitArray and write nothing
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        DerOutputStream out = new DerOutputStream();
        try {
            out.putTruncatedUnalignedBitString(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected: ba.truncate() dereferences null before any output is written
        }
        if (out.size() != 0) {
            throw new AssertionError("output must not be modified, size=" + out.size());
        }
    }
}
