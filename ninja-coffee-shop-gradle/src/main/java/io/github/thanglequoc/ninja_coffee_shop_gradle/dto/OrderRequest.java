package io.github.thanglequoc.ninja_coffee_shop_gradle.dto;

public class OrderRequest {
    private String orderID;
    private int itemId;
    private boolean isHot;
    private int sweetness;
    private String size;
    private String customerName;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public int getSweetness() {
        return sweetness;
    }

    public void setSweetness(int sweetness) {
        this.sweetness = sweetness;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderID='" + orderID + '\'' +
                ", itemId=" + itemId +
                ", isHot=" + isHot +
                ", sweetness=" + sweetness +
                ", size='" + size + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
