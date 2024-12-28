package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Tracker {
	public static void main(String[] args) throws SQLException {
		
		String url = "jdbc:mysql://localhost:3306/YOUR_MYSQL_DATABASE_NAME";
		String username = "YOUR_MYSQL_USERNAME";
		String password = "YOUR_MYSQL_PASSWORD";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try{
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connection Established");
		}
		catch(Exception e) {
			System.out.println("Connection Failed "+e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("Welcome to Expense Tracker");
		Scanner sc = new Scanner(System.in);
		int choice=0;
		while(choice!=6) {
			System.out.println("Choose any one of the option:");
			System.out.println("1.Add Expense");
			System.out.println("2.View Expenses");
			System.out.println("3.Delete Expense");
			System.out.println("4.Update Expense");
			System.out.println("5.Show Expense Summary");
			System.out.println("6.Exit");
			choice = sc.nextInt();
			
			if(choice==1) {
				System.out.println("Enter Id:");
				int s_no = sc.nextInt();
				System.out.println("Enter Category:");
				String category = sc.next();
				System.out.println("Item:");
				String item = sc.next();
				System.out.println("Price:");
				int price = sc.nextInt();
				System.out.println("Date of Purchase(YYYY-MM-DD):");
				String p_date = sc.next();
				
				String insertExpenseQuery = "INSERT INTO expenses (id, amount, category, date, item) VALUES (?, ?, ?, ?, ?)";
	            preparedStatement = connection.prepareStatement(insertExpenseQuery);
	            preparedStatement.setInt(1, s_no);
	            preparedStatement.setDouble(2, price);
	            preparedStatement.setString(3, category);
	            preparedStatement.setString(4, p_date);
	            preparedStatement.setString(5, item);
	            
	            int rowsInserted = preparedStatement.executeUpdate();
	            if (rowsInserted > 0) {
	                System.out.println("Expense added successfully!");
	            }
			}
			
			else if(choice==2) {
				String displayQuery = "select * from expenses";
				preparedStatement = connection.prepareStatement(displayQuery);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
	                double amount = resultSet.getDouble("amount");
	                String category = resultSet.getString("category");
	                String date = resultSet.getString("date");
	                String item = resultSet.getString("item");

	                // Display the values
	                System.out.printf("ID: %d, Amount: %.2f, Category: %s, Date: %s, Item: %s%n", id, amount, category, date, item);
				}
			}
			
			else if(choice==3) {
				System.out.println("Enter the serial number:");
				int serial_no = sc.nextInt();
				String deleteQuery = "delete from expenses where id=?";
				preparedStatement = connection.prepareStatement(deleteQuery);
				preparedStatement.setInt(1, serial_no);
				
				int rowsDeleted = preparedStatement.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("Expense deleted successfully!");
	            }
			}
			
			else if(choice==4) {
				System.out.println("Enter the serial number of row to be updated:");
				int serial_no = sc.nextInt();
				String checkQuery = "select * from expenses where id=?";
				preparedStatement = connection.prepareStatement(checkQuery);
				preparedStatement.setInt(1, serial_no);
				resultSet = preparedStatement.executeQuery();
				
				if(resultSet.next()) {
					System.out.println("What would you like to update?");
					System.out.println("1.Serial No");
					System.out.println("2.Price");
					System.out.println("3.Category");
					System.out.println("4.Date");
					System.out.println("5.Item");
					
					int change_choice = sc.nextInt();
					String updateQuery = "update expenses set ";
					
					switch(change_choice) {
					case 1:
						System.out.println("Enter new serial number:");
						int new_serial = sc.nextInt();
						updateQuery += "id=? where id=?";
						preparedStatement = connection.prepareStatement(updateQuery);
						preparedStatement.setInt(1, new_serial);
						preparedStatement.setInt(2, serial_no);
						
						int rowUpdated = preparedStatement.executeUpdate();
						if(rowUpdated>0) {
							System.out.println("Serial Number updated successfully");
						}
						break;
						
					case 2:
						System.out.println("Enter new price:");
						int new_price = sc.nextInt();
						updateQuery += "amount=? where id=?";
						preparedStatement = connection.prepareStatement(updateQuery);
						preparedStatement.setInt(1, new_price);
						preparedStatement.setInt(2, serial_no);
						
						int rowUpdated1 = preparedStatement.executeUpdate();
						if(rowUpdated1>0) {
							System.out.println("Price amount updated successfully");
						}
						break;
						
					case 3:
						System.out.println("Enter new category:");
						String new_category = sc.nextLine();
						updateQuery += "category=? where id=?";
						preparedStatement = connection.prepareStatement(updateQuery);
						preparedStatement.setString(1, new_category);
						preparedStatement.setInt(2, serial_no);
						
						int rowUpdated2 = preparedStatement.executeUpdate();
						if(rowUpdated2>0) {
							System.out.println("Category updated successfully");
						}
						break;
						
					case 4:
						System.out.println("Enter new date(YYYY-MM-DD):");
						String new_date = sc.nextLine();
						updateQuery += "date=? where id=?";
						preparedStatement = connection.prepareStatement(updateQuery);
						preparedStatement.setString(1, new_date);
						preparedStatement.setInt(2, serial_no);
						
						int rowUpdated3 = preparedStatement.executeUpdate();
						if(rowUpdated3>0) {
							System.out.println("Date updated successfully");
						}
						break;
						
					case 5:
						System.out.println("Enter new item name:");
						String new_item = sc.nextLine();
						updateQuery += "item=? where id=?";
						preparedStatement = connection.prepareStatement(updateQuery);
						preparedStatement.setString(1, new_item);
						preparedStatement.setInt(2, serial_no);
						
						int rowUpdated4 = preparedStatement.executeUpdate();
						if(rowUpdated4>0) {
							System.out.println("Price amount updated successfully");
						}
						break;
						
					default:
						System.out.println("Invalid choice");
						break;
					}
				}
				else {
					System.out.println("No records found for the Id");
				}
			}
			else if(choice==5) {
				String totalExpenses = "select sum(amount) as total_expenses from expenses";
				preparedStatement = connection.prepareStatement(totalExpenses);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					System.out.println("Total Expense: "+resultSet.getDouble("total_expenses"));
				}
				
				String highestExpense = "select max(amount) as highest_expense from expenses";
				preparedStatement = connection.prepareStatement(highestExpense);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					System.out.println("Highest Expense: "+resultSet.getDouble("highest_expense"));
				}
				String lowestExpense = "select min(amount) as lowest_price from expenses";
				preparedStatement = connection.prepareStatement(lowestExpense);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					System.out.println("Lowest Expense: "+resultSet.getDouble("lowest_price"));
				}
			}
			else {
				System.out.println("Thank you for using Expense Tracker!!");
			}
		}
		
		sc.close();
		
	}

}
