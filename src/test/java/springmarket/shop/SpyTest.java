package springmarket.shop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpyTest {

    @Spy
    private List<Integer> list = new ArrayList<>();

    @Test
    public void test() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Mockito.verify(list).add(1);
        Mockito.verify(list).add(2);
        Mockito.verify(list).add(3);
        Mockito.verify(list).add(4);

        assertEquals(4, list.size());

        Mockito.doReturn(100).when(list).size();

        assertEquals(100, list.size());
    }
}
