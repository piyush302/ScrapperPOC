package scraper;

import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class test {

	public static void main(String[] args) {
//		https://www.amazon.com/s?i=electronics-intl-ship&bbn=16225009011&rh=n%3A10048700011&dc&ds=v1%3Aomq62KbpzmjyizrX08jMKGNDGSGEA5vBolRWmH9gUqs&qid=1665659917&ref=sr_ex_n_1
//		String baseUrl = "https://www.amazon.com/s?bbn=13707&rh=n%3A283155%2Cn%3A173507%2Cn%3A173515%2Cn%3A227544%2Cn%3A13707%2Cn%3A13723&dc&qid=1665651390&rnid=13707&ref=lp_13707_nr_n_1";
		String baseUrl = "https://api.ipgeolocation.io/ipgeo?apiKey=a7171bb3e40345749c6eb3dbeef4f516";

		WebClient client = new WebClient(BrowserVersion.CHROME);
//		client.getOptions().setThrowExceptionOnScriptError(false);
//		client.waitForBackgroundJavaScriptStartingBefore(50000);
		client.addRequestHeader(baseUrl, baseUrl);
//		client.getOptions().setCssEnabled(false);
//		client.getOptions().setJavaScriptEnabled(false);
		client.setAjaxController(new NicelyResynchronizingAjaxController());

		try {
			FileWriter leafNodes = new FileWriter("test.txt");
			HtmlPage page = client.getPage(baseUrl);
			page = client.getPage(baseUrl);
//			page.refresh();
//			client.waitForBackgroundJavaScript(10000);
//			client.setAjaxController(new NicelyResynchronizingAjaxController());
			leafNodes.write(page.asXml());
			leafNodes.flush();
			System.out.println("done");
//			page.getFirstByXPath("//*[text() = \\\"See all results\\\"]");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

//https://www.amazon.com/ask/questions/asin/B07PXGQC1Q/ref=ask_dp_lla_ql_hza


