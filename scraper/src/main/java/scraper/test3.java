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

public class test3 {

	public static void main(String[] args) {

		WebClient client = new WebClient(BrowserVersion.CHROME);
		String baseUrl = "https://www.amazon.com/s?bbn=13707&rh=n%3A283155%2Cn%3A173507%2Cn%3A173515%2Cn%3A227544%2Cn%3A13707%2Cn%3A13723&dc&qid=1665651390&rnid=13707&ref=lp_13707_nr_n_1";

		client.getOptions().setThrowExceptionOnScriptError(false);
		client.addRequestHeader(baseUrl, baseUrl);
		client.setAjaxController(new NicelyResynchronizingAjaxController());

		try {
			FileWriter test = new FileWriter("test.txt");
			HtmlPage page = client.getPage(baseUrl);
			client.waitForBackgroundJavaScript(5000);
			test.write(page.asNormalizedText());
			test.flush();
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

//https://www.amazon.com/ask/questions/asin/B07PXGQC1Q/ref=ask_dp_lla_ql_hza


