/**
 * 
 */
package me.djin.dcore;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.djin.dcore.frame.model.Response;

/**
 * @author djin
 * 测试接口
 */
@RestController
@RequestMapping("/")
public class TestController {
	@PostMapping("index")
	public Response<?> index() {
		return Response.ok();
	}
	@PostMapping("params")
	public Response<?> params(@RequestBody Model model) {
		return Response.ok(model);
	}
}
