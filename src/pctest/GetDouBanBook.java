package pctest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import pojo.Book;

public class GetDouBanBook extends Thread {
	@Test
	public void test(){

		try {
			String tUrl = "";
			String strUrl = "https://book.douban.com/tag/编程?";
			HSSFWorkbook hssfworkbook = new HSSFWorkbook();
			HSSFSheet hssfsheet = hssfworkbook.createSheet("豆瓣编程信息");
			HSSFRow hssfrow = hssfsheet.createRow(0);
			hssfrow.createCell(0).setCellValue("序号");
			hssfrow.createCell(1).setCellValue("书籍名称");
			hssfrow.createCell(2).setCellValue("评价数");
			hssfrow.createCell(3).setCellValue("评分");
			hssfrow.createCell(4).setCellValue("作者");
			hssfrow.createCell(5).setCellValue("出版社");
			hssfrow.createCell(6).setCellValue("出版日期");
			hssfrow.createCell(7).setCellValue("价格");
			Integer num = 1;
			List<Book> list = new ArrayList<>();
			for (int j = 0; j <= 6; j++) {// 爬出前六页数据，并进行相关操作
				tUrl = "";

				String a = 20 * j + "";
				tUrl = strUrl + "start=" + a + "&type=T";
				System.out.println(tUrl);
				String pageData = "";
				int len;
				URL url = new URL(tUrl);
				HttpURLConnection url_con = null;
				url_con = (HttpURLConnection) url.openConnection();
				url_con.setFollowRedirects(true);
				url_con.setInstanceFollowRedirects(false);
				url_con.setRequestMethod("GET");
				if (url_con.getResponseCode() == 200) {
					InputStream in = url_con.getInputStream();
					byte[] by = new byte[1024];
					while ((len = in.read(by)) != -1) {
						pageData += new String(by, 0, len);
					}
					in.close();
					url_con.disconnect();
					// 设置抓取规则
					String regEx = "<h2class=\"\">(.+?)</h2>"
							+ "<divclass=\"pub\">(.+?)</div>"
							+ "<divclass=\"starclearfix\">(.+?)</div>";
					String regEx2 = "<spanclass=\"pl\">(.+?)</span>";
					String bookName = null;
					String constrall = null;
					String score = null;
					String content = null;
					String country = null;
					String author = null;
					String press = null;
					String pressdate = null;
					String price = null;

					pageData = pageData.replaceAll("\\s|\\t|\\r", "");
					Matcher mat = Pattern.compile(regEx).matcher(pageData);
					while (mat.find()) {

						bookName = mat.group(1).replaceAll("<a.*?>|</a>", "");
						constrall = mat.group(2).replaceAll("|", "");
						score = mat
								.group(3)
								.replaceAll(
										"<span.*></span><spanclass=\"rating_nums\">|</span><span.*>.*</span>",
										"");
						if (score.contains("少于")) {
							score = "0";
						}
						content = mat
								.group(3)
								.replaceAll(
										"<span.*></span><spanclass=\"rating_nums\">.*</span><spanclass=\"pl\">|</span>",
										"");

						content = content.replace("人评价)", "").replace("(", "");

						String[] conStr = constrall.split("/");
						if (conStr.length == 4) {
							author = conStr[0];
							press = conStr[1];// 出版社
							pressdate = conStr[2];
							price = conStr[3];
						}
						if (conStr.length > 4) {
							author = conStr[1];
							press = conStr[2];// 出版社
							pressdate = conStr[3];
							price = conStr[4];
						}
						Book book = new Book(bookName, constrall,
								Double.parseDouble(score), content, country,
								author, press, pressdate, price);
						list.add(book);

					}

					url_con.disconnect();
				}

				/* } */
			}
			Collections.sort(list, new Comparator<Book>(){  
	             
	             public int compare(Book o1, Book o2) { 
	                 if(o1.getScore() < o2.getScore()){  
	                     return 1;  
	                 }  
	                 if(o1.getScore() == o2.getScore()){  
	                     return 0;  
	                 }  
	                 return -1;  
	             }  
	         });   
			int i=0;
	         for (Book book : list) {
	         	 try {
	            	  if(Integer.valueOf(book.getContent())>1000){
	            		  if(i<=39){
	            		  i++;
	                	  HSSFRow  hssfrow2 = hssfsheet.createRow(hssfsheet.getLastRowNum()+1);
	                      hssfrow2.createCell(0).setCellValue(num++); 
	                      hssfrow2.createCell(1).setCellValue(book.getBookName());
	                      hssfrow2.createCell(2).setCellValue(book.getContent());
	                      hssfrow2.createCell(3).setCellValue(book.getScore());  
	                      hssfrow2.createCell(4).setCellValue(book.getAuthor());
	                      hssfrow2.createCell(5).setCellValue(book.getPress());  
	                      hssfrow2.createCell(6).setCellValue(book.getPressdate());  
	                      hssfrow2.createCell(7).setCellValue(book.getPrice());  
	                    }                                         
	            	  }                
				} catch (Exception e) {
				}
	         	//将数据导入到excel中  
	             File fileName = new File("H:\\pc1.xls");  
	             if(!fileName.exists()){  
	                 fileName.createNewFile();  
	             }  
	             FileOutputStream output = new FileOutputStream(fileName);  
	             hssfworkbook.write(output);  
	             output.close();  
					
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}