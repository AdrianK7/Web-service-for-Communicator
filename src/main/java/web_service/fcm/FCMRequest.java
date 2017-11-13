package web_service.fcm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import web_service.utilities.RequestInterceptor;

@Service
public class FCMRequest
{
	@Async
	public <T> Future<FCMResponse> callForPush(T NotificationObject, RestTemplate restTemplate, List<RequestInterceptor> headers)
	{
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.addAll(headers);
        restTemplate.setInterceptors(interceptors);
		T push = NotificationObject;
        try
        {
			FCMResponse response = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", push, FCMResponse.class);
	        if(response.getSuccess() == 1)
	        {
	        	return new AsyncResult<FCMResponse>(response);
	        }
	        else
	        	return new AsyncResult<FCMResponse>(response);
        }
        catch(RestClientException e)
        {
        	return new AsyncResult<FCMResponse>(new FCMResponse());
        }

	}
}
