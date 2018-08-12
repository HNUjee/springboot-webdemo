package com.example.springbootwebdemo.ueditor;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DemoController {

	@RequestMapping("/ueditor")
	public ModelAndView demo(Model model) {

		// 带有双引号的数据
		String content = "带有双引的数据：<br/>图片路径:<img alt=\"图片\" src=\"/images/aaaa.jpg\">;";
		model.addAttribute("content", content);
		System.out.println("带有双引号的数据: " + content);

		return new ModelAndView("ueditor");
	}

}
