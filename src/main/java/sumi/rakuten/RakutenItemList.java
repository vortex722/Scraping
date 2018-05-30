package sumi.rakuten;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sumi.dto.ItemListDTO;
import sumi.dto.ProductDTO;

public class RakutenItemList {

	private List<ItemListDTO> itemList;
	private String url;
	private String nextUrl;

	public List<ItemListDTO> getItemList() {
		return itemList;
	}
	public String getUrl() {
		return url;
	}
	public String getNextUrl() {
		return nextUrl;
	}

	//コンストラクタでURLをもらって、ページをスクレイピングする
	public RakutenItemList(String requestUrl) {
		url = requestUrl;
		itemList = new ArrayList();

		try {
			Document doc = Jsoup.connect(url).get();
			System.out.println(doc);

			//「最安ショップを見る」が存在するitemを特定
			Elements items =  doc.select("div.searchresultitem>div.extra");
			for(Element item : items) {

				ItemListDTO dto = new ItemListDTO();
				Element parent = item.parent();

				dto.setProductListUrl(item.select("a").attr("href"));
				dto.setTitle(parent.select("div.title>h2>a").text());
				dto.setURL(parent.select("div.title>h2>a").attr("href"));
				dto.setPrice(parent.select("div.price>span.important").text());
				dto.setShopName(parent.select("div.merchant>a").text());
				dto.setShopURL(parent.select("div.merchant>a").attr("href"));
				dto.setProductList(RakutenProductList(dto.getProductListUrl()));
				itemList.add(dto);
			}

			//次のページへのURL取得
			nextUrl = doc.select("a.nextPage").attr("href");

		} catch (IOException e) {

		}
	}

	private List<ProductDTO> RakutenProductList(String url){
		List<ProductDTO> productList = new ArrayList();

		try {
			Document doc = Jsoup.connect(url).get();

			Elements items = doc.select("tr.specColumnUsed");
			for(Element item : items) {

				ProductDTO dto = new ProductDTO();

				dto.setPrice(item.select("td:nth-of-type(1)>span").text());
				dto.setShopName(item.select("td:nth-of-type(4)>div.shop_link>a").text());
				dto.setShopURL(item.select("td:nth-of-type(4)>div.shop_link>a").attr("href"));
				dto.setTitle(item.select("td:nth-of-type(5)>div.quickViewIteminfo>div>a").text());
				dto.setURL(item.select("td:nth-of-type(5)>div.quickViewIteminfo>div>a").attr("href"));

				productList.add(dto);
			}

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return productList;
	}
	public void write(FileWriter filewriter) {
		// TODO 自動生成されたメソッド・スタブ

		for(ItemListDTO item : itemList) {
			for(ProductDTO product : item.getProductList()) {
				try {
					filewriter.write(
							item.getProductListUrl() + "\t"
							+item.getTitle() + "\t"
							+item.getURL()+ "\t"
							+item.getShopName()+ "\t"
							+item.getShopURL()+ "\t"
							+item.getPrice()+ "\t"
							+product.getTitle() + "\t"
							+product.getURL()+ "\t"
							+product.getShopName()+ "\t"
							+product.getShopURL()+ "\t"
							+product.getPrice()+ "\r\n"
							);
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}
	}

}
