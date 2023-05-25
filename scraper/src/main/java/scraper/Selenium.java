package scraper;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Selenium {

	public static void main(String[] args) throws IOException, InterruptedException {
		WebDriver driver = null;
		WebDriverManager.chromedriver().create();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("enable-automation");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-browser-side-navigation");
		options.addArguments("--disable-gpu");
		options.addArguments("--headless");
		driver = new ChromeDriver(options);
		FileWriter leafNodes = new FileWriter("test.html");
		System.out.println("Befor for loop");
//		for(int i=0;i<2;i++) {
//			System.out.println(i);
			driver.get(
					"https://www.amazon.com/s?i=hpc-intl-ship&bbn=16225010011&rh=n%3A723418011&dc&ds=v1%3AfOIIJ2SSFtM2R7vr77Mps1mvtCpvPcahP3auMJni5YQ&qid=1664212763&ref=sr_ex_n_1");
//			driver.get(
//					"https://www.amazon.com/s?i=hpc-intl-ship&bbn=16225010011&rh=n%3A723418011%2Cn%3A723462011&dc&qid=1671608782&rnid=723418011&ref=sr_nr_n_2&ds=v1%3A0DcgP%2FBcSxsVF7g2Q0OiXJfhkxqjnXAzKFz6mOz59NE");
			driver.navigate().refresh();
			
			WebClient client = new WebClient();
			client.getOptions().setThrowExceptionOnScriptError(false);
			HtmlPage page = client.loadHtmlCodeIntoCurrentWindow(driver.getPageSource().toString());
			
//			URL url = new URL("https://www.amazon.com/s?i=hpc-intl-ship&bbn=16225010011&rh=n%3A723418011&dc&ds=v1%3AfOIIJ2SSFtM2R7vr77Mps1mvtCpvPcahP3auMJni5YQ&qid=1664212763&ref=sr_ex_n_1");
//			StringWebResponse response = new StringWebResponse(driver.getPageSource(), url);
//			WebClient client = new WebClient();
//			HtmlPage page = new HtmlPage(response, client.getCurrentWindow());
//			System.out.println(page.getTitleText());
			leafNodes.write(page.asXml());
			leafNodes.flush();
//		}

		
		System.out.println("done");

	}

}
