package com.imooc.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * Created by wangjie on 2018/6/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void whenUploadSuccess() throws Exception{
        String result = mockMvc.perform(multipart("/file").
                file(new MockMultipartFile("file","test.txt","multipart/form-data","hello upload".getBytes()))
                )
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenQuerySuccess() throws Exception{
        String result = mockMvc.perform(get("/user")
                        .param("username","xiaoming")
                        .param("page","3")
                        .param("size","15")
                        .param("sort","age,desc")

                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        ).andDo(print()).andExpect(status().isOk())
                        .andExpect(jsonPath("$.length()").value(3))//返回的json的数组 长度是3
                        .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenGetInfoSuccess() throws Exception{
        String result = mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andDo(print()).andExpect(status().isOk())
                        .andExpect(jsonPath("$.username").value("tom"))
                        .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenGetInfoFail() throws Exception{
        mockMvc.perform(get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCreateSuccess() throws Exception{
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":"+new Date().getTime()+"}";
        String result = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenUpdateSuccess() throws Exception{
        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        String content = "{\"id\":\"1\",\"username\":\"tom\",\"password\":null,\"birthday\":"+date.getTime()+"}";
        String result = mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenDeleteSuccess() throws Exception{
        mockMvc.perform(delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk());
    }



}
