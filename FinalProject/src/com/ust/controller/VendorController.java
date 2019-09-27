package com.ust.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ust.beans.User;
import com.ust.beans.VendorContactDetails;
import com.ust.dao.VendorDaoService;

@RestController
public class VendorController {
	@Autowired
	VendorDaoService dao;
	
	// view staff list
		@RequestMapping(value = "/api/vendors/{vendorName}", method = RequestMethod.GET)
		@ResponseBody
		public List getVendors(Model m, @PathVariable("vendorName") String vendorName ) {
			List list = null;
			if (vendorName.equals("null")) {
				list = dao.getVendor();
			} 
//				else {
//				list = dao.getStaffByName(vendorName);
//			}

			return list;
		}
	
		// Add Vendor
				@ResponseBody
				@RequestMapping(value = "/api/insertvendor", method = RequestMethod.POST)
				public void insertVendor(@RequestBody VendorContactDetails vc) throws ParseException {
					dao.saveVendor(vc);
				}
				
				// update Vendor
				@ResponseBody
				@RequestMapping(value = "/api/updatevendor", method = RequestMethod.PUT)
				public void updateVendor(@RequestBody VendorContactDetails vc) throws ParseException {
					int vendorId = vc.getVendorId();
					int contactId = vc.getContactId();
					dao.updateVendor(vendorId, vc);
				}
				
				// view vendors by id
				@RequestMapping(value = "/api/vendor/{vendorId}", method = RequestMethod.GET)
				@ResponseBody
				public VendorContactDetails getVendorById(Model m, @PathVariable("vendorId") int vendorId) {
					
					return dao.getVendorById(vendorId);
				
				}
				
				//verify login
				@RequestMapping(value = "/api/{username}/{password}", method = RequestMethod.GET)
				@ResponseBody
				public User selectUser(@PathVariable("username") String username,
						@PathVariable("password") String password) {
					return dao.selectRole(username,password);
				}
				
				// to disable a vendor

				@RequestMapping(value = "/api/disablevendor/{vendorId}", method = RequestMethod.PUT)
				@ResponseBody
				public void disableVendor(@PathVariable("vendorId") int vId) {
				dao.disableVendor(vId);
				}

				
}
