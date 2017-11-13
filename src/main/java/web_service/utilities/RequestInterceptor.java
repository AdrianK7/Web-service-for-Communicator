package web_service.utilities;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RequestInterceptor implements ClientHttpRequestInterceptor 
{
    private final String headerName;
    private final String headerValue;

    public RequestInterceptor(String headerName, String headerValue) 
    {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException 
    {
        HttpHeaders headers = request.getHeaders();
        headers.add(headerName, headerValue);
        return execution.execute(request, body);
    }
}
