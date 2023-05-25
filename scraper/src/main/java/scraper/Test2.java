package scraper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.Browser;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;

public class Test2 {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);

		driver = new ChromeDriver(options);
		driver.get("http://www.google.com");
	}

}

//https://www.amazon.com/s?crid=1Z7RHFNDCL2JS&i=stripbooks-intl-ship&k=&ref=nb_sb_noss&sprefix=%2Cstripbooks-intl-ship%2C287&url=search-alias%3Dstripbooks-intl-ship
//https://www.amazon.com/s?i=electronics-intl-ship&bbn=16225009011&rh=n%3A10048700011&dc&ds=v1%3Aomq62KbpzmjyizrX08jMKGNDGSGEA5vBolRWmH9gUqs&qid=1665659917&ref=sr_ex_n_1