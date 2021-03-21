package org.geekcamp.http.client;

import org.geekcamp.HttpClientApplication;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTests {
    @Test
    void allTests() throws Exception {
//        HttpRequest httpRequest = HttpRequest.build("GET /api/log....\r\n");
        HttpRequest RequestText = HttpClientApplication.RequestText();

        assertThrows(Exception.class, () -> RequestText.setMethod("FOO"));

        assertDoesNotThrow(() -> RequestText.setMethod("GET"));
        assertNotNull(RequestText.getMethod());

        RequestText.setUri(RequestText.getUri());
        assertEquals("/api/login", RequestText.getUri());

        RequestText.setHttpVersion(RequestText.getHttpVersion());
        assertEquals("HTTP/1.1", RequestText.getHttpVersion());

        final ByteBuffer buffer = ByteBuffer.wrap("Hello world!".getBytes(StandardCharsets.UTF_8));
        RequestText.setRawData(buffer);
        assertEquals(buffer, RequestText.getRawData());

        // TODO 将HTTP请求报文解析成HttpRequest对象
        // TODO 完成HTTP响应报文的解析，以及单元测试
    }
}
