/* @test    @summary Test InflaterOutputStream constructor with default inflater    @run main InflaterOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithOutputStream();
    }

    static void testConstructorWithOutputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos);
        assert ios != null : "InflaterOutputStream should be instantiated";
        // Verify that the inflater used is default by checking class type
        assert ios.inf != null : "Inflater should not be null";
        assert ios.inf.getClass() == Inflater.class : "Inflater should be of type Inflater";
        // Verify that usesDefaultInflater is true
        assert ios.usesDefaultInflater : "usesDefaultInflater should be true";
        // Close stream
        try {
            ios.close();
        } catch (Exception e) {
            assert false : "Closing stream should not throw";
        }
    }
}
