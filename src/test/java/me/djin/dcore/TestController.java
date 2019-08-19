/**
 * 
 */
package me.djin.dcore;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.djin.dcore.frame.model.Response;
import me.djin.dcore.mq.FutureCallback;
import me.djin.dcore.mq.Message;
import me.djin.dcore.mq.MqAbstractFactory;
import me.djin.dcore.mq.Producer;

/**
 * @author djin
 * 测试接口
 */
@RestController
@RequestMapping("/")
public class TestController {
	@Autowired
	private MqAbstractFactory mqFactory;
	
	@PostMapping("index")
	public Response<?> index() {
		return Response.ok();
	}
	@PostMapping("params")
	public Response<?> params(@RequestBody Model model) {
		return Response.ok(model);
	}
	@PostMapping("kafka")
	public Response<?> kafka(){
		String message = new Date().toString();
		Producer producer = mqFactory.createProducer();
		producer.send("test", message, new FutureCallback() {
			
			@Override
			public void onSuccess(Message message) {
				System.out.printf("kafka success, %s", message.getMessage());
			}
			
			@Override
			public void onFailure(Message message, Throwable exception) {
				System.out.printf("kafka failure, %s", message.getMessage());
			}
		});
		return Response.ok();
	}
}
