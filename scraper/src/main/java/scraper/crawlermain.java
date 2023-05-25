package scraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class crawlermain {

	public static void main(String[] args) {
		String baseUrl = "https://www.amazon.com/s?i=hpc-intl-ship&bbn=16225010011&rh=n%3A723418011&dc&ds=v1%3AfOIIJ2SSFtM2R7vr77Mps1mvtCpvPcahP3auMJni5YQ&qid=1664212763&ref=sr_ex_n_1";
		WebClient client = new WebClient(BrowserVersion.CHROME);
		client.addRequestHeader(baseUrl, baseUrl);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		try {

			Product startProduct = new Product("Stationery & Gift Wrapping Supplies", baseUrl);
			Queue<Product> queue = new LinkedList<Product>();
			HashSet<String> set=new HashSet<String>();
			queue.add(startProduct);
			while (!queue.isEmpty()) {
				Product current = queue.poll();
				if (!set.contains(current.getName())) {
					set.add(current.getName());
					current.setNbr(getNebr(client, current.getURL()));
					System.out.println(current.getName()+"  "+current.getURL());
					List<Product> a = current.getNbr();
					if(a.get(a.size()-1).getName().equals(current.getName())) {
						System.out.println("Leaf Node");
					}
					queue.addAll(a);
					for (Product product : current.getNbr()) {
						System.out.println("Name = " + product.getName() + " " + "Link = " + product.getURL());

					}
				}
			}

//			for (Product product : products) {
//				System.out.println("Name = " + product.getName() + " " + "Link = " + product.getURL());

//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static List<Product> getNebr(WebClient client, String baseUrl)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println("Making Web Call");
		HtmlPage page = client.getPage(baseUrl);

		List<HtmlElement> itemList = page.getByXPath("//*[text() = \"Department\"]/../..//li");
		List<Product> products = getProductList(itemList);
		return products;

	}

	private static List<Product> getProductList(List<HtmlElement> itemList) {

		List<Product> productList = new LinkedList<Product>();
		for (HtmlElement htmlItem : itemList) {

			String name = htmlItem.getVisibleText();
			String link = getHref(htmlItem, ".//a");
			Product p = new Product(name, "https://www.amazon.com" + link);
			productList.add(p);
//			System.out.println("Name = " + name + " " + "Link = " + link);

		}
		return productList;
	}

	public static String getHref(HtmlElement htmlItem, String XPath) {
		try {
			if (htmlItem.getFirstByXPath(XPath) == null)
				return "";
			else
				return ((HtmlElement) htmlItem.getFirstByXPath(XPath)).getAttribute("href");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
