package com.mvvmsample;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NewsAPIAvailabilityTest {
    @Test
    public void testAPIAvailability() throws Exception {
        URLConnection connection = new URL(BuildConfig.BASE_URL+"7.json?api-key="+"UoxG2hYgUPioGcdkqI8OEX1RGmrkxAVT").openConnection();
        InputStream response = connection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, Charset.defaultCharset()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                buffer.append(line);
            }
        }
        assert buffer.length() > 0;
    }
}