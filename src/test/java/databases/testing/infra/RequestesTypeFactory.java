package databases.testing.infra;

import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;

public class RequestesTypeFactory {
	private static Builder getRequestBuilder(String url) {
		Builder requestBuilder = new Request
				.Builder()
				.addHeader("content-type", "application/json")
				.url(url);
		return requestBuilder;
	}
	
	public static Request getRequest(String url, RequestType verbe, RequestBody ...body) {
		Builder requestBuilder = getRequestBuilder(url);
		
		switch (verbe) {
			case GET:
				requestBuilder.get();			
				break;
			case POST:
				requestBuilder.post(body[0]);
				break;
			case DELETE:
				requestBuilder.delete();
				break;
			case PUT:
				requestBuilder.put(body[0]);
				break;
			case PATCH:
				requestBuilder.patch(body[0]);
				break;

			default:
				System.out.println(verbe + " No Such Method ");
				break;
		}
		return requestBuilder.build();
	}
}
