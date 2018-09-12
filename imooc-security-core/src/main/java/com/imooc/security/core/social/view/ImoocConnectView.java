/**
 * 
 */
package com.imooc.security.core.social.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * 访问/connect获取社交绑定信息 对应的返回视图connect/status
 * 绑定微信 Get /connect/weixin 绑定成功之后返回的视图 connect/weixinConnected
 * 解绑微信 Delete /connect/weixin 返回视图connect/weixinConnect
 * @author zhailiang
 */
public class ImoocConnectView extends AbstractView {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel
	 * (java.util.Map, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		if (model.get("connections") == null) {
			response.getWriter().write("<h3>解绑成功</h3>");
		} else {
			response.getWriter().write("<h3>绑定成功</h3>");
		}

	}

}
