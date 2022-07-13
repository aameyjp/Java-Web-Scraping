package com.tut.WebScraping;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class App {
	
	public static String getData(String country) throws Exception {
		
		// Using buffer as it is easy to append no. of values to it
		StringBuffer br = new StringBuffer();
		
		br.append("<html>"
				+ "<body style='text-align:center'>");
		
		// Website to be scrapped
		String url = "https://www.worldometers.info/coronavirus/country/" + country + "/";
		
		// To connect to the url
		Document doc = Jsoup.connect(url).get();
		
		// To verify check the title
		//System.out.println(doc.title());
		br.append(doc.title().toUpperCase() + "<br><br>");
		
		// get elements by id--> 
		Elements ele = doc.select("#maincounter-wrap");
//		System.out.println(ele.html());
		
		// Now get all the elements using for loop & lambda function
		ele.forEach((e) -> {
			String text = e.select("h1").text();
			String count = e.select(".maincounter-number>span").text();
			br.append(text).append(" ").append(count).append("<br>");
			//System.out.println(text + " " + count);
		});

		br.append("</html>");
		
		return br.toString();
	}
	
    public static void main( String[] args ) throws Exception{
    	
    	/*
    	Scanner sc =new Scanner(System.in);
    	
        System.out.println("Enter country name ? ");
        String country = sc.nextLine();
        String ans = getData(country);
        
        if(ans.equals("")) {
        	System.out.println("NO SUCH COUNTRY");
        }else {
            System.out.println(ans);        	
        }
        
        sc.close();
        */
    	
    	// GUI
    	// Creating a window
    	JFrame window = new JFrame("Country wise Corona cases ");
    	window.setSize(500, 500);
    	
    	// Setting font
    	Font f = new Font("Times", Font.ITALIC, 20);
    	
    	JLabel dataL = new JLabel("Enter Name of country ?");
    	dataL.setBackground(Color.lightGray);
    	// necessary so that color appears
    	dataL.setOpaque(true);
    	
    	// Text field
    	JTextField field = new JTextField();
    	field.setHorizontalAlignment(JTextField.CENTER);
    	dataL.setHorizontalAlignment(JTextField.CENTER);
    	// Label
    	
    	field.setFont(f);
    	dataL.setFont(f);
    	
    	// Button
    	JButton btn = new JButton("SUBMIT");
    	btn.setFont(f);
    	
    	btn.addActionListener(event ->{
    		
    		try {
    		
    			String mainData = field.getText();
    			String Result = getData(mainData);
    			System.out.println("result = " + Result);
    			if(Result.contains("404 NOT FOUND")) {
    				dataL.setText("NO SUCH COUNTRY!!");
    			}else {
        			dataL.setText(Result);    				
    			}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	});
    	
    	window.setLayout(new BorderLayout());
    	window.add(field, BorderLayout.NORTH);
    	window.add(dataL, BorderLayout.CENTER);
    	window.add(btn, BorderLayout.SOUTH);
    	
    	
    	window.setVisible(true);
        
    }
}
