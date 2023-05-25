package scraper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class GetApi {

	public static void main(String[] args) throws IOException, InterruptedException {
	    try {
			final String uri = "https://api.ipgeolocation.io/ipgeo?apiKey=a7171bb3e40345749c6eb3dbeef4f516";

			RestTemplate restTemplate = new RestTemplate();
			for(int i=0;i<100;i++) {
				String result = restTemplate.getForObject(uri, String.class);
				FileWriter leafNodes = new FileWriter("test.txt",true);
				leafNodes.write(result);
				leafNodes.flush();
				TimeUnit.SECONDS.sleep(10);
				System.out.println("Done");
			}
			

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
