package com.imooc.wiremock;

import com.github.tomakehurst.wiremock.matching.UrlPathPattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by wangjie on 2018/6/30.
 */
public class MockServer {
    public static void main(String[] args) throws Exception{
        configureFor(8062);
        removeAllMappings();
        mock("/order/1","01");
        mock("/order/2","02");
    }

    private static void mock(String url,String file) throws Exception{
        ClassPathResource resource = new ClassPathResource("mock/response/"+file+".txt");
        String content = FileUtils.readFileToString(resource.getFile(),"UTF-8");
        stubFor(get(urlPathEqualTo(url)).willReturn(aResponse().withBody(content).withStatus(200)));
    }
}
