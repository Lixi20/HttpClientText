package org.geekcamp;

import org.geekcamp.http.client.HttpRequest;
import org.geekcamp.http.client.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpClientApplication {

    public static void main(String[] args) throws Exception {
        RequestText();
        ResponseText();
    }

    public static HttpRequest RequestText() throws Exception {
        final String filename = "src/main/resources/request.txt";

        final String requestStream = Files.readString(Path.of(filename));

        final int bodyIndex = requestStream.indexOf("\r\n\r\n");
        final String body = requestStream.substring(bodyIndex + 4);

        final int startLineIndex = requestStream.indexOf("\r\n");
        final String startLine = requestStream.substring(0, startLineIndex);

        final int headersBeginIndex = startLineIndex + 2;
        final String headers = requestStream.substring(headersBeginIndex, bodyIndex);

        HttpRequest httpRequest = new HttpRequest();

        startLineParse(startLine, 0, httpRequest);
        headersParse(headers, httpRequest);

        System.out.println(httpRequest.getMethod());
        System.out.println(httpRequest.getHttpVersion());
        System.out.println(httpRequest.getUri());
        System.out.println(httpRequest.getHeaders());

        return httpRequest;
//        request内容
//        System.out.println(startLine);
//        System.out.println(headers);
//        System.out.println(body);
    }

    public static HttpResponse ResponseText() throws IOException {
        final String responseFilename = "src/main/resources/response.txt";

        // InputStream inputStream = clazz.getResourceAsStream("/request.txt");

        final String responseStream = Files.readString(Path.of(responseFilename));

        final int responseBodyIndex = responseStream.indexOf("\r\n\r\n");
        final String responseBody = responseStream.substring(responseBodyIndex + 4);

        final int responseStartLineIndex = responseStream.indexOf("\r\n");
        final String responseStartLine = responseStream.substring(0, responseStartLineIndex);

        final int responseHeadersBeginIndex = responseStartLineIndex + 2;
        final String responseHeaders = responseStream.substring(responseHeadersBeginIndex, responseBodyIndex);

        HttpResponse httpResponse = new HttpResponse();

        responseStartLineParse(responseStartLine, 0, httpResponse);
        responseHeadersParse(responseHeaders, httpResponse);

//        response内容
//        System.out.println(responseStartLine);
//        System.out.println(responseHeaders);
//        System.out.println(responseBody);
        System.out.println(httpResponse.getHttpVersion());
        System.out.println(httpResponse.getStatusCode());
        System.out.println(httpResponse.getMessage());
        System.out.println(httpResponse.getHeaders());

        return httpResponse;
    }

    private static void responseHeadersParse(String responseHeaders, HttpResponse httpResponse) {
        int responseHeaderBegin = 0;
        int responseHeaderEnd = responseHeaders.indexOf("\r\n");

        if (responseHeaderEnd == -1) {
            int keyIndex = responseHeaders.indexOf(":");
            String key = responseHeaders.substring(0, keyIndex);
            String value = responseHeaders.substring(keyIndex + 2);
            httpResponse.addHeader(key, value);
        } else {
            String responseHederContent = responseHeaders.substring(responseHeaderBegin, responseHeaderEnd);
            int keyIndex = responseHederContent.indexOf(":");
            String key = responseHederContent.substring(0, keyIndex);
            String value = responseHederContent.substring(keyIndex + 2);
            httpResponse.addHeader(key, value);

            String nextHederContent = responseHeaders.substring(responseHeaderEnd + 2);
            responseHeadersParse(nextHederContent, httpResponse);
        }
    }

    private static void responseStartLineParse(String responseStartLine, int num, HttpResponse httpResponse) {
        int responseStartLintBegin = 0;
        int responseStartLintEnd = responseStartLine.indexOf(" ");

        if (responseStartLintEnd == -1) {
             String value = responseStartLine.substring(responseStartLintBegin);
            httpResponse.setMessage(value);
        } else {
            String value = responseStartLine.substring(responseStartLintBegin, responseStartLintEnd);

            String nextHederContent = responseStartLine.substring(responseStartLintEnd + 1);

            if (num == 0) {
                httpResponse.setHttpVersion(value);
            } else {
                httpResponse.setStatusCode(value);
            }
            num++;
            responseStartLineParse(nextHederContent, num, httpResponse);
        }
    }

    private static void startLineParse(String startLine, int num, HttpRequest httpRequest) throws Exception {
        int startLintBegin = 0;
        int startLinetEnd = startLine.indexOf(" ");

        if (startLinetEnd == -1) {
            String value = startLine.substring(startLintBegin);
            httpRequest.setHttpVersion(value);
        } else {
            String value = startLine.substring(startLintBegin, startLinetEnd);

            String newStartLine = startLine.substring(startLinetEnd + 1);

            if (num == 0) {
                httpRequest.setMethod(value);
            } else {
                httpRequest.setUri(value);
            }
            num += 1;
            startLineParse(newStartLine, num, httpRequest);
        }
    }

    private static void headersParse(String headers, HttpRequest httpRequest) {
        int headersBegin = 0;
        int headersEnd = headers.indexOf("\r\n");

        if (headersEnd == -1) {
            int keyIndex = headers.indexOf(":");
            String key = headers.substring(0, keyIndex);
            String value = headers.substring(keyIndex + 2);
            httpRequest.addHeader(key, value);
        } else {
            String hederContent = headers.substring(headersBegin, headersEnd);
            int keyIndex = hederContent.indexOf(":");
            String key = hederContent.substring(0, keyIndex);
            String value = hederContent.substring(keyIndex + 2);
            httpRequest.addHeader(key, value);

            String nextHederContent = headers.substring(headersEnd + 2);
            headersParse(nextHederContent, httpRequest);
        }
    }
}

