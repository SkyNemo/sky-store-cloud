package cn.edu.kmust.store.user.controller;


import cn.edu.kmust.store.user.entity.User;
import cn.edu.kmust.store.user.param.UserDto;
import cn.edu.kmust.store.user.param.UserParam;
import cn.edu.kmust.store.user.service.UserService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.eureka.http.EurekaApplications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {


    @Resource
    private UserService userService;

    @Resource
    private EurekaClient eurekaClient;


    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }


    @RequestMapping("/login")
    public String Login(@Valid UserParam userParam, BindingResult result, HttpServletRequest request, ModelMap map) {


        // 获取所有已注册服务
        Applications applications = eurekaClient.getApplications();
        // 获取实例名为server-gateway的所有实例
        Application application = applications.getRegisteredApplications("server-gateway");

        String reqUrl = "";

        if (application != null) {
            List<InstanceInfo> instances = application.getInstances();
            if (instances.size() > 0) {
                // 获取其中一个应用实例，这里可以添加路由算法
                InstanceInfo instance = instances.get(0);
                // 获取实例公开的地址和端口
                reqUrl = "http://" + instance.getIPAddr() + ":" + instance.getPort();
            }
        }


        StringBuffer errorMsg = new StringBuffer();


        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();

            for (ObjectError error : list) {
                errorMsg = errorMsg.append(error.getCode()).append("-").append(error.getDefaultMessage())
                        .append(";").append("\n");
            }

            map.addAttribute("errorMsg", errorMsg.toString());
            return "login";
        }

        //  调用service查询是否有与该用户名、密码相匹配的用户
        //  若用户存在，则登录成功，否则表示用户名或者密码错误
        User user = userService.selectUserByNameAndPassword(userParam.getName(), userParam.getPassword());
        if (user == null) {
            map.addAttribute("errorMsg", "用户名或密码错误");
            return "login";
        } else {
            // 登录成功，将用户Id和用户名存储在session中
            HttpSession session = request.getSession();
            session.setAttribute("user", user.getId().toString() + " " + user.getName().toString());
            return "redirect:"+ reqUrl +"/service-product/";
        }

    }


    @RequestMapping("/register")
    public String Register(@Valid UserParam userParam, BindingResult result, ModelMap map) {

        StringBuffer errorMsg = new StringBuffer();

        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();

            for (ObjectError error : list) {
                errorMsg = errorMsg.append(error.getCode()).append("-").append(error.getDefaultMessage()).append(";");
            }

            map.addAttribute("errorMsg", errorMsg.toString());
            return "register";
        }

        // 判断用户名是否已被占用
        boolean userIsExist = userService.selectUserByName(userParam.getName());
        if (userIsExist) {
            map.addAttribute("errorMsg", "用户已存在！");
            return "register";
        }

        // 创建新用户
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        boolean saveResult = userService.saveUser(user);
        if (saveResult) {
            return "login";
        } else {
            return "register";
        }

    }


    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "login";
    }

    /*
     * 用户Ajax登录
     * */
    @RequestMapping("/ajaxLogin")
    @ResponseBody
    public String ajaxLogin(HttpServletRequest request) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        User user = userService.selectUserByNameAndPassword(name, password);
        if (user == null) {
            return "fail";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user.getId().toString() + " " + user.getName().toString());
            return "success";
        }
    }


    /*
     * 检查用户是否登录
     * */
    @RequestMapping("checkLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        String userInfo = (String) session.getAttribute("user");
        if (null != userInfo && 0 < userInfo.length()) {
            return "success";
        }
        return "fail";
    }


    /*
     * 根据Id获取用户信息
     * */
    @RequestMapping("/user/{id}")
    @ResponseBody
    public UserDto findUserById(@PathVariable Integer id) {

        UserDto userDto = userService.getUserByUserId(id);

        return userDto;
    }

}
