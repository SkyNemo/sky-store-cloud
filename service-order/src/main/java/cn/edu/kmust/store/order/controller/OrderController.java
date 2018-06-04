package cn.edu.kmust.store.order.controller;


import cn.edu.kmust.store.order.param.OrderDto;
import cn.edu.kmust.store.order.param.OrderItemVo;
import cn.edu.kmust.store.order.param.OrderParam;
import cn.edu.kmust.store.order.param.OrderVo;
import cn.edu.kmust.store.order.service.OrderItemService;
import cn.edu.kmust.store.order.service.OrderService;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 立即购买
     * */
    @RequestMapping("/buyAtOnce")
    private String buyOne(HttpServletRequest request, Model model) {

        // 获取前端传来的数据

        Integer productId = Integer.parseInt(request.getParameter("productId"));

        Integer number = Integer.parseInt(request.getParameter("number"));

        HttpSession session = request.getSession();

        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);

        Integer orderItemVoId = -1;


        // 根据用户id、商品id、商品数目创建订单项
        OrderItemVo orderItemVo = orderItemService.getOrderItemVoByUserIdAndProductIdAndNumber(userId, productId, number);

        orderItemVoId = orderItemVo.getId();

        // 调用结算方法，跳转到订单提交页面
        return this.buy(new String[]{String.valueOf(orderItemVoId)}, session, model);
    }


    @RequestMapping("/buy")
    public String buy(String[] orderItemIds, HttpSession session, Model model) {

        List<OrderItemVo> orderItemVos = orderItemService.getOrderItemVoListByOrderItemIds(orderItemIds);

        // 将订单项信息转换成JSON数据存入session中
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

    /**
     * 创建订单
     * */
    @RequestMapping("/createOrder")
    public String createOrder(OrderParam orderParam, HttpSession session, Model model) {


        // 获取session中的订单项信息
        String orderItemVosStr = (String) session.getAttribute("orderItemVosStr");

        List<OrderItemVo> orderItemVos = JSON.parseArray(orderItemVosStr, OrderItemVo.class);


        if (orderItemVos == null || orderItemVos.size() < 0) {
            return "orderError";
        }


        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);

        orderParam.setUserId(userId);

        OrderVo orderVo = orderService.createOrder(orderParam, orderItemVos);

        if (orderVo == null){
            return "orderError";
        }

        model.addAttribute("order", orderVo);

        return "alipayPage";

    }


    @RequestMapping("/confirmPay")
    public String confirmPay(OrderParam orderParam, Model model) {

        OrderVo orderVo = orderService.confirmPay(orderParam);

        model.addAttribute("order", orderVo);

        return "payedPage";
    }


    @RequestMapping("/orders")
    public String bought(Model model, HttpSession session) {

        // 获取用户信息
        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);

        //根据用户id获取订单
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

        // 获取前端传来的信息

        int productId = Integer.parseInt(request.getParameter("productId"));

        int number = Integer.parseInt(request.getParameter("number"));


        HttpSession session = request.getSession();

        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);

        // 创建订单项
        orderItemService.getOrderItemVoByUserIdAndProductIdAndNumber(userId, productId, number);

        return "success";
    }

    @RequestMapping("/shoppingCart")
    public String shoppingCart(HttpServletRequest request,Model model) {


        // 获取用户信息
        HttpSession session = request.getSession();

        String userInfo = (String) session.getAttribute("user");

        Integer userId = Integer.parseInt(userInfo.split(" ")[0]);


        // 根据用户id获取购物车订单项
        List<OrderItemVo> orderItemVos = orderItemService.getOrderItemVoListByUserIdAndOrderId(userId, null);

        model.addAttribute("orderItems", orderItemVos);

        return "cartPage";
    }


    /*
    * 根据订单id获取订单信息
    * */
    @RequestMapping("/order/{id}")
    @ResponseBody
    public OrderDto getOrderById(@PathVariable Integer id){

        OrderDto orderDto = orderService.getOrderDtoByOrderId(id);

        List<OrderItemVo> orderItemVos = orderDto.getOrderItems();

        return orderDto;

    }


    @RequestMapping("/finishOrder/{id}")
    @ResponseBody
    public boolean finishOrder(@PathVariable Integer id){

        boolean isSuccess = orderService.finishOrder(id);

        return isSuccess;
    }


    @RequestMapping("/confirmReceipt/{id}")
    public String confirmReceipt(@PathVariable Integer id){

        orderService.confirmReceipt(id);

        return "forward:/orders";
    }



    @RequestMapping("/orderToBuy")
    public String orderToBuy(HttpServletRequest request,Model model){

        Integer orderId = Integer.parseInt(request.getParameter("orderId"));


        OrderVo orderVo = orderService.findByOrderId(orderId);

        model.addAttribute("order",orderVo);

        return "alipayPage";
    }


}
