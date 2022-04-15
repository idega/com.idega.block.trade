package com.idega.block.trade.stockroom.business;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.block.trade.stockroom.data.PriceCategory;
import com.idega.block.trade.stockroom.data.Reseller;
import com.idega.block.trade.stockroom.data.Supplier;
import com.idega.block.trade.stockroom.data.SupplierStaffGroup;
import com.idega.block.trade.stockroom.data.SupplierStaffGroupBMPBean;
import com.idega.block.trade.stockroom.data.SupplierStaffGroupHome;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
import com.idega.core.accesscontrol.business.LoginDBHandler;
import com.idega.core.accesscontrol.data.ICPermission;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.EmailHome;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneHome;
import com.idega.core.contact.data.PhoneType;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.AddressHome;
import com.idega.core.location.data.AddressType;
import com.idega.core.location.data.AddressTypeHome;
import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.idegaweb.IWUserContext;
import com.idega.user.business.GroupBusiness;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.GroupHome;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;

/**
 * @author gimmi
 */
public class SupplierManagerBusinessBean extends IBOServiceBean implements SupplierManagerBusiness {

	private static final long serialVersionUID = 1145632811223504946L;

	public static String SUPPLIER_MANAGER_GROUP_TYPE_COLLECTION = "supplier_manager_coll";
	public static String SUPPLIER_MANAGER_GROUP_TYPE = "supplier_manager";
	public static String SUPPLIER_MANAGER_USER_GROUP_TYPE = "supplier_manager_user";
	public static String SUPPLIER_MANAGER_ADMIN_GROUP_TYPE = "supplier_manager_admin";
	public static String SUPPLIER_MANAGER_RESELLER_GROUP_TYPE = "supplier_manager_reseller";
	public static String SUPPLIER_MANAGER_SUPPLIER_GROUP_TYPE = "supplier_manager_supplier";
	public static String SUPPLIER_MANAGER_BOOKING_STAFF_TYPE = "supplier_manager_b_staff";
	public static String SUPPLIER_MANAGER_CASHIER_STAFF_TYPE = "supplier_manager_c_staff";
	public static String SUPPLIER_MANAGER_TRAVEL_AGENT_STAFF_TYPE = "supplier_manager_ta_staff";
	public static String SUPPLIER_MANAGER_PARTNER_STAFF_TYPE = "supplier_manager_partner_staff";

	@Override
	public Group updateSupplierManager(Object pk, String name, String description, String email, String phone, String address) throws IDOLookupException, FinderException {
		GroupHome gHome = (GroupHome) IDOLookup.getHome(Group.class);
		Group manager = gHome.findByPrimaryKey(pk);
		manager.setGroupType(SUPPLIER_MANAGER_GROUP_TYPE);
		manager.setName(name);
		manager.setDescription(description);
		manager.store();

		try {
			Collection coll = manager.getEmails();
			if (coll != null && !coll.isEmpty()) {
				Email eemail = (Email)coll.iterator().next();
				eemail.setEmailAddress(email);
				eemail.store();
			} else {
				EmailHome eHome = (EmailHome) IDOLookup.getHome(Email.class);
				Email eemail = eHome.create();
				eemail.setEmailAddress(email);
				eemail.store();
				manager.addEmail(eemail);
			}

			coll = manager.getPhones();
			if (coll != null && !coll.isEmpty()) {
				Phone pphone = (Phone)coll.iterator().next();
				pphone.setNumber(phone);
				pphone.store();
			} else {
				PhoneHome pHome = (PhoneHome) IDOLookup.getHome(Phone.class);
				Phone pphone = pHome.create();
				pphone.setNumber(phone);
				pphone.setPhoneTypeId(PhoneType.HOME_PHONE_ID);
				pphone.store();
				manager.addPhone(pphone);
			}

			AddressTypeHome atHome = (AddressTypeHome) IDOLookup.getHome(AddressType.class);
			AddressType at1 = atHome.findAddressType1();
			coll = manager.getAddresses(at1);
			if (coll != null && !coll.isEmpty()) {
				Address aaddress = (Address)coll.iterator().next();
				aaddress.setStreetName(address);
				aaddress.store();
			} else {
				AddressHome aHome = (AddressHome) IDOLookup.getHome(Address.class);
				Address aaddress = aHome.create();
				aaddress.setStreetName(address);
				aaddress.setAddressType(at1);
				aaddress.store();
				manager.addAddress(aaddress);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return manager;
	}

	@Override
	public User createSupplierManagerStaff(Group supplierManager, String userType, String name, String email, String loginName, String password) throws RemoteException, CreateException {
		try {
			// Making sure the group type exist
			getGroupBusiness().getGroupTypeFromString(userType);
		} catch (FinderException e1) {
			System.out.println("TravelBlock : groupType not found, creating");
			getGroupBusiness().createVisibleGroupType(userType);
		}

		Group staffGroup = null;
		if (userType.equals(SUPPLIER_MANAGER_BOOKING_STAFF_TYPE)) {
			staffGroup = getSupplierManagerBookingStaffGroup(supplierManager);
		} else if (userType.equals(SUPPLIER_MANAGER_ADMIN_GROUP_TYPE)) {
			staffGroup = getSupplierManagerAdminGroup(supplierManager);
		} else if (userType.equals(SUPPLIER_MANAGER_CASHIER_STAFF_TYPE)) {
			staffGroup = getSupplierManagerCashierStaffGroup(supplierManager);
		} else if (userType.equals(SUPPLIER_MANAGER_TRAVEL_AGENT_STAFF_TYPE)) {
			staffGroup = getSupplierManagerTravelAgentStaffGroup(supplierManager);
		} else if (userType.equals(SUPPLIER_MANAGER_PARTNER_STAFF_TYPE)) {
			staffGroup = getSupplierManagerPartnerGroup(supplierManager);
		}

		if (staffGroup != null) {

			User user;
			UserBusiness ub = (UserBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), UserBusiness.class);

			user = ub.insertUser(name, "", "", name, "staff", null, null, null);
			LoginDBHandler.createLogin(user.getID(), loginName, password);


			if ( email != null ) {
				try {
					EmailHome eHome = (EmailHome) IDOLookup.getHome(Email.class);
					Email eemail = eHome.create();
					eemail.setEmailAddress(email);
					eemail.store();
					user.addEmail(eemail);
				} catch (IDOAddRelationshipException e) {
					e.printStackTrace();
				}
			}

			staffGroup.addGroup(user);
			user.setPrimaryGroup(staffGroup);
			user.store();

			return user;
		} else {
			throw new CreateException("Could not find the staffgroup");
		}

	}

	@Override
	public User createSupplierManagerBookingStaff(Group supplierManager, String name, String loginName, String password) throws RemoteException, CreateException {
		try {
			// Making sure the group type exist
			getGroupBusiness().getGroupTypeFromString(SUPPLIER_MANAGER_BOOKING_STAFF_TYPE);
		} catch (FinderException e1) {
			System.out.println("TravelBlock : groupType not found, creating");
			getGroupBusiness().createVisibleGroupType(SUPPLIER_MANAGER_BOOKING_STAFF_TYPE);
		}

		Group bookingStaffGroup = getSupplierManagerBookingStaffGroup(supplierManager);
		if (bookingStaffGroup != null) {

			User user;
			UserBusiness ub = (UserBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), UserBusiness.class);

			user = ub.insertUser(name, "", "", name, "staff", null, null, null);
			LoginDBHandler.createLogin(user.getID(), loginName, password);

			bookingStaffGroup.addGroup(user);
			user.setPrimaryGroup(bookingStaffGroup);
			user.store();

			return user;
		} else {
			throw new CreateException("Could not find the staffgroup");
		}

	}

