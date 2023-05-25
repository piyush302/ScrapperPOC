package scraper;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class crawler {

	public static void main(String[] args) {
		String url = "";
		String name = "";
		Product startProduct = setproduct("","");
		 Queue<Product> queue = new LinkedList<Product>();
	        queue.add(startProduct);
	        while (!queue.isEmpty()) {
	        	Product current = queue.poll();
	            if (!current.visited) {
	                current.visited=true;
	                System.out.println(current);
	                //get all the nebr as linked list
	                current.setNebr();
	                //add all the parent path
	                queue.addAll(current.neighbors);
	            }
	        }

	}
 static Product setproduct(String name, String url) {
		Product startProduct = new Product();
		startProduct.name = name;
		startProduct.URL = url;
		startProduct.neighbors = null;//get it by calling the page
		return startProduct;
		
	}

	public static class Product {

	    public String name;
	    public boolean visited;
	    public String URL;
	    List<Product> neighbors = new LinkedList<Product>();

	}

}
