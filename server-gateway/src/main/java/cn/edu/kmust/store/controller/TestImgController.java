package cn.edu.kmust.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestImgController {


    @RequestMapping("img/{id}")
    public String test(@PathVariable Integer id, Model model) {

        model.addAttribute("id",id);

        return "test";
    }

}
