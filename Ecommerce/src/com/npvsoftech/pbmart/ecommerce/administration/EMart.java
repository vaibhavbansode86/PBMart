package com.npvsoftech.pbmart.ecommerce.administration;

import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import com.npvsoftech.pbmart.ecommerce.design.Format1;
import com.npvsoftech.pbmart.ecommerce.registration.CustomerManagement;

public class EMart {
	public static Scanner sc = new Scanner(System.in);






	static {

		System.out.println(StringUtils.center(StringUtils.center("Welcome To PBMart", 130 - 112), 130, "*"));
	}
	public static void afterLogedInCust(int customer_id) throws InterruptedException {
		CustomerFuctionality cF = new CustomerFuctionality();
		cF.displayProducts();
		char ch = 'y';
		while (ch == 'y') {
			Format1.lineFormatting();
			System.out.print("\nSelect product Id for adding the product into CART : ");
			int product_id = sc.nextInt();
			sc.nextLine();
			System.out.print("\nEnter the Quantity : ");
			int quantity = sc.nextInt();
			sc.nextLine();
			cF.addToCart(product_id, customer_id, quantity);
			System.out.print("\nDo you want to add more product into CART (y/n) : ");

			ch = sc.next().charAt(0);
		}
		Format1.doubleLineFormatting();
		char ch1 = 'y';
		while (ch1 == 'y') {

			cF.displayBill(customer_id);

			System.out.print("\n1.To Buy the products ");
			System.out.print("\n\n2.To romove the products from CART  ");
			System.out.print("\n\nEnter your choice: ");
			int i = Integer.parseInt(sc.next());

			if (i == 1) {
				cF.placeorder(customer_id);
				if (CustomerFuctionality.flag == 1) {
					CustomerFuctionality.flag = 0;
					Format1.doubleLineFormatting();
					System.out.println("\nYour order is placed successfully....");
					Format1.doubleLineFormatting();

				} else if (CustomerFuctionality.flag == 0) {
					Format1.lineFormatting();
					Thread.sleep(1000);
					System.err
							.println("\nYou don't have anything in your CART." + "\n\nThank you for visiting PB-MART.");
					Format1.lineFormatting();
				}
				break;

			} else if (i == 2 && CustomerFuctionality.flag == 1) {
				CustomerFuctionality.flag = 0;
				Format1.lineFormatting();

				System.out.print("\nSelect product Id for removing the product from CART : ");
				int product_id = Integer.parseInt(sc.next());
				cF.removeFromCart(product_id, customer_id);

			} else if (CustomerFuctionality.flag == 0) {
				System.err.println("\nCART is empty...");
				Format1.lineFormatting();

			}
		}

	}

	public static void operateCustRequirement() {
		int flag1 = 0;
		char ch = 'y';
		CustomerManagement cm = new CustomerManagement();
		while (true) {

			System.out.println("\n1. Login \t2.Sign up \t3.Forgot Password");
			Format1.lineFormatting();
			System.out.print("\nEnter your choice :");
			ch = sc.next().charAt(0);
			Format1.lineFormatting();

			switch (ch) {
			case '1':
				try {

					if (cm.login()) {
						Format1.doubleLineFormatting();
						System.out.println("\nYou are successfully loged in .......");
						Format1.doubleLineFormatting();
						while (true) {
							afterLogedInCust(cm.user_id);
							System.out.print("\nLog Out (y/n) : ");
							char at = sc.next().charAt(0);
							if (at == 'y') {
								flag1 = 1;
								break;
							}
						}
					} else {
						System.err.println("\nInvalid User Id or Password...");

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case '2':
				try {
					cm.addNewCustomer();
					if (cm.login()) {
						Format1.doubleLineFormatting();
						System.out.println("\nYou are successfully loged in .......");
						Format1.doubleLineFormatting();
						while (true) {
							afterLogedInCust(cm.user_id);
							Thread.sleep(1000);
							System.out.print("\nLog Out (y/n) : ");
							char at = sc.next().charAt(0);
							if (at == 'y') {
								flag1 = 1;
								break;
							}
						}
					} else {
						System.err.println("\nInvalid User Id or Password...");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case '3':
				try {

					cm.forgotPassword();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				System.err.println("\nInvalid option.");

			}
			if (flag1 == 1) {
				break;
			}
		}
	}

	public static void main(String[] args) {
		Admin ad = new Admin();
		char ch = 'y';
		int mainFlag = 0;
		while (ch == 'y') {
			System.out.println("1.Customer  \n\n2.Admin");
			Format1.lineFormatting();
			System.out.print("\nEnter your choice : ");
			char choice = sc.next().charAt(0);
			Format1.doubleLineFormatting();

			switch (choice) {
			case '1':

				operateCustRequirement();
				Format1.starLineFormatting();
				mainFlag = 1;
				break;

			case '2':
				if (ad.login()) {
					while (true) {
						System.out.println("\n1-Display product quantity by product id."
								+ "\n2-Display product list.\n3-Add product quantity."
								+ "\n4-Display customer list.\n5-Display customer history.");
						System.out.print("\nEnter your option: ");
						int i = Integer.parseInt(sc.next());
						Format1.doubleLineFormatting();
						switch (i) {
						case 1:
							System.out.print("\nEnter the product id : ");
							int product_id = Integer.parseInt(sc.next());
							ad.displayProductQuantity(product_id);
							break;
						case 2:
							ad.displayProductQuantity();
							break;
						case 3:
							System.out.print("\nEnter the product id : ");
							int p_id = Integer.parseInt(sc.next());
							System.out.print("\nEnter the quantity to add : ");
							int new_qty = Integer.parseInt(sc.next());
							ad.updateQuantity(new_qty, p_id);
							break;
						case 4:
							ad.displayCustomerList();
							break;
						case 5:
							System.out.print("\nEnter the customer id : ");
							int c_id = Integer.parseInt(sc.next());
							ad.displayPurchaseHistory(c_id);
							break;
						default:
							System.err.println("\nInvalid input");
						}
						Format1.doubleLineFormatting();
						System.out.print("\nLog out (y/n) : ");
						char out = sc.next().charAt(0);
						Format1.doubleLineFormatting();
						if (out == 'y') {
							ch = 'n';
							break;
						}

					}
				} else {

				}

			}
			if (mainFlag == 1) {

				break;
			}
		}
		System.out.println("\nEnd of application.....\n");
		Format1.starLineFormatting();

	}

}
