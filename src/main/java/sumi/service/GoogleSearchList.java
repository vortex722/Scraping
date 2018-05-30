package sumi.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearchList {

	public static void getHtml() {
		//String url = "http://ypage.tennis365.net/ypage/index_detail.html?ypage_id=4127";
		String url = "https://product.rakuten.co.jp/product/-/a4562d256fa823136999a2cf0ff8bc31/";

		try {
			Document doc = Jsoup.connect(url).get();
			System.out.println(doc);
			Elements as = doc.select("a");

			for(Element a : as) {
				System.out.println(a.attr("src"));
				//System.out.println(img);
			}

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public static void getProductLink() {

		String url = "https://search.rakuten.co.jp/search/mall/-/502368/";
		int page = 10;
		String filename = "productLink.txt";


		File file = new File(filename);
		try {
			FileWriter filewriter = new FileWriter(file);

			WebDriver driver = new PhantomJSDriver();
			driver.get(url);

			Boolean loop = true;
			int counter = 0;
			while(loop) {
				counter++;
				System.out.println("Page : " + counter);
				//(new WebDriverWait(driver,10)).until( d -> d.getTitle().startsWith("ほりえもん"));
				//new WebDriverWait(driver, 100);

				List<WebElement> elements = driver.findElements(By.linkText("最安ショップを見る"));
				for(WebElement e : elements) {
					//System.out.println(e.getText());
					System.out.println(e.getAttribute("href"));

					filewriter.write(e.getAttribute("href") + "\r\n");
				}

				try {
					WebElement nextbtn = driver.findElement(By.linkText("次のページ"));
					nextbtn.click();
				} catch (NoSuchElementException e) {
					//要素がなくなった場合、ループを終了する
			        loop = false;
			    }

				if(counter>10) {
					loop=false;
				}

			}


	        System.out.println("End:");
	        filewriter.close();

			driver.quit();

		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	}

	public static void getHtmlByPhontom() {

		String url = "http://www.google.com";
		int page = 10;
		String filename = "output.txt";


		File file = new File(filename);
		try {
			FileWriter filewriter = new FileWriter(file);

			WebDriver driver = new PhantomJSDriver();
			driver.get(url);

			WebElement element = driver.findElement(By.name("q"));
			element.sendKeys("site://https://product.rakuten.co.jp/product/");
			element.submit();

			Boolean loop = true;
			int counter = 0;
			while(loop) {
				counter++;
				System.out.println("Page : " + counter);
				//(new WebDriverWait(driver,10)).until( d -> d.getTitle().startsWith("ほりえもん"));
				new WebDriverWait(driver, 100);

				List<WebElement> elements = driver.findElements(By.cssSelector("h3.r>a"));
				for(WebElement e : elements) {
					//System.out.println(e.getText());
					//System.out.println(e.getAttribute("href"));

					filewriter.write(e.getText() + "\t" + e.getAttribute("href") + "\r\n");
				}

				try {
					WebElement nextbtn = driver.findElement(By.linkText("次へ"));
					nextbtn.click();
				} catch (NoSuchElementException e) {
					//要素がなくなった場合、ループを終了する
			        loop = false;
			    }
			}


	        System.out.println("End:");
	        filewriter.close();

			driver.quit();

		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	}

}
