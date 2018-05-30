package sumi.dto;

import java.util.List;

public class ItemListDTO {

	private String shopName;
	private String shopURL;
	private String title;
	private String URL;
	private String price;
	private String productListUrl;
	private List<ProductDTO> productList;

	public List<ProductDTO> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopURL() {
		return shopURL;
	}
	public void setShopURL(String shopURL) {
		this.shopURL = shopURL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProductListUrl() {
		return productListUrl;
	}
	public void setProductListUrl(String productListUrl) {
		this.productListUrl = productListUrl;
	}

}
