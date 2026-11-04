/* @test    @summary Test for Properties.load(InputStream) method, covering null input stream case.    @run main PropertiesWitupTest */

import java.util.Properties;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testLoadWithNullInputStream();
        testLoadWithValidInputStream();
    }

    public static void testLoadWithNullInputStream() {
        Properties props = new Properties();
        try {
            props.load((InputStream) null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testLoadWithValidInputStream() {
        Properties props = new Properties();
        String data = "key1=value1\nkey2=value2";
        InputStream input = new ByteArrayInputStream(data.getBytes());
        try {
            props.load(input);
            if ("value1".equals(props.getProperty("key1")) && "value2".equals(props.getProperty("key2"))) {
                System.out.println("PASS: Properties loaded correctly");
            } else {
                System.out.println("FAIL: Properties not loaded as expected");
            }
        } catch (IOException e) {
            System.out.println("FAIL: IOException thrown: " + e);
        }
    }
}
