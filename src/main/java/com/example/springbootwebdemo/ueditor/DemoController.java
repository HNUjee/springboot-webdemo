package com.example.springbootwebdemo.ueditor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Api(value = "DemoController", tags = {"demon控制器"})
@RestController
public class DemoController {

	@ApiOperation(value = "ueditor")
	@ApiImplicitParam(name = "demo", paramType = "Model",dataType = "ModelAndView", type = "query", required = true)
	@PostMapping("/ueditor")
	public ModelAndView demo(Model model) {

		// 带有双引号的数据
		String content = "带有双引的数据：<br/>图片路径:<img alt=\"图片\" src=\"/images/aaaa.jpg\">;";
		model.addAttribute("content", content);

		return new ModelAndView("ueditor");
	}

}
