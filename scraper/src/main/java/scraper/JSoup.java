package scraper;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JSoup {

	public static void main(String[] args) throws IOException {
		
		Document document = Jsoup.connect("https://www.amazon.com/s?bbn=13707&rh=n%3A283155%2Cn%3A173507%2Cn%3A173515%2Cn%3A227544%2Cn%3A13707%2Cn%3A13723&dc&qid=1665651390&rnid=13707&ref=lp_13707_nr_n_1").get();
		FileWriter leafNodes = new FileWriter("test.html", true);
		leafNodes.write(document.outerHtml());
		leafNodes.flush();
		System.out.println("done");

	}

}