	@Override
	public Email getSupplierManagerEmail(Group supplierManager) {
		Collection coll = supplierManager.getEmails();
		if (coll != null && !coll.isEmpty()) {
			return (Email)coll.iterator().next();
		}
		return null;
	}


	@Override
	public Group createSupplierManager(String name, String description, String email, String phone, String address, String adminName, String loginName, String password, IWUserContext iwuc) throws RemoteException, CreateException, EJBException, FinderException {
		try {
			// Making sure the group type exist
			getGroupBusiness().getGroupTypeFromString(SUPPLIER_MANAGER_GROUP_TYPE);
		} catch (FinderException e1) {
			System.out.println("TravelBlock : groupType not found, creating");
			getGroupBusiness().createVisibleGroupType(SUPPLIER_MANAGER_GROUP_TYPE);
		}
		try {
			// Making sure the group type exist
			getGroupBusiness().getGroupTypeFromString(SUPPLIER_MANAGER_USER_GROUP_TYPE);
		} catch (FinderException e1) {
			System.out.println("TravelBlock : groupType not found, creating");
			getGroupBusiness().createVisibleGroupType(SUPPLIER_MANAGER_USER_GROUP_TYPE);
		}
		try {
			// Making sure the group type exist
			getGroupBusiness().getGroupTypeFromString(SUPPLIER_MANAGER_ADMIN_GROUP_TYPE);
		} catch (FinderException e1) {
			System.out.println("TravelBlock : groupType not found, creating");
			getGroupBusiness().createVisibleGroupType(SUPPLIER_MANAGER_ADMIN_GROUP_TYPE);
		}
		try {
			// Making sure the group type exist
			getGroupBusiness().getGroupTypeFromString(SUPPLIER_MANAGER_SUPPLIER_GROUP_TYPE);
		} catch (FinderException e1) {
			System.out.println("TravelBlock : groupType not found, creating");
			getGroupBusiness().createVisibleGroupType(SUPPLIER_MANAGER_SUPPLIER_GROUP_TYPE);
		}
		try {
			// Making sure the group type exist
			getGroupBusiness().getGroupTypeFromString(SUPPLIER_MANAGER_RESELLER_GROUP_TYPE);
		} catch (FinderException e1) {
			System.out.println("TravelBlock : groupType not found, creating");
			getGroupBusiness().createVisibleGroupType(SUPPLIER_MANAGER_RESELLER_GROUP_TYPE);
		}
		try {
			// Making sure the group type exist
			getGroupBusiness().getGroupTypeFromString(SUPPLIER_MANAGER_BOOKING_STAFF_TYPE);
		} catch (FinderException e1) {
			System.out.println("TravelBlock : groupType not found, creating");
			getGroupBusiness().createVisibleGroupType(SUPPLIER_MANAGER_BOOKING_STAFF_TYPE);
		}
		try {
			// Making sure the group type exist
			getGroupBusiness().getGroupTypeFromString(SUPPLIER_MANAGER_CASHIER_STAFF_TYPE);
		} catch (FinderException e1) {
			System.out.println("TravelBlock : groupType not found, creating");
			getGroupBusiness().createVisibleGroupType(SUPPLIER_MANAGER_CASHIER_STAFF_TYPE);
		}

		User user;
		UserBusiness ub = (UserBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), UserBusiness.class);

		if (adminName == null || "".equals(adminName)) {
			adminName = name;
		}

		user = ub.insertUser(adminName, "", "", adminName, "staff", null, null, null);
		LoginDBHandler.createLogin(user.getID(), loginName, password);

