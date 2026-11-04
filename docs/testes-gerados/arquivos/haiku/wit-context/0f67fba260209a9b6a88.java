/*
 * @test
 * @summary Verify InflaterInputStream.reset() always throws IOException because mark/reset is not supported
 * @run main InflaterInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testResetOneArgCtor();
        testResetTwoArgCtor();
        testResetThreeArgCtor();
        testResetRepeatedCalls();
    }

    static void testResetOneArgCtor() {
        InflaterInputStream s = new InflaterInputStream(newInput());
        expectResetFailure(s);
    }

    static void testResetTwoArgCtor() {
        InflaterInputStream s = new InflaterInputStream(newInput(), new Inflater());
        expectResetFailure(s);
    }

    static void testResetThreeArgCtor() {
        InflaterInputStream s = new InflaterInputStream(newInput(), new Inflater(), 16);
        expectResetFailure(s);
    }

    static void testResetRepeatedCalls() {
        InflaterInputStream s = new InflaterInputStream(newInput());
        expectResetFailure(s);
        expectResetFailure(s);
    }

    static InputStream newInput() {
        return new ByteArrayInputStream(new byte[] {1, 2, 3});
    }

    static void expectResetFailure(InflaterInputStream s) {
        try {
            s.reset();
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
            return;
        }
        throw new RuntimeException("reset() did not throw IOException");
    }
}
