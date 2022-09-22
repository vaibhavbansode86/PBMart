package com.npvsoftech.pbmart.ecommerce.administration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.npvsoftech.pbmart.ecommerce.design.Format1;
import com.npvsoftech.pbmart.ecommerce.registration.ConnectToMysql;
import com.npvsoftech.pbmart.ecommerce.util.Moniterable;

public class Admin extends AdminAuthentication implements Moniterable {

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
	public void displayPurchaseHistory(int customer_id) {
		ArrayList<String> product = new ArrayList<String>();

		Connection con = null;
		PreparedStatement ps = null;

		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement("Select * from minipro.purchase_history where customer_id=" + customer_id + ";");
			ResultSet rs = ps.executeQuery();

			ps = con.prepareStatement("Select product from minipro.product_info ;");
			ResultSet pi = ps.executeQuery();

			ps = con.prepareStatement("Select * from minipro.customerdetails where customer_id=" + customer_id + ";");
			ResultSet ci = ps.executeQuery();

			if (ci.next()) {

				Format1.purchaseHistoryHeading(((Integer) ci.getInt(1)).toString(), ci.getString(2), ci.getString(3),
						ci.getString(5), ((Integer) ci.getInt(8)).toString());

			}

			while (pi.next()) {

				product.add(pi.getString(1));

			}

			Format1.purchaseHistoryBorder();
			Format1.purchaseHistoryFormat("Product Id", "Product Name", "Quntity", "Date", "Time");
			Format1.purchaseHistoryBorder();

			while (rs.next()) {

				Format1.purchaseHistoryFormat(((Integer) rs.getInt(1)).toString(), product.get(rs.getInt(1) - 1),
						((Integer) rs.getInt(3)).toString(), rs.getDate(4).toString(), rs.getTime(5).toString());
				Format1.purchaseHistoryBorder();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void displayProductQuantity(int product_id) {
		Connection con = null;
		PreparedStatement ps = null;
		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement(
					"Select * from minipro.product_info where product_info.product_id=" + product_id + ";");

			ResultSet rs = ps.executeQuery();

			Format1.adminProductListHeading();
			Format1.formatAdminProductBorder();
			Format1.formatedAdminProductList("product_id", "product_Name", "description", "price", "quantity");
			Format1.formatAdminProductBorder();
			while (rs.next()) {

				Format1.formatedAdminProductList(((Integer) rs.getInt(1)).toString(), rs.getString(2), rs.getString(3),
						((Integer) rs.getInt(4)).toString(), ((Integer) rs.getInt(5)).toString());
				Format1.formatAdminProductBorder();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void displayCustomerList() {
		Connection con = null;
		PreparedStatement ps = null;
		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement("Select * from minipro.customerdetails;");

			ResultSet rs = ps.executeQuery();

			Format1.formatAdminCustomerHeading();
			Format1.formatAdminCustomerBorder();
			Format1.formatedAdminCustomerList("Customer_Id", "First Name", "Last Name", "Birth date", "City",
					"District", "State", "Pincode", "Email Id");
			Format1.formatAdminCustomerBorder();

			while (rs.next()) {

				Format1.formatedAdminCustomerList(((Integer) rs.getInt(1)).toString(), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
						((Integer) rs.getInt(8)).toString(), rs.getString(9));
				Format1.formatAdminCustomerBorder();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int updateQuantity(int newQuantity, int pro_id) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;
		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement(

					"update minipro.product_info set quantity=quantity+ " + newQuantity + " where (product_id=" + pro_id
							+ ");");

			rs = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public void displayProductQuantity() {
		Connection con = null;
		PreparedStatement ps = null;
		con = getConnect();
		try {
			con = getConnect();
			ps = con.prepareStatement("Select * from minipro.product_info ;");

			ResultSet rs = ps.executeQuery();

			Format1.adminProductListHeading();
			Format1.formatAdminProductBorder();
			Format1.formatedAdminProductList("product_id", "product_Name", "description", "price", "quantity");
			Format1.formatAdminProductBorder();
			while (rs.next()) {

				Format1.formatedAdminProductList(((Integer) rs.getInt(1)).toString(), rs.getString(2), rs.getString(3),
						((Integer) rs.getInt(4)).toString(), ((Integer) rs.getInt(5)).toString());
				Format1.formatAdminProductBorder();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
