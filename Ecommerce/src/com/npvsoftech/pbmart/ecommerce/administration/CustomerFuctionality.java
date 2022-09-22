package com.npvsoftech.pbmart.ecommerce.administration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.npvsoftech.pbmart.ecommerce.design.Format1;
import com.npvsoftech.pbmart.ecommerce.registration.ConnectToMysql;
import com.npvsoftech.pbmart.ecommerce.util.Operatable;

public class CustomerFuctionality implements Operatable {

	static int flag = 0;

	public Connection getConnect() {

		ConnectToMysql obj1 = new ConnectToMysql();
		Connection con = null;
		try {
			con = obj1.connect();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return con;
	}

	@Override
	public void displayProducts() {
		Connection con = null;
		PreparedStatement ps = null;
		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement("Select * from minipro.product_info;");

			ResultSet rs = ps.executeQuery();

			Format1.customerProductListHeading();
			Format1.formatCustomerProductBorder();
			Format1.formatedCustomerProductList("product_id", "product_Name", "description", "price");
			Format1.formatCustomerProductBorder();
			while (rs.next()) {

				Format1.formatedCustomerProductList(((Integer) rs.getInt(1)).toString(), rs.getString(2),
						rs.getString(3), ((Integer) rs.getInt(4)).toString());
				Format1.formatCustomerProductBorder();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int addToCart(int product_id, int customer_id, int quantity) {
		Connection con = null;
		PreparedStatement ps = null;
		int ext = 0;
		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement("insert into minipro.cart values(?,?,?,"
					+ "quantity*(select price from minipro.product_info where product_id=?),"
					+ "(select product from minipro.product_info where product_id=?));");
			ps.setInt(1, product_id);
			ps.setInt(2, customer_id);
			ps.setInt(3, quantity);
			ps.setInt(4, product_id);
			ps.setInt(5, product_id);

			ext = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Invalid Product id.\nPlease select product id from given product list.");
		}
		return ext;

	}

	@Override
	public int generateBill(int customer_id) {

		Connection con = null;
		PreparedStatement ps = null;
		int sum = 0;
		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement("Select * from minipro.cart where customer_id=" + customer_id + ";");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sum = sum + rs.getInt(4);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;
	}

	@Override
	public int placeorder(int customer_id) {
		Connection con = null;
		PreparedStatement ps = null;
		int c = 0;
		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement(

					"insert into minipro.purchase_history (product_id,customer_id,quantity)"
							+ "(select product_id,customer_id,quantity from minipro.cart where cart.customer_id="
							+ customer_id + ");");
			ps.executeUpdate();
			ps = con.prepareStatement(
					"update minipro.purchase_history set date = current_date() where purchase_history.customer_id="
							+ customer_id + ";");
			ps.executeUpdate();
			ps = con.prepareStatement(
					"update minipro.purchase_history set time = current_time() where purchase_history.customer_id="
							+ customer_id + ";");
			ps.executeUpdate();

		} catch (SQLException e) {
			if (e.getMessage().equals("Check constraint 'product_info_chk_1' is violated.")) {
				System.err.println("Entered quantity exceeds the limit of available stock...");
				System.err.println("Your request can't be proceed...");

			}
		}
		try {
			ps = con.prepareStatement("delete from minipro.cart where customer_id=" + customer_id + ";");
			c = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}

	@Override
	public void displayBill(int customer_id) {
		Connection con = null;
		PreparedStatement ps = null;
		con = getConnect();
		try {
			con = getConnect();

			ps = con.prepareStatement("Select * from minipro.cart where customer_id=" + customer_id);
			ResultSet rs = ps.executeQuery();
			Format1.billFormatHeading();

			Format1.billBorder();
			Format1.billFormat("Product Id", "Product Name", "Quntity", "Qty*Price");
			Format1.billBorder();

			while (rs.next()) {
				flag = 1;

				Format1.billFormat(((Integer) rs.getInt(1)).toString(), rs.getString(5),
						((Integer) rs.getInt(3)).toString(), ((Integer) rs.getInt(4)).toString());
				Format1.billBorder();
			}
			int bill = generateBill(customer_id);

			Format1.totalBill(((Integer) bill).toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int removeFromCart(int product_id, int customer_id) {
		Connection con = null;
		PreparedStatement ps = null;
		int ext = 0;
		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement("delete from minipro.cart where product_id=? and customer_id=?;");
			ps.setInt(1, product_id);
			ps.setInt(2, customer_id);

			ext = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ext;
	}

}
