import java.util.Scanner;

class Orders {
	Users user=new Users();
	Products product=new Products();
	Scanner sc=new Scanner(System.in);
	public Users getUser() {
		return user;
	}
	public Products getProduct() {
		return product;
	}
	public void adduser() {
		System.out.println("Name");
		user.setName(sc.nextLine());
		System.out.println("Email");
		user.setEmail(sc.next());
		System.out.println("Mobile");
		user.setMobile(sc.nextLong());
		System.out.println("Password");
		user.setPassword(sc.next());
	}
	public void login() {
		System.out.println("Email");
		user.setEmail(sc.next());
		System.out.println("Password");
		user.setPassword(sc.next());
	}
	public void cart() {
		System.out.println("Enter product name");
		product.setPname(sc.next());
		
	}
}
