package scraper;

import java.io.FileWriter;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class scraper {

	public static void main(String[] args) {
		String baseUrl = "https://www.amazon.in/s?rh=n%3A1389401031&fs=true&ref=lp_1389401031_sar";
		WebClient client = new WebClient(BrowserVersion.CHROME);
		client.addRequestHeader(baseUrl, baseUrl);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		try {
			for(int i=0;i<3;i++) {
			
			HtmlPage page = client.getPage(baseUrl);
//			System.out.println(page.asXml());
//			FileWriter myWriter = new FileWriter("filename.txt");
//		      myWriter.write(page.asXml());
//		      myWriter.close();

			List<HtmlElement> itemList = page.getByXPath("//div[@data-asin and not(contains(@class, 'AdHolder')) and @data-uuid]");
			if (itemList.isEmpty()) {
				System.out.println("No item found");
			} else {
////				for(int i=0;i<itemList.size();i++) {
////					System.out.println(itemList.get(i).asNormalizedText());
////				}
				for (HtmlElement htmlItem : itemList) {
					String ASIN = htmlItem.getAttribute("data-asin");
					String Title = ((HtmlElement) htmlItem.getFirstByXPath(".//span[contains(@class, 'a-size-base-plus')]")).getTextContent();
					System.out.println(Title);
					String Link = ((HtmlElement) htmlItem.getFirstByXPath(".//span[contains(@class, 'a-size-base-plus')]/..")).getAttribute("href");
//					String Review = ((HtmlElement) htmlItem.getFirstByXPath(".//span[contains(@class, 'a-icon-alt')]")).getTextContent();
//					String NoOfReview = ((HtmlElement) htmlItem.getFirstByXPath(".//span[@class='a-size-base s-underline-text']")).getTextContent();
////					String Price = ((HtmlElement) htmlItem.getFirstByXPath(".//span[@class='a-price-whole']")).getTextContent();
////					System.out.println("ASIN = "+ASIN+"  "+"Title = "+Title + "  "+"Link = "+Link+"  "+"Review = "+Review+"  "+"NoOfReview = "+NoOfReview+"  "+"Price = "+Price+"  ");
					System.out.println("ASIN = "+ASIN+"  "+"Title = "+Title + "  "+"Link = "+Link+"  ");

				}

			}
			Thread.sleep(5000);
//			System.out.println("https://www.amazon.in/"+((HtmlElement)page.getFirstByXPath(".//a[contains(@class, 's-pagination-next')]")).getAttribute("href"));
			baseUrl = "https://www.amazon.in/"+((HtmlElement)page.getFirstByXPath(".//a[contains(@class, 's-pagination-next')]")).getAttribute("href");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

//Parent element Xpath - //div[@data-asin and not(contains(@class, 'AdHolder')) and @data-uuid]
//Title - span[contains(@class, 'a-size-base-plus')]
//Link - span[contains(@class, 'a-size-base-plus')]/..
//Review - span[contains(@class, 'a-icon-alt')]
//No of review - span[@class='a-size-base s-underline-text']
//Price - span[@class='a-price-whole']
//Next Page - //a[contains(@class, 's-pagination-next')]

//https://www.amazon.in/s?rh=n%3A1389401031&fs=true&ref=lp_1389401031_sar
///html/body/center/table/tbody/tr[3]/td/table/tbody/tr[7]/td[1]/span