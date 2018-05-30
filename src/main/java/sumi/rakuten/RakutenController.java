package sumi.rakuten;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RakutenController {
	public static void run(){
		final String startURL = "https://search.rakuten.co.jp/search/mall/-/502368/";
		final int max = 10000;
		final int sleeptime=1000;
		String timeformat = "yyyy-MM-dd_HH-mm-ss";
		SimpleDateFormat sdf = new SimpleDateFormat(timeformat);

		Calendar cl2 = Calendar.getInstance();
		String filename = "RakutenItemList_" + sdf.format(cl2.getTime()) + ".csv";
		File file = new File(filename);


		//楽天のアイテム一覧ページのリスト
		List<RakutenItemList> ItemLists = new ArrayList();

		//ループ処理用の内部変数初期化
		boolean handler = true;
		String url = startURL;
		int counter = 0;

		System.out.println("Start with : "+url);
		System.out.println("-----------------------------------");

		try {
			FileWriter filewriter = new FileWriter(file);


			while(handler) {
				//楽天のアイテムリストを1ページスクレイピング
				counter++;
				RakutenItemList itemlist = new RakutenItemList(url);
				ItemLists.add(itemlist);

				itemlist.write(filewriter);

				Calendar cl = Calendar.getInstance();
				System.out.println(sdf.format(cl.getTime())+" : "+counter+"ページ目完了 : "+url);

				//次のページの動作設定
				url = itemlist.getNextUrl();
				System.out.println("url:"+url);
				if(url.equals("") || !url.startsWith("http") || counter==max) {
					//空文字　または　httpから始まらな文字列の場合、ループを抜ける
					//counterは試験用
					handler = false;
				}


				//ちょっと寝かす
				try {
					Thread.sleep(sleeptime);
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}

			filewriter.close();
			System.out.println("-----------------------------------");
			System.out.println("完了");

		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	}
}
