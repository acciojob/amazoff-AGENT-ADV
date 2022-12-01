package com.driver;

public class Order
{
    private String id;
    private int deliveryTime;

    public Order()
    {

    }
    public Order(String id, String deliveryTime)
    {
        this.id = id;
        this.deliveryTime = convert(deliveryTime);

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }
    public int convert(String deliveryTime)
    {
        String[] arr = deliveryTime.split(":");
        int hr = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);

        return (hr*60) + min;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
