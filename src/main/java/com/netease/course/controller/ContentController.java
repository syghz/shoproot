package com.netease.course.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.netease.course.model.Content;
import com.netease.course.model.Json;
import com.netease.course.model.Trx;
import com.netease.course.service.ContentService;
import com.netease.course.service.TrxService;

/**
 * content的controller
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/api")
public class ContentController {
	@Autowired
	private ContentService contentService;

	@Autowired
	private TrxService trxService;

	/**
	 * 删除产品
	 * 
	 * @param id
	 * @param map
	 * @param hs
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json apiDelete(@RequestParam("id") int id, ModelMap map, HttpSession hs) {
		if (id > 0) {
			contentService.deleteContentById(id);
			return new Json(200, "删除成功！", true);
		} else {
			return new Json(220, "删除失败！", false);
		}
	}

	/**
	 * 购买
	 * <p>Title: buyProductById</p>
	 * <p>Description: </p>
	 * @param id
	 * @param map
	 * @param hs
	 * @return
	 */
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	@ResponseBody
	public Json buyProductById(@RequestParam("id") int id, ModelMap map, HttpSession hs) {
		Map<String, Object> user = (Map<String, Object>) hs.getAttribute("user");
		Content c =contentService.getByContentId(id);
		Trx t = new Trx();
		t.setPersonid(1);
		t.setTime(new Date().getTime());
		t.setPrice(c.getPrice());
		t.setContentid(id);
		int result = trxService.insertTrx(t);
		if (result > 0) {
			return new Json(200, "购买成功！", true);
		} else {
			return new Json(220, "购买失败！", false);
		}
	}
}
