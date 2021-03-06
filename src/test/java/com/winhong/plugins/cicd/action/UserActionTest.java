package com.winhong.plugins.cicd.action;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.tool.RandomString;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.user.User;

public class UserActionTest extends UserAction {
	
	static User user=new User();
	
	//批量增加用户test
	@Test
	public void adduserBatch() throws Exception{

		for(int i=1; i<=12; i++){
			User user = new User();
			user.setUserType("local");
			user.setRole("admin");
			user.setUsername("admin"+i);
			UserAction.addUser(user);
		}
	}
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		user.setUsername("中文用户");
		user.setPassword("密码");
		user.setRole("admin");
		user.setUserType(User.LOCAL);
		RandomString.init(12);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddUser() {
		try {
			
			UserAction.addUser(user);
			System.out.println(UserAction.getUserinfo(user.getUsername()));
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail();
		}finally{
			try {
				UserAction.deleteUser(user.getUsername());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
 	}
	
	@Test
	public void testAddUser2() {
		try {
			user.setUsername("xiehq");
			user.setPassword("acd12345");
			user.setRole("admin");

			UserAction.addUser(user);
			System.out.println(UserAction.getUserinfo(user.getUsername()));
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail();
		} 
		
 	}

	@Test
	public void testModifyUser() {
		try {
			user.setUsername("修改用户");
			UserAction.addUser(user);
			UserAction.modifyUser(user,false);
			System.out.println(UserAction.searchUser("修改",null,null).size());
			ArrayList<User> t = UserAction.searchUser("修改",null,null);
			if (t.size()!=1)
					fail();
			else{
				System.out.println(Tools.getJson(t));
			}
			
			if (UserAction.searchUser("修改2","","").size()!=0)
				fail();
			
			System.out.println(UserAction.getUserinfo(user.getUsername()));
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail();
		}finally{
			try {
				UserAction.deleteUser(user.getUsername());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testDeleteUser() {
		try {
			user.setUsername("删除用户");
			UserAction.addUser(user);
			UserAction.deleteUser(user.getUsername());
			 
			System.out.println(Tools.getJson(UserAction.getUserinfo(user.getUsername())));
		} catch (IOException e) {
			 
			e.printStackTrace();
			return;
 		}	
		fail();
	}

	@Test
	public void testResetPassword() {
		try {
			
			UserAction.addUser(user);
			UserAction.resetPassword(user.getUsername());
			System.out.println(Tools.getJson(UserAction.getUserinfo(user.getUsername())));			
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail();
		}finally{
			try {
				UserAction.deleteUser(user.getUsername());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
 	}
	
	
	@Test
	public void testResetPasswordExpired() {
		try {
			
			UserAction.addUser(user);
			//UserAction.resetPassword(user.getUsername());
			user.setPasswordExpired(System.currentTimeMillis());
			UserAction.modifyUser(user,true);
			Thread.sleep(10);
			User u = UserAction.Login(user.getUsername(),user.getPassword());
			if (u!=null)
				fail();
 			
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				UserAction.deleteUser(user.getUsername());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
 	}
}
