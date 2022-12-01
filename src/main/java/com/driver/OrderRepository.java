package com.driver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository
{
    private Map<String, Order> map_order = new HashMap<>();
    private Map<String, DeliveryPartner> map_partner = new HashMap<>();
    private Map<String, String> map_order_partner = new HashMap<>();

    private Map<String, String> map_partner_lastOrder = new HashMap<>();

    public void addOrder(Order order)
    {
        map_order.put(order.getId(), order);
    }

    public void addPartner( String partnerId)
    {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        map_partner.put(deliveryPartner.getId(), deliveryPartner);
    }

    public void addOrderPartnerPair( String orderId,  String partnerId)
    {
        map_order_partner.put(orderId,partnerId);

        map_partner_lastOrder.put(partnerId,orderId);

        int count =  map_partner.get(partnerId).getNumberOfOrders();
        count++;
        map_partner.get(partnerId).setNumberOfOrders(count);
    }

    public Order getOrderById( String orderId)
    {
        return map_order.get(orderId);
    }

    public DeliveryPartner getPartnerById( String partnerId)
    {
        return map_partner.get(partnerId);
    }

    public Integer getOrderCountByPartnerId( String partnerId)
    {
        return map_partner.get(partnerId).getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId( String partnerId)
    {
        List<String> ans = new ArrayList<>();

        for(String order_id : map_order_partner.keySet())
        {
            if(map_order_partner.get(order_id).equalsIgnoreCase(partnerId))
            {
                ans.add(order_id);
            }
        }
        return ans;
    }

    public List<String> getAllOrders()
    {
        List<String> ans = new ArrayList<>(map_order.keySet());
        return ans;
    }

    public Integer getCountOfUnassignedOrders()
    {
        int count =0;
        for(String order_id : map_order.keySet())
        {
            if(!map_order_partner.containsKey(order_id)) count++;
        }
        return count;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId( String time,  String partnerId)
    {
        int count = 0;
        for(String order_id : map_order_partner.keySet())
        {
            if(map_order_partner.get(order_id).equalsIgnoreCase(partnerId))
            {
                Order order = map_order.get(order_id);
                if(order.getDeliveryTime() > order.convert(time))  count++;
            }
        }
        return count;
    }


    public String getLastDeliveryTimeByPartnerId( String partnerId)
    {
        int time = map_order.get(map_partner_lastOrder.get(partnerId)).getDeliveryTime();
        String hr = String.valueOf(time/60);
        String min = String.valueOf(time%60);
        if(min.length() == 1) min = "0"+min;

        return hr+":"+min;
    }

    public void deletePartnerById( String partnerId)
    {
        map_partner.remove(partnerId);

        List<String> temp = new ArrayList<>(map_order_partner.keySet());

        for(String orderId : temp)
        {
            if(map_order_partner.get(orderId).equalsIgnoreCase(partnerId) ) map_order_partner.remove(orderId);
        }
    }

    public void deleteOrderById( String orderId)
    {
        map_order.remove(orderId);
        map_order_partner.remove(orderId);

    }


}
