/**
 * 
 */
package me.djin.dcore.frame.test;

import java.net.URL;

import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author djin
 *
 */
@RunWith(SpringRunner.class)
public class TestBase {
	@LocalServerPort
    protected int port;
	
    @Autowired
    protected TestRestTemplate restTemplate;

    /**
     * 性能测试
     */
	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();

	protected URL base;
    
    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/", port);
        this.base = new URL(url);
    }
}
