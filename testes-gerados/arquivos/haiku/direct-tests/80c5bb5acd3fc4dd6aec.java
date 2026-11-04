/*
 * @test
 * @summary Constructor checks for TlsRsaPremasterSecretParameterSpec
 * @modules java.base/sun.security.internal.spec
 * @run main TlsRsaPremasterSecretParameterSpecWitupTest
 */

import sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec;

public class TlsRsaPremasterSecretParameterSpecWitupTest {

    public static void main(String[] args) {
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "true");

        new TlsRsaPremasterSecretParameterSpec(0x0303, 0x0301);
        new TlsRsaPremasterSecretParameterSpec(0, 65535);
        new TlsRsaPremasterSecretParameterSpec(0x0303, 0x0303, new byte[48]);

        expectIAE(-1, 0x0303);
        expectIAE(0x0303, -1);
        expectIAE(65536, 0x0303);
        expectIAE(0x0303, 65536);
    }

    private static void expectIAE(int clientVersion, int serverVersion) {
        try {
            new TlsRsaPremasterSecretParameterSpec(clientVersion, serverVersion);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for "
                + clientVersion + ", " + serverVersion);
    }
}
