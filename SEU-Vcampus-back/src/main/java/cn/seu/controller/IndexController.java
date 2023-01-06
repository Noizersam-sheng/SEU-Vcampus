package cn.seu.controller;

import cn.seu.Impl.AliPayImpl;
import cn.seu.domain.store.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    private AliPayImpl aliPayimpl;

    @RequestMapping("/")
    public String index() {
        return "这里是虚拟校园开发项目的后端，小赖头发快写没了";
    }


}
