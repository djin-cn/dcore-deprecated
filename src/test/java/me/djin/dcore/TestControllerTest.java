package me.djin.dcore;

import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.databene.contiperf.PerfTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import me.djin.dcore.frame.model.Response;
import me.djin.dcore.frame.test.SpringBootTestUtils;
import me.djin.dcore.frame.test.TestBase;

/**
 * 测试类
 * @author djin
 *
 */
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest extends TestBase {
	
    @Test
    public void index() {
    	String url = this.base.toString() + "/index";
    	Response<Model> response = SpringBootTestUtils.post(this.restTemplate, url, null, Model.class);
    	System.out.println(String.format("%s：%s", this.getClass().getName(), response));
        Assert.assertThat(response.getStatus(), is("1"));
    }
    
    @Test
    public void testParams() {
    	String url = this.base.toString() + "/params";
    	Model params = new Model();
    	params.setAccount("account1");
    	params.setId(1L);
    	params.setUpdateTime(new Date());
    	Response<Model> response = SpringBootTestUtils.post(this.restTemplate, url, params, Model.class);
    	System.out.println(String.format("%s：%s", this.getClass().getName(), response));
    	Assert.assertThat(response.getStatus(), is("1"));
    }
    @Test
    public void kafka() {
    	String url = this.base.toString() + "/kafka";
    	Response<Model> response = SpringBootTestUtils.post(this.restTemplate, url, null, Model.class);
    	System.out.println(String.format("%s：%s", this.getClass().getName(), response));
        Assert.assertThat(response.getStatus(), is("1"));
    }
    @Test
    public void redis() {
    	String url = this.base.toString() + "/redis";
    	Response<Model> response = SpringBootTestUtils.post(this.restTemplate, url, null, Model.class);
    	System.out.println(String.format("%s：%s", this.getClass().getName(), response));
        Assert.assertThat(response.getStatus(), is("1"));
    }
    @Test
    public void redisUnlock() {
    	String url = this.base.toString() + "/redisUnlock";
    	SpringBootTestUtils.post(this.restTemplate, url, null, Model.class);
    }
    
    @Test
    @PerfTest(invocations = 100,threads = 100)
    public void redisLock() {
    	String url = this.base.toString() + "/redisLock";
    	SpringBootTestUtils.post(this.restTemplate, url, 5, Model.class);
    }
    
    @Test
    @PerfTest(invocations = 100,threads = 100)
    public void redisLockExecute() {
    	String url = this.base.toString() + "/redisLockExecute";
    	SpringBootTestUtils.post(this.restTemplate, url, 2, Model.class);
    }
}
