package scraper;

import java.util.List;

public class Product {
	String name;
	String URL;
	String path;
	boolean visited;
	
	
	public Product(String name, String uRL) {
		super();
		this.name = name;
		this.URL = uRL;
	}
	List<Product> nbr;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public List<Product> getNbr() {
		return nbr;
	}
	public void setNbr(List<Product> nbr) {
		this.nbr = nbr;
	}


}