		Group manager = getGroupBusiness().createGroup(name, description, SUPPLIER_MANAGER_GROUP_TYPE, false);
		Group users = getGroupBusiness().createGroup("Users", "User group for "+name, SUPPLIER_MANAGER_USER_GROUP_TYPE, false);
		Group admin = getGroupBusiness().createGroup("Managers", "Manager group for "+name, SUPPLIER_MANAGER_ADMIN_GROUP_TYPE, false);
		Group resellers = getGroupBusiness().createGroup("Resellers", "Resellers belonging to "+name, SUPPLIER_MANAGER_RESELLER_GROUP_TYPE, false);
		Group suppliers = getGroupBusiness().createGroup("Suppliers", "Suppliers belonging to "+name, SUPPLIER_MANAGER_SUPPLIER_GROUP_TYPE, false);

		getSupplierManagerGroup().addGroup(manager);

		manager.addGroup(users);
		manager.addGroup(suppliers);
		manager.addGroup(resellers);

		users.addGroup(admin);
		admin.addGroup(user);
		user.setPrimaryGroup(admin);
		user.store();
		try {
			getIWMainApplication().getAccessController().setAsOwner(manager, Integer.parseInt(user.getPrimaryKey().toString()), getIWApplicationContext());
		}
		catch (NumberFormatException e2) {
			e2.printStackTrace();
		}
		catch (EJBException e2) {
			e2.printStackTrace();
		}
		catch (Exception e2) {
			e2.printStackTrace();
		}
	  	getIWMainApplication().getAccessController().addRoleToGroup(TradeConstants.SUPPLIER_MANAGER_ROLE_KEY, admin, getIWApplicationContext());
	  	updateSupplierManager(manager.getPrimaryKey(), name, description, email, phone, address);
	  	return manager;
	}

	/**
	 * Add/Remove a role from SupplierManager
	 * @param supplierManager The SupplierManager
	 * @param role The role
	 * @param setRole if true the role will be set, if false the role will be removed
	 * @param iwuc IWUserContext
	 * @throws RemoteException
	 */
	@Override
	public void setRole(Group supplierManager, String role, boolean setRole) throws RemoteException {
		Group adminGroup = getSupplierManagerAdminGroup(supplierManager);
		if (adminGroup != null) {
			if (setRole) {
				getIWMainApplication().getAccessController().addRoleToGroup(role, adminGroup, getIWApplicationContext());
			} else {
				getIWMainApplication().getAccessController().removeRoleFromGroup(role, Integer.valueOf(adminGroup.getId()), getIWApplicationContext());
			}
		}
	}

	/**
	 * Add/Remove a role from SupplierManager
	 * @param supplierManager The SupplierManager
	 * @param role The role
	 * @param setRole if true the role will be set, if false the role will be removed
	 * @param iwuc IWUserContext
	 * @throws RemoteException
	 * @throws FinderException
	 */
	@Override
	public void setRole(Supplier supplier, String role, boolean setRole) throws RemoteException, FinderException {
		Group adminGroup = getPermissionGroup(supplier);
		if (adminGroup != null) {
			if (setRole) {
				getIWMainApplication().getAccessController().addRoleToGroup(role, adminGroup, getIWApplicationContext());
			} else {
				getIWMainApplication().getAccessController().removeRoleFromGroup(role, Integer.valueOf(adminGroup.getId()), getIWApplicationContext());
			}
		}
	}

	/**
	 * Returns all roles for the admin group of this supplierManager
	 * @param supplierManager
	 * @return A collection of ICPermission objects
	 * @throws RemoteException
	 */
	@Override
	public Collection getRoles(Group supplierManager) throws RemoteException {
		Group adminGroup = getSupplierManagerAdminGroup(supplierManager);
		if (adminGroup != null) {
			if (true) {
				Collection coll = getIWMainApplication().getAccessController().getAllRolesForGroup(adminGroup);
				ICPermission toRemove = null;
				Iterator iter = coll.iterator();
				while (iter.hasNext()) {
					toRemove = (ICPermission) iter.next();
					if (toRemove.getPermissionString().equals(TradeConstants.SUPPLIER_MANAGER_ROLE_KEY)) {
						break;
					}
				}

				if (toRemove != null) {
					coll.remove(toRemove);
				}
				return coll;
			} else {
				return getIWMainApplication().getAccessController().getAllRolesForGroup(adminGroup);
			}
		}
		return null;
	}

	@Override
	public boolean hasRole(Group supplierManager, String role) throws RemoteException {
		Group adminGroup = getSupplierManagerAdminGroup(supplierManager);
		if (adminGroup != null) {
			Collection coll = getIWMainApplication().getAccessController().getAllRolesForGroup(adminGroup);
			ICPermission p = null;
			Iterator iter = coll.iterator();
			while (iter.hasNext()) {
				p = (ICPermission) iter.next();
				if (p.getPermissionString().equals(role)) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * Returns all roles for the admin group of this supplierManager
	 * @param supplierManager
	 * @return A collection of Strings
	 * @throws RemoteException
	 */
	@Override
	public Collection getRolesAsString(Group supplierManager) throws RemoteException {
		Collection roles = getRoles(supplierManager);
		if (roles != null) {
			Vector v = new Vector();
			ICPermission role;
			Iterator iter = roles.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				role = (ICPermission) obj;
				v.add(role.getPermissionString());
			}
			return v;
		}
		return null;
	}

	@Override
	public Collection getRoles(Supplier supplier) throws RemoteException, FinderException {
		Group group = getPermissionGroup(supplier);
		if (group != null) {
			return getIWMainApplication().getAccessController().getAllRolesForGroup(group);
		}
		return null;
	}

	@Override
	public Collection getRolesAsString(Supplier supplier) throws RemoteException, FinderException {
		Collection roles = getRoles(supplier);
		if (roles != null) {
			Vector v = new Vector();
			ICPermission role;
			Iterator iter = roles.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				role = (ICPermission) obj;
				v.add(role.getPermissionString());
			}
			return v;
		}
		return null;
	}

	@Override
	public Collection getSupplierManagerAdmins(Group supplierManager) throws RemoteException, FinderException {
		Group adminGroup = getSupplierManagerAdminGroup(supplierManager);
		if (adminGroup != null) {
			return getGroupBusiness().getUsers(adminGroup);
		}
		return null;
	}

	private Group getSupplierManagerUserGroup(Group supplierManager) throws RemoteException {
		Collection coll = getGroupBusiness().getChildGroups(supplierManager, new String[]{SUPPLIER_MANAGER_USER_GROUP_TYPE}, true);
		if (coll != null && !coll.isEmpty()) {
			Iterator iter = coll.iterator();
			return (Group) iter.next();
		}
		return null;
	}

	@Override
	public Collection getStaffGroupTypes(Group supplierManager) throws RemoteException {
		Collection coll = getGroupBusiness().getChildGroups(getSupplierManagerUserGroup(supplierManager));
		Collection v = new Vector();
		Iterator iter = coll.iterator();
		while (iter.hasNext()) {
			Group g = (Group) iter.next();
			v.add(g.getGroupType());

		}
		return v;
	}

	@Override
	public Integer getGroupIDFromGroupType(Group supplierManager, String grouptype) throws RemoteException{
		Group staffGroup = getGroupFromGroupType(supplierManager, grouptype);
		Integer groupid = new Integer(staffGroup.getPrimaryKey().toString());
		return groupid;
	}

	@Override
	public Group getGroupFromGroupType(Group supplierManager, String grouptype) throws RemoteException{
		Group staffGroup = null;
		if(grouptype.equals(SUPPLIER_MANAGER_BOOKING_STAFF_TYPE)) {
			staffGroup = getSupplierManagerBookingStaffGroup(supplierManager);
		} else if (grouptype.equals(SUPPLIER_MANAGER_ADMIN_GROUP_TYPE)) {
			staffGroup = getSupplierManagerAdminGroup(supplierManager);
		} else if (grouptype.equals(SUPPLIER_MANAGER_TRAVEL_AGENT_STAFF_TYPE)) {
			staffGroup = getSupplierManagerTravelAgentStaffGroup(supplierManager);
		} else if (grouptype.equals(SUPPLIER_MANAGER_CASHIER_STAFF_TYPE)) {
			staffGroup = getSupplierManagerCashierStaffGroup(supplierManager);
		} else if (grouptype.equals(SUPPLIER_MANAGER_PARTNER_STAFF_TYPE)) {
			staffGroup = getSupplierManagerPartnerGroup(supplierManager);
		}
		return staffGroup;
	}

	@Override
	public Collection getStaffGroupNames(Group supplierManager) throws RemoteException {
		Collection coll = getGroupBusiness().getChildGroups(getSupplierManagerUserGroup(supplierManager));
		Collection v = new Vector();
		Iterator iter = coll.iterator();
		while (iter.hasNext()) {
			Group g = (Group) iter.next();
			v.add(g.getName());
		}
		return v;
	}

	private void forceCheckGroupTypes(Group supplierManager) throws RemoteException {
		getSupplierManagerBookingStaffGroup(supplierManager);
		getSupplierManagerCashierStaffGroup(supplierManager);
		getSupplierManagerTravelAgentStaffGroup(supplierManager);
		getSupplierManagerPartnerGroup(supplierManager);
	}

	@Override
	public Collection getStaffGroups(Group supplierManager) throws RemoteException {
		forceCheckGroupTypes(supplierManager);
		Collection groups = getGroupBusiness().getChildGroups(getSupplierManagerUserGroup(supplierManager));
		Iterator iter = groups.iterator();
		Collection smRoles = getRoles(supplierManager);
		Iterator smIt = smRoles.iterator();
		Vector v = new Vector();
		while (iter.hasNext()) {
			Group g = (Group) iter.next();
			Collection roles = getIWMainApplication().getAccessController().getAllRolesForGroup(g);
			Iterator rIter = roles.iterator();
			while (rIter.hasNext()) {
				ICPermission r = (ICPermission) rIter.next();
				smIt = smRoles.iterator();
				while (smIt.hasNext() && !v.contains(g)) {
					ICPermission r2 = (ICPermission) smIt.next();
					if (r.getPermissionString().equals(r2.getPermissionString())) {
						v.add(g);
						break;
					}
				}
			}
		}
		return v;
	}

	private Group getSupplierManagerPartnerGroup(Group supplierManager) throws RemoteException {
		return getSupplierManagerStaffGroup(supplierManager, "Partnes", SUPPLIER_MANAGER_PARTNER_STAFF_TYPE, TradeConstants.ROLE_SUPPLIER_MANAGER_PARTNER_STAFF);
	}
	private Group getSupplierManagerTravelAgentStaffGroup(Group supplierManager) throws RemoteException {
		return getSupplierManagerStaffGroup(supplierManager, "Travel Agent staff", SUPPLIER_MANAGER_TRAVEL_AGENT_STAFF_TYPE, TradeConstants.ROLE_SUPPLIER_MANAGER_TRAVEL_AGENT_STAFF);
	}
	private Group getSupplierManagerBookingStaffGroup(Group supplierManager) throws RemoteException {
		return getSupplierManagerStaffGroup(supplierManager, "Booking staff", SUPPLIER_MANAGER_BOOKING_STAFF_TYPE, TradeConstants.ROLE_SUPPLIER_MANAGER_BOOKING_STAFF);
	}
	private Group getSupplierManagerCashierStaffGroup(Group supplierManager) throws RemoteException {
		return getSupplierManagerStaffGroup(supplierManager, "Cashier staff", SUPPLIER_MANAGER_CASHIER_STAFF_TYPE, TradeConstants.ROLE_SUPPLIER_MANAGER_CASHIER_STAFF);
	}

	private Group getSupplierManagerStaffGroup(Group supplierManager, String groupName, String groupType, String role) throws RemoteException {
		Collection coll = getGroupBusiness().getChildGroups(supplierManager, new String[]{SUPPLIER_MANAGER_USER_GROUP_TYPE}, true);
		if (coll != null) {
			Iterator iter = coll.iterator();
			if (iter.hasNext()) {
				Group userGroup = (Group) iter.next();
				Collection coll2 = getGroupBusiness().getChildGroups(userGroup, new String[]{groupType}, true);
				if (coll2 != null && !coll2.isEmpty()) {
					Iterator iter2 = coll2.iterator();
					if (iter2.hasNext()) {
						Group bookingStaffGroup = (Group) iter2.next();
						return bookingStaffGroup;
					}
				} else {
					try {
					Group bookingStaffGroup = getGroupBusiness().createGroup(groupName, groupName+" group for "+supplierManager.getName(), groupType, false);
					userGroup.addGroup(bookingStaffGroup);
					getIWMainApplication().getAccessController().addRoleToGroup(role, bookingStaffGroup, getIWApplicationContext());
					return bookingStaffGroup;
					} catch (CreateException e) {
						e.printStackTrace();
						return null;
					}
				}
			}
			return null;
		}
		return null;
	}

	private Group getSupplierManagerAdminGroup(Group supplierManager) throws RemoteException {
		Collection coll = getGroupBusiness().getChildGroups(supplierManager, new String[]{SUPPLIER_MANAGER_USER_GROUP_TYPE}, true);
		if (coll != null) {
			Iterator iter = coll.iterator();
			if (iter.hasNext()) {
				Group userGroup = (Group) iter.next();
				Collection coll2 = getGroupBusiness().getChildGroups(userGroup, new String[]{SUPPLIER_MANAGER_ADMIN_GROUP_TYPE}, true);
				if (coll2 != null) {
					Iterator iter2 = coll2.iterator();
					if (iter2.hasNext()) {
						Group adminGroup = (Group) iter2.next();
						return adminGroup;
					}
				}
			}
			return null;
		}
		return null;
	}

	@Override
	public Collection findAllSupplierManagers() {
		return getSupplierManagerGroup().getChildren();
	}

	@Override
	public Group getSupplierManagerGroup() {
		try {
			Collection coll = getGroupBusiness().getGroups(new String[] {SUPPLIER_MANAGER_GROUP_TYPE_COLLECTION}, true);
			if (coll != null && coll.size() != 1) {
				if (coll.isEmpty()) {
					try {
						getGroupBusiness().getGroupTypeFromString(SUPPLIER_MANAGER_GROUP_TYPE_COLLECTION);
					} catch (FinderException e) {
						System.out.println("TravelBlock : groupType not found, creating");
						getGroupBusiness().createVisibleGroupType(SUPPLIER_MANAGER_GROUP_TYPE_COLLECTION);
					}
					return getGroupBusiness().createGroup("Supplier Managers", "Group containing group managers", SUPPLIER_MANAGER_GROUP_TYPE_COLLECTION, true);
				} else {
					System.err.println("TravelBlock : "+coll.size()+" supplier manager groups found !!! should only be 1");
					Iterator iter = coll.iterator();
					return (Group) iter.next();
				}
			} else if (coll != null) {
				Iterator iter = coll.iterator();
				return (Group) iter.next();
			} else  {
				System.err.println("TravelBlock : NULL supplier manager groups found !!! should only be 1");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Group getSupplierManager(User user) throws RemoteException {
		return getParentGroupByTypes(user, new String[] {SUPPLIER_MANAGER_GROUP_TYPE});
	}
	
	public Group getSupplierManagerStaff(User user) throws RemoteException {
		return getParentGroupByTypes(user, new String[] {SupplierStaffGroupBMPBean.GROUP_TYPE_VALUE});
	}
	
	private Group getParentGroupByTypes(User user, String[] types) throws RemoteException {
		Collection coll = getGroupBusiness().getParentGroupsRecursive(user, types, true);
		if (coll != null && !coll.isEmpty()) {
			Iterator iter = coll.iterator();
			return (Group) iter.next();
		}
		return null;
	}

	@Override
	public GroupBusiness getGroupBusiness() {
		try {
			return (GroupBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), GroupBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

//	GAMLI SUPPLIER MANAGER

	public static String PRICE_CATEGORY_FULL_PRICE_DEFAULT_NAME = "default full price";
	public static String SUPPLIER_ADMINISTRATOR_GROUP_DESCRIPTION = "Supplier administator group";

	public static String permissionGroupNameExtention = " - admins";


	@Override
	public void deleteSupplier(int id)throws Exception{
		invalidateSupplier(((com.idega.block.trade.stockroom.data.SupplierHome)com.idega.data.IDOLookup.getHomeLegacy(Supplier.class)).findByPrimaryKeyLegacy(id));
	}

	@Override
	public Supplier updateSupplier(int supplierId, String name, String description, int[] addressIds, int[] phoneIds, int[] emailIds, String organizationID, int fileID) throws Exception {
		return createSupplier(supplierId, name, null,null, description, addressIds, phoneIds, emailIds, organizationID, fileID);
	}

	@Override
	public Supplier createSupplier(String name, String userName, String password, String description, int[] addressIds, int[] phoneIds, int[] emailIds, String organizationID, int fileID) throws Exception {
		return createSupplier(-1, name, userName, password, description, addressIds, phoneIds, emailIds, organizationID, fileID);
	}

	private Supplier createSupplier(int supplierId,String name, String userName, String password, String description, int[] addressIds, int[] phoneIds, int[] emailIds, String organizationID, int fileID) throws Exception {
		try {
			boolean isUpdate = false;
			if (supplierId != -1) {
				isUpdate = true;
			}

			if (isUpdate) {
				Supplier supp = ((com.idega.block.trade.stockroom.data.SupplierHome)com.idega.data.IDOLookup.getHomeLegacy(Supplier.class)).findByPrimaryKeyLegacy(supplierId);
				supp.setName(name);
				supp.setDescription(description);
				if (organizationID != null) {
					supp.setOrganizationID(organizationID);
				}
				if (fileID > 0) {
					supp.setICFile(fileID);
				} else {
					supp.setICFile(null);
				}
				supp.store();

				supp.removeFrom(GenericEntity.getStaticInstance(Address.class));
				for (int i = 0; i < addressIds.length; i++) {
					supp.addTo(Address.class, addressIds[i]);
				}

				supp.removeFrom(GenericEntity.getStaticInstance(Phone.class));
				for (int i = 0; i < phoneIds.length; i++) {
					supp.addTo(Phone.class, phoneIds[i]);
				}

				supp.removeFrom(GenericEntity.getStaticInstance(Email.class));
				for (int i = 0; i < emailIds.length; i++) {
					supp.addTo(Email.class, emailIds[i]);
				}
				return supp;

			}else {
				Supplier supp = ((com.idega.block.trade.stockroom.data.SupplierHome)com.idega.data.IDOLookup.getHome(Supplier.class)).create();
				supp.setName(name);
				supp.setDescription(description);
				supp.setIsValid(true);
				if (fileID > 0) {
					supp.setICFile(fileID);
				} else {
					supp.setICFile(null);
				}
				supp.store();

				String sName = name+"_"+supp.getPrimaryKey().toString();

				SupplierStaffGroup sGroup = ((SupplierStaffGroupHome)com.idega.data.IDOLookup.getHome(SupplierStaffGroup.class)).create();
				sGroup.setName(sName);
				sGroup.store();

				UserBusiness uBus = getUserBusiness();
				User user = uBus.insertUser(name,"","- admin",name+" - admin","Supplier administrator",null,IWTimestamp.RightNow(),null);
				LoginDBHandler.createLogin(user.getID(), userName, password);

				Group pGroup = ((GroupHome) IDOLookup.getHome(Group.class)).create();
				pGroup.setName(sName+permissionGroupNameExtention);
				pGroup.setDescription(SUPPLIER_ADMINISTRATOR_GROUP_DESCRIPTION);
				pGroup.store();


				pGroup.addGroup(user);
				sGroup.addGroup(user);

//				int[] userIDs = {user.getID()};
//
//				AccessControl ac = new AccessControl();
//				ac.createPermissionGroup(sName+permissionGroupNameExtention, SUPPLIER_ADMINISTRATOR_GROUP_DESCRIPTION, "", userIDs ,null);

				//sGroup.addTo(PermissionGroup.class, permissionGroupID);

				if(addressIds != null){
					for (int i = 0; i < addressIds.length; i++) {
						supp.addTo(Address.class, addressIds[i]);
					}
				}

				if(phoneIds != null){
					for (int i = 0; i < phoneIds.length; i++) {
						supp.addTo(Phone.class, phoneIds[i]);
					}
				}

				if(emailIds != null){
					for (int i = 0; i < emailIds.length; i++) {
						supp.addTo(Email.class, emailIds[i]);
					}
				}

				PriceCategory pCategory = ((com.idega.block.trade.stockroom.data.PriceCategoryHome)com.idega.data.IDOLookup.getHome(PriceCategory.class)).create();
				pCategory.setSupplierId(supp.getID());
				pCategory.setType(com.idega.block.trade.stockroom.data.PriceCategoryBMPBean.PRICETYPE_PRICE);
				pCategory.setDescription(PRICE_CATEGORY_FULL_PRICE_DEFAULT_NAME);
				pCategory.setName("Price");
				pCategory.setCountAsPerson(true);
				pCategory.setExtraInfo("PriceCategory created at "+IWTimestamp.RightNow().toSQLString()+" when creating "+supp.getName());
				pCategory.insert();


				supp.setGroupId(((Integer)sGroup.getPrimaryKey()).intValue());

				if (organizationID != null) {
					supp.setOrganizationID(organizationID);
				}

				supp.update();

				return supp;
			}
		}catch (SQLException sql) {
			sql.printStackTrace(System.err);
			return null;
		}
	}

	@Override
	public void invalidateSupplier(Supplier supplier) throws FinderException, RemoteException{
		supplier.setIsValid(false);
		supplier.store();
		List users = getUsers(supplier);
		if (users != null) {
			for (int i = 0; i < users.size(); i++) {
				try {
					LoginDBHandler.deleteUserLogin( ((User) users.get(i)).getID() );
				} catch (Exception e) {
					throw new FinderException(e.getMessage());
				}
			}
		}
		Group pGroup = getPermissionGroup(supplier);
		pGroup.setName(pGroup.getName()+"_deleted");
		pGroup.store();

		SupplierStaffGroup sGroup = getSupplierStaffGroup(supplier);
		sGroup.setName(sGroup.getName()+"_deleted");
		sGroup.store();
	}

	@Override
	public void validateSupplier(Supplier supplier) throws SQLException {
		supplier.setIsValid(true);
		supplier.update();
	}



	@Override
	public Group getPermissionGroup(Supplier supplier) throws FinderException, RemoteException {
		String name = supplier.getName()+"_"+supplier.getID() + permissionGroupNameExtention;
		String description = SUPPLIER_ADMINISTRATOR_GROUP_DESCRIPTION ;
		Group pGroup = null;
		Collection coll = getGroupBusiness().getGroupHome().findGroupsByNameAndDescription(name, description);
		//List listi = EntityFinder.findAllByColumn((Group) com.idega.core.accesscontrol.data.PermissionGroupBMPBean.getStaticInstance(PermissionGroup.class), com.idega.core.accesscontrol.data.PermissionGroupBMPBean.getNameColumnName(), name, com.idega.core.accesscontrol.data.PermissionGroupBMPBean.getGroupDescriptionColumnName(), description);

		if (coll != null) {
			if (!coll.isEmpty()) {
				Iterator iter = coll.iterator();
				pGroup = (Group) iter.next();
				//pGroup = (Group) listi.get(listi.size()-1);
			}
		}
		if (coll == null || coll.isEmpty()) {
			coll = getGroupBusiness().getGroupHome().findGroupsByNameAndDescription(supplier.getName()+permissionGroupNameExtention, description);
			if (coll != null) {
				if (!coll.isEmpty()) {
					Iterator iter = coll.iterator();
					pGroup = (Group) iter.next();
					//pGroup = (Group) listi.get(listi.size()-1);
				}
			}
		}
//		if (listi == null) {
//		listi = EntityFinder.findAllByColumn((Group) com.idega.core.accesscontrol.data.PermissionGroupBMPBean.getStaticInstance(PermissionGroup.class), com.idega.core.accesscontrol.data.PermissionGroupBMPBean.getNameColumnName(), supplier.getName()+permissionGroupNameExtention, com.idega.core.accesscontrol.data.PermissionGroupBMPBean.getGroupDescriptionColumnName(), description);
//		if (listi != null)
//		if (listi.size() > 0) {
//		pGroup = (Group) listi.get(listi.size()-1);
//		}
//		}
		return pGroup;
	}

	@Override
	public SupplierStaffGroup getSupplierStaffGroup(Supplier supplier) throws RemoteException, FinderException{
		String name = supplier.getName()+"_"+supplier.getID();
		SupplierStaffGroup sGroup = null;
		SupplierStaffGroupHome ssgh = (SupplierStaffGroupHome) IDOLookup.getHome(SupplierStaffGroup.class);
		Collection coll = ssgh.findGroupsByName(name);
//		List listi = EntityFinder.findAllByColumn((SupplierStaffGroup) com.idega.block.trade.stockroom.data.SupplierStaffGroupBMPBean.getStaticInstance(SupplierStaffGroup.class), com.idega.block.trade.stockroom.data.SupplierStaffGroupBMPBean.getNameColumnName(), name);
		if (coll != null) {
			if (!coll.isEmpty()) {
				Iterator iter = coll.iterator();
				sGroup = (SupplierStaffGroup) iter.next();
//				sGroup = (SupplierStaffGroup) listi.get(listi.size()-1);
			}
		}

		if (coll == null || coll.isEmpty()) {
			coll = ssgh.findGroupsByName(supplier.getName());
			if (coll != null) {
				if (!coll.isEmpty()) {
					Iterator iter = coll.iterator();
					sGroup = (SupplierStaffGroup) iter.next();
					//      sGroup = (SupplierStaffGroup) listi.get(listi.size()-1);
				}
			}
		}
//		if (listi == null) {
//		listi = EntityFinder.findAllByColumn((SupplierStaffGroup) com.idega.block.trade.stockroom.data.SupplierStaffGroupBMPBean.getStaticInstance(SupplierStaffGroup.class), com.idega.block.trade.stockroom.data.SupplierStaffGroupBMPBean.getNameColumnName(), supplier.getName());
//		if (listi != null)
//		if (listi.size() > 0) {
//		sGroup = (SupplierStaffGroup) listi.get(listi.size()-1);
//		}
//		}
		return sGroup;
	}

	@Override
	public void addUser(Supplier supplier, User user, boolean addToPermissionGroup) throws FinderException, RemoteException{
		Group pGroup = getPermissionGroup(supplier);
		SupplierStaffGroup sGroup = getSupplierStaffGroup(supplier);
		if (addToPermissionGroup) {
			pGroup.addGroup(user);
		}
//		pGroup.addUser(user);
		((Group) sGroup).addGroup(user);
	}

	@Override
	public List getUsersInPermissionGroup(Supplier supplier) throws RemoteException, FinderException {
		Group pGroup = getPermissionGroup(supplier);
		if (pGroup != null) {
			Collection coll = getUserBusiness().getUsersInGroup( pGroup );
//			List users = getUserBusiness().getUsersInGroup(pGroup);
			List users = new Vector(coll);
			java.util.Collections.sort(users, new com.idega.util.GenericUserComparator(com.idega.util.GenericUserComparator.NAME));
			return users;
		}else {
			return null;
		}
	}

	@Override
	public List getUsersNotInPermissionGroup(Supplier supplier) throws RemoteException, FinderException {
		List allUsers = getUsers(supplier);
		List permUsers = getUsersInPermissionGroup(supplier);

		if (permUsers != null) {
			allUsers.removeAll(permUsers);
		}

		return allUsers;
	}

	@Override
	public List getUsers(Supplier supplier) throws RemoteException, FinderException{
		SupplierStaffGroup sGroup = getSupplierStaffGroup(supplier);
		Collection coll = getUserBusiness().getUsersInGroup(sGroup);
		List users = new Vector(coll);
		java.util.Collections.sort(users, new com.idega.util.GenericUserComparator(com.idega.util.GenericUserComparator.NAME));
		return users;
	}

	@Override
	public List getUsersIncludingResellers(Supplier supplier) throws RemoteException, FinderException {
		return getUsersIncludingResellers(supplier, false);
	}

	@Override
	public List getSupplierManagerStaffUsers(Group supplierManager) throws RemoteException, FinderException{
		Group sGroup = null;
		List users = new Vector();

		sGroup = getSupplierManagerBookingStaffGroup(supplierManager);
		Collection coll = getUserBusiness().getUsersInGroup(sGroup);

		sGroup = getSupplierManagerAdminGroup(supplierManager);
		Collection coll2 = getUserBusiness().getUsersInGroup(sGroup);

		sGroup = getSupplierManagerCashierStaffGroup(supplierManager);
		Collection coll4 = getUserBusiness().getUsersInGroup(sGroup);

		sGroup = getSupplierManagerTravelAgentStaffGroup(supplierManager);
		Collection coll3 = getUserBusiness().getUsersInGroup(sGroup);

		sGroup = getSupplierManagerPartnerGroup(supplierManager);
		Collection coll5 = getUserBusiness().getUsersInGroup(sGroup);


		users.addAll(coll2);
		users.addAll(coll);
		users.addAll(coll4);
		users.addAll(coll3);
		users.addAll(coll5);

		java.util.Collections.sort(users, new com.idega.util.GenericUserComparator(com.idega.util.GenericUserComparator.NAME));

		return users;
	}

	@Override
	public Collection getTravelAgents(Group supplierManager) throws RemoteException {
		Group sGroup = getSupplierManagerTravelAgentStaffGroup(supplierManager);
		Collection coll = getUserBusiness().getUsersInGroup(sGroup);
		List v = new Vector();
		v.addAll(coll);
		java.util.Collections.sort(v, new com.idega.util.GenericUserComparator(com.idega.util.GenericUserComparator.NAME));
		return coll;
	}

	@Override
	public Collection getSupplierManagerCashiers(Group supplierManager) throws RemoteException {
		Group sGroup = getSupplierManagerCashierStaffGroup(supplierManager);
		Collection coll = getUserBusiness().getUsersInGroup(sGroup);
		List v = new Vector();
		v.addAll(coll);
		java.util.Collections.sort(v, new com.idega.util.GenericUserComparator(com.idega.util.GenericUserComparator.NAME));
		return coll;
	}

	@Override
	public List getUsersIncludingResellers(Supplier supplier, Object objBetweenResellers) throws RemoteException, FinderException {
		List users = getUsers(supplier);
		List temp;
		if (users == null) {
			users = com.idega.util.ListUtil.getEmptyList();
		}
		Iterator resellers = getResellerManager().getResellers(supplier, com.idega.block.trade.stockroom.data.ResellerBMPBean.getColumnNameName());
		while (resellers.hasNext()) {
			temp = getResellerManager().getUsersIncludingSubResellers((Reseller)resellers.next(), objBetweenResellers);
			if (temp != null) {
				users.addAll(temp);
			}
		}
		return users;
	}

	@Override
	public List getUsersIncludingResellers(Supplier supplier, boolean includeSupplierUsers) throws RemoteException, FinderException {
		List users = new Vector();
		if (includeSupplierUsers) {
			users = getUsers(supplier);
		}
		List temp;
		if (users == null) {
			users = com.idega.util.ListUtil.getEmptyList();
		}
		Iterator resellers = getResellerManager().getResellers(supplier, com.idega.block.trade.stockroom.data.ResellerBMPBean.getColumnNameName());
		while (resellers.hasNext()) {
			temp = getResellerManager().getUsers((Reseller)resellers.next());
//			temp = ResellerManager.getUsersIncludingSubResellers((Reseller)resellers.next());
			if (temp != null) {
				users.addAll(temp);
			}
		}
		return users;
	}


	@Override
	public User getMainUser(Supplier supplier) throws RemoteException, FinderException {
		if (supplier.getGroupId() == -1) {
			return null;
		}
		//Group group = getGroupBusiness().getGroupHome().findByPrimaryKey(new Integer(supplier.getGroupId()));
		Group group = getPermissionGroup(supplier);
		Collection coll = getUserBusiness().getUsersInGroup(group);
		List users = new Vector(coll);
		//List users = UserGroupBusiness.getUsersContained(((com.idega.core.data.GenericGroupHome)com.idega.data.IDOLookup.getHomeLegacy(GenericGroup.class)).findByPrimaryKeyLegacy(supplier.getGroupId()));
		if (!users.isEmpty()) {
			return (User) users.get(0);
		}else {
			return null;
		}
	}

	protected UserBusiness getUserBusiness() {
		try {
			return (UserBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), UserBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	protected TravelAgentBusiness getTravelAgentBusiness() {
		try {
			return (TravelAgentBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), TravelAgentBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	protected ResellerManager getResellerManager() {
		try {
			return (ResellerManager) IBOLookup.getServiceInstance(getIWApplicationContext(), ResellerManager.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	@Override
	public String[] getMetaDataKeysForGroupType(String groupType) throws RemoteException {
		if (groupType != null) {
			if (groupType.equals(SUPPLIER_MANAGER_TRAVEL_AGENT_STAFF_TYPE)) {
				return getTravelAgentBusiness().getMetaDataKeys();
			}
		}
		return null;
	}

}
