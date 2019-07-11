package org.dyp.mybatis;

import org.dyp.mybatis.dao.UserMapper;
import org.dyp.mybatis.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.dyp.mybatis.dao.CityDao;
import org.dyp.mybatis.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void findByName() {
        City city =  cityDao.findByName("xian");
        System.out.println(city.toString());
    }


    @Test
    public void save() {
        City city = new City();
        city.setCityName("zhengzhou");
        city.setDescription("zhengzhou description");
        city.setId(200l);
        city.setProvinceId(211l);
        Assert.assertEquals(1,cityDao.save(city));
    }


    @Test
    public void update() {
        City city = new City();
        city.setCityName("zhengzhou");
        city.setDescription("bad city");
        city.setId(200l);
        city.setProvinceId(219l);
        Assert.assertEquals(1,cityDao.update(city));
    }

    @Test
	public void selectById() {
		Assert.assertNotNull(userMapper.selectById(2l));
        User user = userMapper.selectById(2l);
        System.out.println(user.getUsername());
	}

    @Test
    public void selectAll() {
        List<User> users =  userMapper.selectAll();
        for(User user: users)
        {
            System.out.println(user.getUsername());
        }
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(2L);
        user.setPassword("newpassword");
        // 返回更新的记录数 ，期望是1条 如果实际不是一条则抛出异常
        Assert.assertEquals(1,userMapper.update(user));
    }

    @Test
    public void deleteById(){
        Assert.assertEquals(1, userMapper.deleteById(1l));
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setUsername("dyp");
        user.setPassword("password");
        user.setSex(1);
        user.setAge(18);
        // 返回插入的记录数 ，期望是1条 如果实际不是一条则抛出异常
        Assert.assertEquals(1,userMapper.save(user));
    }
}
