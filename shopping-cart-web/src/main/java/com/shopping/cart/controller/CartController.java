package com.shopping.cart.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping.cart.service.CartService;
import com.shopping.common.utils.CookieUtils;
import com.shopping.common.utils.JsonUtils;
import com.shopping.common.utils.TaotaoResult;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbUser;
import com.shopping.service.ItemService;

@Controller
public class CartController {
	
	@Value("${CART_COOKIE_TIME}")
	private Integer CART_COOKIE_TIME;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/cart/add/{itemId}")
	public String addItemToCart(HttpServletRequest req,HttpServletResponse res,@PathVariable long itemId,@RequestParam(defaultValue="1")Integer num) {
		
		TbUser user = (TbUser) req.getAttribute("user");
		if(user != null) {
			TaotaoResult result = cartService.addItemToCart(user.getId(),itemId,num);
			return "cartSuccess";
		}
		
		List<TbItem> list = getCarts(req);
		boolean flag = false;
		for (TbItem tbItem : list) {
			if(tbItem.getId() == itemId) {
				tbItem.setNum(tbItem.getNum()+num);
				flag=true;
				break;
			}
		}
		if(!flag) {
			TbItem item = itemService.findItemInfoById(itemId);
			item.setNum(num);
			item.setImage(item.getImage().split(",")[0]);
			list.add(item);
		}
		
		CookieUtils.setCookie(req, res, "cart", JsonUtils.objectToJson(list), CART_COOKIE_TIME, true);
		return "cartSuccess";
	}
	
	@RequestMapping("cart/cart")
	public String showCartItems(HttpServletRequest req,HttpServletResponse res) {
		TbUser user = (TbUser) req.getAttribute("user");
		String cookitem = CookieUtils.getCookieValue(req, "cart", true);
		List<TbItem> cart = JsonUtils.jsonToList(cookitem, TbItem.class);
		if(user != null) {
			TaotaoResult result = cartService.cookieMergeToRedis(user.getId(),cart);
			CookieUtils.deleteCookie(req,res,"cart");
			cart =cartService.showCartItems(user.getId());
		}
		req.setAttribute("cartList",cart);
		
		return "cart";
	}
		
	
	@RequestMapping("/cart/update/num/{itemId}/{itemNum}")
	public TaotaoResult updateCartNumber(@PathVariable long itemId,@PathVariable int itemNum,HttpServletRequest req,HttpServletResponse res) {
		TbUser user = (TbUser) req.getAttribute("user");
		if(user != null) {
			TaotaoResult result = cartService.updateCartNumber(user.getId(),itemId,itemNum);
			return TaotaoResult.ok();
		}
		String cookitem = CookieUtils.getCookieValue(req, "cart", true);
		List<TbItem> cart = JsonUtils.jsonToList(cookitem, TbItem.class);
		for (TbItem tbItem : cart) {
			if(tbItem.getId() == itemNum) {
				tbItem.setNum(itemNum);
				break;
			}
			
		}
		CookieUtils.setCookie(req, res, "cart", JsonUtils.objectToJson(cart), CART_COOKIE_TIME, true);
		
		return TaotaoResult.ok();
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(HttpServletRequest req,HttpServletResponse res,@PathVariable long itemId) {
		TbUser user = (TbUser) req.getAttribute("user");
		if(user != null) {
			TaotaoResult result = cartService.deleteCartItem(user.getId(),itemId);
		}else {
			String cart = CookieUtils.getCookieValue(req, "cart", true);
			List<TbItem> items = JsonUtils.jsonToList(cart, TbItem.class);
			Iterator<TbItem> iterator = items.iterator();
			while(iterator.hasNext()) {
				if(iterator.next().getId() == itemId) {
					iterator.remove();
					break;
				}
			}
		}
		
		return "redirect:/cart/cart.html";
	}
	
	public List<TbItem> getCarts(HttpServletRequest request){
		String carts = CookieUtils.getCookieValue(request, "cart", true);
		
		if(StringUtils.isNotBlank(carts)) {
			return JsonUtils.jsonToList(carts, TbItem.class);
		}
		return new ArrayList<TbItem>();
		
	}
	
}
