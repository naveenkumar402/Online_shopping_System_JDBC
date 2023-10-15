
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class OnlineShoppingApp {

	public static void main(String[] args) throws SQLException{
		Orders order=new Orders();
		Scanner sc=new Scanner(System.in);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineshoppingapplication","root","password");
			PreparedStatement psmt=null;
			ResultSet rst;
			while(true) {
				System.out.println("1.Sign in/Sign up");
				System.out.println("2.View Products");
				System.out.println("3.Add to cart");
				System.out.println("4.Place order");
				System.out.println("5.Exit");
				int choice=sc.nextInt();
				switch(choice) {
				case 1:
					System.out.println("1.Sign up"+"\n"+"2.Sign in");
					int ch=sc.nextInt();
					if(ch==1) {
						order.adduser();
						psmt=con.prepareStatement("insert into user values(?,?,?,?)");
						psmt.setString(1,order.getUser().getEmail());
						psmt.setString(2,order.getUser().getName());
						psmt.setLong(3,order.getUser().getMobile());
						psmt.setString(4,order.getUser().getPassword());
						try {
							psmt.execute();
						}
						catch(SQLIntegrityConstraintViolationException e) {
							System.out.println("User already exists");
						}
						System.out.println("User created successfully");
					}
					else if(ch==2) {
						order.login();
						psmt=con.prepareStatement("select* from user where email=?");
						psmt.setString(1,order.getUser().getEmail());
						rst=psmt.executeQuery();
						if(rst.next()) {
							if(rst.getString(4).equals(order.getUser().getPassword())) {
								order.getUser().setName(rst.getString(2));
								order.getUser().setMobile(rst.getLong(3));
								System.out.println("Login successfull");
							}
							else System.out.println("Incorrect password");
						}
						else System.out.println("User not found");
					}
					break;
				case 2:
					psmt=con.prepareStatement("select distinct category from products");
					rst=psmt.executeQuery();
					while(rst.next()) {
						System.out.println(rst.getString(1));
					}
					psmt=con.prepareStatement("select id,pname,price from products where category=?");
					System.out.println("Select category");
					psmt.setString(1,sc.next().toLowerCase());
					rst=psmt.executeQuery();
					while(rst.next()) {
						System.out.println(rst.getString(1)+" "+rst.getString(2)+" "+rst.getDouble(3));
					}
					break;
				case 3:
					order.cart();
					psmt=con.prepareStatement("select price from products where pname=?");
					psmt.setString(1,order.getProduct().getPname());
					rst=psmt.executeQuery();
					if(rst.next()) {
						order.getProduct().setPrice(rst.getDouble(1));
					}
					psmt=con.prepareStatement("insert into cart values(?,?,?)");
					psmt.setString(1,order.getUser().getEmail());
					psmt.setString(2,order.getProduct().getPname());
					psmt.setDouble(3,order.getProduct().getPrice());
					try {
						psmt.execute();
					}
					catch(SQLIntegrityConstraintViolationException e) {
						System.out.println("login your ID");
					}
					break;
				case 4:
					psmt=con.prepareStatement("select pname,price from cart where email=?");
					psmt.setString(1,order.getUser().getEmail());
					rst=psmt.executeQuery();
					while(rst.next()) {
						System.out.println(rst.getString(1)+" "+rst.getDouble(2));
					}
					psmt=con.prepareStatement("select sum(price) from cart");
					rst=psmt.executeQuery();
					if(rst.next()) System.out.println("Total price:"+rst.getDouble(1));
					System.out.println("Enter delivery address");
					String address=sc.next();
					System.out.println(order.getUser().getName());
					System.out.println(order.getUser().getMobile());
					System.out.println(address);
					psmt=con.prepareStatement("truncate table cart");
					psmt.executeUpdate();
					break;
					
				case 5:
					System.exit(0);
					break;
				default:
					System.out.println("Invalid option");	
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
