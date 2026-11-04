/*
 * @test
 * @summary Witup-generated checks for DerOutputStream.putSequence
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.putSequence(new DerValue[0]);
        byte[] b = out.toByteArray();
        if (b.length != 2 || b[0] != (byte) 0x30 || b[1] != (byte) 0x00) {
            throw new RuntimeException("unexpected empty sequence encoding");
        }

        DerOutputStream out2 = new DerOutputStream(16);
        out2.putSequence(new DerValue[0]);
        out2.putSequence(new DerValue[0]);
        byte[] c = out2.toByteArray();
        if (c.length != 4 || c[0] != (byte) 0x30 || c[1] != (byte) 0x00
                || c[2] != (byte) 0x30 || c[3] != (byte) 0x00) {
            throw new RuntimeException("unexpected appended sequence encoding");
        }

        try {
            new DerOutputStream().putSequence((DerValue[]) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }
}
