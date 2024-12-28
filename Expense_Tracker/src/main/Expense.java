package main;

import java.util.ArrayList;

public class Expense {
	static ArrayList<Expense> exp = new ArrayList<>();
	private int s_no;
	private String category;
	private String item;
	private int price;
	private String p_date;
	
	public Expense() {
		this.s_no = 0;
        this.category = "Unknown";
        this.item = "Unknown";
        this.price = 0;
        this.p_date = "Unknown";
    }
	
	public Expense(int z,String a,String b,int c,String d){
		s_no = z;
		category = a;
		item = b;
		price = c;
		p_date = d;
	}
	
	public void addExpense(Expense obj) {
		exp.add(obj);
	}
	
	public void displayExpense() {
		for(Expense e:exp) {
			System.out.println("Serial No:"+e.s_no+" Category:"+e.category+" Item Name:"+e.item+" Price:"+e.price+" Purchase Date:"+e.p_date);
		}
	}
	
	public void deleteExpense(int serial_no) {
		for(Expense e:exp) {
			if(e.s_no == serial_no) {
				exp.remove(e);
				System.out.println("Expense removed successfully");
				break;
			}
		}
	}
}
