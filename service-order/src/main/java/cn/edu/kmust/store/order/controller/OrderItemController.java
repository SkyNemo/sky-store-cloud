package cn.edu.kmust.store.order.controller;


import cn.edu.kmust.store.order.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class OrderItemController {


    @Autowired
    private OrderItemService orderItemService;


    @RequestMapping("/deleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(HttpServletRequest request) {

        int orderItemId = Integer.parseInt(request.getParameter("orderItemId"));

        orderItemService.deleteOrderItemById(orderItemId);

        return "success";
    }


    @RequestMapping("/changeOrderItem")
    @ResponseBody
    public String changeOrderItem(HttpServletRequest request) {

        HttpSession session = request.getSession();

        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);


        Integer productId = Integer.parseInt(request.getParameter("productId"));

        Integer number = Integer.parseInt(request.getParameter("number"));

        orderItemService.changeOrderItem(userId, productId, number);


        return "success";
    }


    @RequestMapping("/sale/{id}")
    @ResponseBody
    public Integer countProductSale(@PathVariable Integer id){

        Integer sale = orderItemService.countProductSale(id);

        return sale;
    }

}
