/**
 * 
 */
package com.imooc.security.core.properties;
import lombok.Data;
import org.springframework.social.autoconfigure.SocialProperties;


/**
 * @author wangjie
 *
 */
@Data
public class WeixinProperties extends SocialProperties {
	
	/**
	 * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
	 */
	private String providerId = "weixin";
	

}
