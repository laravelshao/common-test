package com.laravelshao.common.test.controller;

import com.laravelshao.common.test.TestSupport;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author qinghua.shao
 * @date 2019/12/31
 * @since 1.0.0
 */
public class DemoControllerTest extends TestSupport {

    @Test
    public void test() throws Exception {

        MvcResult mvcResult = mockMvc.perform((post("/test")
                //.param("param1", "")
                //.header("header1", "")
        ))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }
}
