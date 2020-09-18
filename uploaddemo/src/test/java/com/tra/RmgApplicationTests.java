package com.tra;


import com.tra.entity.User;
import com.tra.repository.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RmgApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void save() {
		User user = new User();
		user.setUsername("zzzz");
		user.setPassword("bbbb");
		user.setSex(1);
		user.setAge(18);
		// 返回插入的记录数 ，期望是1条 如果实际不是一条则抛出异常
		Assert.assertEquals(1,userMapper.save(user));
	}

	@Test
	public void update() {
		User user = new User();
		user.setId(2L);
		user.setPassword("newpassword1");
		// 返回更新的记录数 ，期望是1条 如果实际不是一条则抛出异常
		Assert.assertEquals(1,userMapper.update(user));
	}

	@Test
	public void selectById() {
		Assert.assertNotNull(userMapper.selectById(2l));
	}

//	@Test
//	public void deleteById() {
//		Assert.assertEquals(1,userMapper.deleteById(1));
//	}

}
