package me.djin.study.frame.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.djin.study.exception.ApplicationException;
import me.djin.study.frame.model.OutputModel;
import me.djin.study.frame.model.TestModel;
import me.djin.study.frame.service.BaseService;
import me.djin.study.frame.service.IBaseService;

@Api
@RestController
@RequestMapping("test")
public class TestApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestApi.class);
	/**
	 * SpringBoot 第一个用例，以启动为主
	 * @return
	 */
	@GetMapping("index")
	public String index() {
		return "hello world";
	}
	
	/**
	 * 统一输出格式
	 * @return
	 */
	@GetMapping("output")
	public OutputModel output() {
		return new OutputModel(0);
	}
	
	@PostMapping("swagger")
	@ApiOperation(value="swagger 文档生成示例")
	@ApiResponses({@ApiResponse(code=10010,message="用户令牌已过期")})
	public OutputModel<List<TestModel>> swagger(
			@ApiParam(name="参数名称,一般为英文，与实际参数名称对应", value="参数描述") @RequestBody @Valid TestModel mod
			, @ApiParam("参数描述") @RequestParam int stringParam) {
		String obj = "asdf";
		try {
			Long a = Long.valueOf(obj);
		}catch(Exception e) {
			throw new ApplicationException(10010, obj, "张三");
		}
		TestModel model = new TestModel();
		List<TestModel> list = new ArrayList<TestModel>();
		list.add(model);
		return new OutputModel<List<TestModel>>(0, list);
	}
	

	@Autowired private IBaseService<TestModel> svc;
//	@Autowired private IBaseService<OutputModel> svc1;
	@GetMapping("multiBean")
	public OutputModel multiBean() {
		svc.add(null);
		OutputModel out = new OutputModel(1);
//		svc1.add(out);
		return new OutputModel(0);
	}
}