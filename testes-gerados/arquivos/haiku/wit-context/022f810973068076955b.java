/*
 * @test
 * @summary Verifies DerValue.getUTF8String returns decoded UTF-8 for UTF8String tags and throws IOException for other tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] abc = {'a', 'b', 'c'};
        DerValue utf8 = new DerValue((byte) 0x0C, abc);
        String result = utf8.getUTF8String();
        if (!"abc".equals(result)) {
            throw new RuntimeException("Expected abc but got " + result);
        }

        byte[] accented = "héllo".getBytes(StandardCharsets.UTF_8);
        DerValue accentedValue = new DerValue((byte) 0x0C, accented);
        String accentedResult = accentedValue.getUTF8String();
        if (!"héllo".equals(accentedResult)) {
            throw new RuntimeException("Expected héllo but got " + accentedResult);
        }

        DerValue integerValue = new DerValue((byte) 0x02, abc);
        boolean thrown = false;
        try {
            integerValue.getUTF8String();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non UTF8String tag");
        }
    }
}
