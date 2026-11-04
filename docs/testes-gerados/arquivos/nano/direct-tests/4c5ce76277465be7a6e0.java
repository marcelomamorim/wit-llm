/* @test    @summary Test constructor of DeflaterInputStream with custom Deflater.    @run main DeflaterInputStreamWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithCustomDeflater();
    }

    public static void testConstructorWithCustomDeflater() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION);
        DeflaterInputStream dis = new DeflaterInputStream(bais, deflater);
        assert dis != null : "DeflaterInputStream should be instantiated";
        dis.close();
    }
}
