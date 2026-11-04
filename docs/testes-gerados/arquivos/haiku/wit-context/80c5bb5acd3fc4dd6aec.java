/*
 * @test
 * @summary Checks public constructor version validation of TlsRsaPremasterSecretParameterSpec and exercises class initialization, which runs the privileged property-reading action
 * @modules java.base/sun.security.internal.spec
 * @run main TlsRsaPremasterSecretParameterSpecWitupTest
 */

import sun.security.internal.spec.TlsRsaPremasterSecretParameterSpec;

public class TlsRsaPremasterSecretParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        System.setProperty("com.sun.net.ssl.rsaPreMasterSecretFix", "true");

        new TlsRsaPremasterSecretParameterSpec(0x0303, 0x0301);
        new TlsRsaPremasterSecretParameterSpec(0, 0);
        new TlsRsaPremasterSecretParameterSpec(65535, 65535);

        expectIllegalArgument(-1, 0x0301);
        expectIllegalArgument(0x0303, -1);
        expectIllegalArgument(65536, 0x0301);
        expectIllegalArgument(0x0303, 65536);
    }

    private static void expectIllegalArgument(int clientVersion, int serverVersion) {
        try {
            new TlsRsaPremasterSecretParameterSpec(clientVersion, serverVersion);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for client="
                + clientVersion + " server=" + serverVersion);
    }
}
