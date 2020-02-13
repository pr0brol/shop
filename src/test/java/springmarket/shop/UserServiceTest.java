package springmarket.shop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import springmarket.shop.entities.User;
import springmarket.shop.repositories.UserRepository;
import springmarket.shop.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOneByPhone() {
        User userDB = new User();
        userDB.setPhone("66666666");
        userDB.setEmail("user@email.ru");

        Mockito.doReturn(userDB)
                .when(userRepository)
                .findOneByPhone("66666666");

        User userTest = userService.findByPhone("66666666");
        Assert.assertNotNull(userTest);
        Mockito.verify(userRepository, Mockito.times(1)).findOneByPhone(ArgumentMatchers.eq("66666666"));
    }
}
