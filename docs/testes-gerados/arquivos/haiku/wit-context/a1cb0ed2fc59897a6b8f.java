/*
 * @test
 * @summary Witup-generated tests for XECPrivateKeySpec constructor null checks and defensive copying
 * @run main XECPrivateKeySpecWitupTest
 */
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.XECPrivateKeySpec;
import java.util.Arrays;

public class XECPrivateKeySpecWitupTest {

    static final class DummyParams implements AlgorithmParameterSpec {
    }

    public static void main(String[] args) {
        testNullParamsThrowsNPE();
        testNullScalarThrowsNPE();
        testBothNullThrowsNPE();
        testScalarIsCopiedOnConstruction();
        testGetScalarReturnsCopy();
        testGetParamsReturnsSameInstance();
        testGetScalarContentMatches();
        testEmptyScalarAccepted();
    }

    static void expectNPE(Runnable r, String name) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError(name + ": expected NullPointerException");
    }

    static void testNullParamsThrowsNPE() {
        expectNPE(() -> new XECPrivateKeySpec(null, new byte[32]), "nullParams");
    }

    static void testNullScalarThrowsNPE() {
        expectNPE(() -> new XECPrivateKeySpec(new DummyParams(), null), "nullScalar");
    }

    static void testBothNullThrowsNPE() {
        expectNPE(() -> new XECPrivateKeySpec(null, null), "bothNull");
    }

    static void testScalarIsCopiedOnConstruction() {
        byte[] input = new byte[32];
        Arrays.fill(input, (byte) 0x11);
        XECPrivateKeySpec spec = new XECPrivateKeySpec(new DummyParams(), input);
        input[0] = (byte) 0x22;
        byte[] out = spec.getScalar();
        if (out[0] != (byte) 0x11) {
            throw new AssertionError("scalar not defensively copied on construction");
        }
    }

    static void testGetScalarReturnsCopy() {
        byte[] input = new byte[32];
        Arrays.fill(input, (byte) 0x33);
        XECPrivateKeySpec spec = new XECPrivateKeySpec(new DummyParams(), input);
        byte[] first = spec.getScalar();
        first[0] = (byte) 0x44;
        byte[] second = spec.getScalar();
        if (second[0] != (byte) 0x33) {
            throw new AssertionError("getScalar does not return a fresh copy");
        }
        if (first == second) {
            throw new AssertionError("getScalar returned the same array instance");
        }
    }

    static void testGetParamsReturnsSameInstance() {
        DummyParams params = new DummyParams();
        XECPrivateKeySpec spec = new XECPrivateKeySpec(params, new byte[32]);
        if (spec.getParams() != params) {
            throw new AssertionError("getParams did not return the supplied params instance");
        }
    }

    static void testGetScalarContentMatches() {
        byte[] input = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        XECPrivateKeySpec spec = new XECPrivateKeySpec(new DummyParams(), input);
        if (!Arrays.equals(input, spec.getScalar())) {
            throw new AssertionError("getScalar content does not match input");
        }
    }

    static void testEmptyScalarAccepted() {
        XECPrivateKeySpec spec = new XECPrivateKeySpec(new DummyParams(), new byte[0]);
        if (spec.getScalar().length != 0) {
            throw new AssertionError("empty scalar length not preserved");
        }
    }
}
