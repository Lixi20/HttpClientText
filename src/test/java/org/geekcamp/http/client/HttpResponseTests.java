package org.geekcamp.http.client;

import org.geekcamp.HttpClientApplication;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class HttpResponseTests {
    @Test
    void allTests() throws IOException {
        HttpResponse ResponseText = HttpClientApplication.ResponseText();

        ResponseText.setHttpVersion(ResponseText.getHttpVersion());
        assertEquals("HTTP/1.1",ResponseText.getHttpVersion());

        ResponseText.setStatusCode(ResponseText.getStatusCode());
        assertEquals("200",ResponseText.getStatusCode());

        ResponseText.setMessage(ResponseText.getMessage());
        assertEquals("OK",ResponseText.getMessage());

        final ByteBuffer buffer = ByteBuffer.wrap("Hello world!".getBytes(StandardCharsets.UTF_8));
        ResponseText.setRawData(buffer);
        assertEquals(buffer, ResponseText.getRawData());

        // TODO 将HTTP请求报文解析成HttpRequest对象
        // TODO 完成HTTP响应报文的解析，以及单元测试

    }
}
