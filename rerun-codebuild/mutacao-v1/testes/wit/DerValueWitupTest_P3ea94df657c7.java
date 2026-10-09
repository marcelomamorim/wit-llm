/* @test    @summary Test for DerValue.getIA5String() method    @run main DerValueWitupTest_P3ea94df657c7 */

import java.io.*;

public class DerValueWitupTest_P3ea94df657c7 {

    public static void main(String[] args) throws Exception {
        testGetIA5StringWithValidTag();
        testGetIA5StringWithInvalidTag();
    }

    private static void testGetIA5StringWithValidTag() throws Exception {
        // Cria um DerValue com tag igual a tag_IA5String (22)
        byte tagIA5 = 22;
        byte[] dataBytes = "TestString".getBytes("ASCII");
        DerValue dv = new DerValue(new byte[] {tagIA5});
        // Força o campo tag para o valor esperado
        dv.tag = tagIA5;
        // Mockar o método getDataBytes() para retornar dataBytes
        // Como não podemos modificar a classe DerValue, criamos uma subclasse anônima
        DerValue dvMock = new DerValue(dv.buffer != null ? dv.buffer.getBytes() : new byte[0]) {
            @Override
            public byte[] getDataBytes() {
                return dataBytes;
            }
        };
        dvMock.tag = tagIA5;
        String result = dvMock.getIA5String();
        if (!"TestString".equals(result)) {
            throw new AssertionError("Expected 'TestString', got: " + result);
        }
    }

    private static void testGetIA5StringWithInvalidTag() {
        // Cria um DerValue com tag diferente de tag_IA5String
        byte invalidTag = 0x01; // BOOLEAN
        DerValue dv = new DerValue(new byte[] {invalidTag});
        dv.tag = invalidTag;
        try {
            dv.getIA5String();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Esperado
            if (!e.getMessage().contains("DerValue.getIA5String, not IA5")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
