package cn.edu.kmust.store.order.controller;


import cn.edu.kmust.store.order.client.ProductFeignClient;
import cn.edu.kmust.store.order.entity.Product;
import cn.edu.kmust.store.order.param.OrderItemVo;
import cn.edu.kmust.store.order.param.OrderParam;
import cn.edu.kmust.store.order.param.OrderVo;
import cn.edu.kmust.store.order.service.OrderItemService;
import cn.edu.kmust.store.order.service.OrderService;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {


    @Resource
    private OrderService orderService;

    @Resource
    private OrderItemService orderItemService;


    @RequestMapping("/buyAtOnce")
    private String buyOne(HttpServletRequest request, Model model) {

        Integer productId = Integer.parseInt(request.getParameter("productId"));

        Integer number = Integer.parseInt(request.getParameter("number"));

        HttpSession session = request.getSession();

        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);

        Integer orderItemVoId = -1;


        OrderItemVo orderItemVo = orderItemService.getOrderItemVoByUserIdAndProductIdAndNumber(userId, productId, number);

        orderItemVoId = orderItemVo.getId();

        return this.buy(new String[]{String.valueOf(orderItemVoId)}, session, model);
    }


    @RequestMapping("/buy")
    public String buy(String[] orderItemIds, HttpSession session, Model model) {

        List<OrderItemVo> orderItemVos = orderItemService.getOrderItemVoListByOrderItemListId(orderItemIds);

        String orderItemVosStr = JSON.toJSONString(orderItemVos);

        session.setAttribute("orderItemVosStr", orderItemVosStr);
        model.addAttribute("orderItems", orderItemVos);


        float total = 0;
        for (OrderItemVo orderItemVo : orderItemVos) {
            total += orderItemVo.getTotal();
        }


        model.addAttribute("total", total);

        return "buy";
    }


    @RequestMapping("/createOrder")
    public String createOrder(OrderParam orderParam, HttpSession session, Model model) {


        String orderItemVosStr = (String) session.getAttribute("orderItemVosStr");

        List<OrderItemVo> orderItemVos = JSON.parseArray(orderItemVosStr, OrderItemVo.class);


        if (orderItemVos == null || orderItemVos.size() < 0) {
            return "error";
        }


        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);

        orderParam.setUserId(userId);

        OrderVo orderVo = orderService.createOrder(orderParam, orderItemVos);

        model.addAttribute("order", orderVo);

        return "alipayPage";

    }


    @RequestMapping("/confirmPay")
    public String confirmPay(OrderParam orderParam, Model model) {

        OrderVo orderVo = orderService.confirmPay(orderParam);

        model.addAttribute("order", orderVo);

        return "payedPage";
    }


    @RequestMapping("/order")
    public String bought(Model model, HttpSession session) {

        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);

        List<OrderVo> orderVos = orderService.getByUserId(userId);

        model.addAttribute("orders", orderVos);

        return "bought";
    }


    @RequestMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(HttpServletRequest request) {

        int deleteOrderId = Integer.parseInt(request.getParameter("orderId"));

        orderService.deleteOrder(deleteOrderId);

        return "success";
    }


    @RequestMapping("/addCart")
    @ResponseBody
    public String addCart(HttpServletRequest request) {

        int productId = Integer.parseInt(request.getParameter("productId"));

        int number = Integer.parseInt(request.getParameter("number"));

        System.out.println(productId + ":" + number);


        HttpSession session = request.getSession();

        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);

        orderItemService.getOrderItemVoByUserIdAndProductIdAndNumber(userId, productId, number);

        return "success";
    }

    @RequestMapping("/shoppingCart")
    public String shoppingCart(HttpServletRequest request,Model model) {


        HttpSession session = request.getSession();

        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);


        List<OrderItemVo> orderItemVos = orderItemService.getOrderItemVoListByUserIdAndOrderId(userId, null);

        model.addAttribute("orderItems", orderItemVos);

        return "cartPage";
    }


}
