package com.jim.spring.cloud.sentinel.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.jim.spring.cloud.sentinel.dto.RespDTO;
import com.jim.spring.cloud.sentinel.exception.MyBlockException;
import com.jim.spring.cloud.sentinel.util.FastJsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author jim lin
 * @date 2019/4/24.
 */
public class MyUrlBlockHandler implements UrlBlockHandler {

    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException{
        //此处抛出异常操作不起效果，因为限流的操作是在Filter中执行的，没有到达DispatchServlet中
        //com.alibaba.csp.sentinel.adapter.servlet.CommonFilter
//        throw new MyBlockException(e);
        blockRequest(httpServletRequest,httpServletResponse);
    }

    private void blockRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
        RespDTO<String> respDTO = RespDTO.failure("系统太热了，降降火");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(FastJsonUtils.toJsonString(respDTO));
        out.flush();
        out.close();
    }

}
