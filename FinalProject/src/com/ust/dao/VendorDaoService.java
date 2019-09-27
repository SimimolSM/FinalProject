package com.ust.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ust.beans.User;
import com.ust.beans.VendorContactDetails;

public class VendorDaoService {
	
	JdbcTemplate template;
	
	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}



//To get all vendors
	public List<VendorContactDetails> getVendor() {
		return template
				.query("select vendor_id,vendor_name,address,location,service,pincode from vendor where isActive=0",
						new RowMapper<VendorContactDetails>() {
							public VendorContactDetails mapRow(ResultSet rs, int row)
									throws SQLException {
								VendorContactDetails vc = new VendorContactDetails();
								vc.setVendorId(rs.getInt(1));
								vc.setVendorName(rs.getString(2));
								vc.setAddress(rs.getString(3));
								vc.setLocation(rs.getString(4));
								vc.setService(rs.getString(5));
								vc.setPincode(rs.getInt(6));
								return vc;
							}
						});
	}
	
	//---------Add vendors
	
		public int saveVendor(VendorContactDetails vc) {

			String sql1 = "insert into vendor(vendor_name,address,location,service,pincode,isActive) values "
					+ "('"
					+ vc.getVendorName()
					+ "','"
					+ vc.getAddress()
					+ "','"
					+ vc.getLocation()
					+ "','"
					+ vc.getService()
					+ "','"
					+ vc.getPincode()
					+ "',"
					+ 0
					+ ")";

			 template.update(sql1);
			 
			 Integer maxId = getSequence();
			 String sql2="insert into contactdetails(vendor_id,name,dept,email,phone) values ("
					 + maxId
						+ ",'"
						+ vc.getName()
						+ "','"
						+ vc.getDept()
						+ "','"
						+ vc.getEmail()
						+ "','" + vc.getPhone() + "')";
			 return template.update(sql2);

					 
			 
		}
	
		//---------- to get vendor id
		private Integer getSequence() {
			Integer seq;
			String sql = "select MAX(vendor_id)from vendor";
			seq = template.queryForObject(sql, new Object[] {}, Integer.class);
			return seq;
		}
		
	
		//-------------- update vendor
		public int updateVendor(int vendor_id, VendorContactDetails vc) {

			String sql = "update vendor set vendor_name='" + vc.getVendorName()
					+ "' ,address='" + vc.getAddress() + "' ,location='"
					+ vc.getLocation() + "',service='" + vc.getService() + "',pincode='" + vc.getPincode() + "',isActive=" + vc.getIsActive() + " "
					+ "where vendor_id =" + vendor_id;
			template.update(sql);

			// Integer maxId = getSequence();

			String sql1 = "update contactdetails set vendor_id=" + vendor_id + ",name='"
					+ vc.getName() + "',dept='"
					+ vc + "',email='"
					+ vc.getEmail() + "',phone='" + vc.getPhone() + "'where cont_id = " + vc.getContactId();

			return template.update(sql1);
		}
		
		//------------get vendor details by id
				public VendorContactDetails getVendorById(int vendor_id) {
					String sql= "select vc.vendor_id,vc.vendor_name,vc.address,vc.location,vc.service,vc.pincode,cd.name,cd.dept,cd.email,cd.phone,cd.cont_id from vendor vc join"
							+ " contactdetails cd on vc.vendor_id=cd.vendor_id where vc.isActive=0 and vc.vendor_id='"+ vendor_id + "'";
					return template.queryForObject(sql, new Object [] {}, new BeanPropertyRowMapper<VendorContactDetails>(VendorContactDetails.class));
							
				}
				//--------------------UST LOGIN---------------------//
				
				public User selectRole(String username, String password) {
					String sql = "select user_id from login where username='"
							+ username
							+ "' and password='" + password + "'";
					return template.queryForObject(sql, new Object[] {},
							new BeanPropertyRowMapper<User>(User.class));
				}
				//--------------- to disable a vendor
				public int disableVendor(int vId) {

				String sql = "update vendor set isActive='1' where vendor_id=" + vId
				+ "";

				return template.update(sql);
				}
				
				//-------------- get contactdetails by id

				public List<VendorContactDetails> getContactDetailsByVId(int vid) {
				return template.query("select cont_id,name,dept,vendor_id,email,phone from contactdetails where vendor_id="+vid+"", new RowMapper<VendorContactDetails>() {
					public VendorContactDetails mapRow(ResultSet rs, int row)
							throws SQLException {
						VendorContactDetails vc = new VendorContactDetails();
						vc.setContactId(rs.getInt(1));
						vc.setName(rs.getString(2));
						vc.setDept(rs.getString(3));
						vc.setVendorId(rs.getInt(4));
						vc.setEmail(rs.getString(5));
						vc.setPhone(rs.getString(6));
						return vc;
					}
				});
				}
			
}
