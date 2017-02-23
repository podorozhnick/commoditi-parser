package com.podorozhnick.commoditiparser;

/**
 * Created by alex on 07.02.17.
 */
public class FlatSale {

    private String auctionNumber;
    private String lotNumber;
    private String date;
    private String categoryName;
    private boolean isLast;
    private String lotName;
    private String auctionDate;
    private String customer;
    private String edrpo;
    private String startPrice;
    private String price;
    private String contractNumber;
    private int house;
    private int flat;
    private double area;
    private int floor;

    public String getAuctionNumber() {
        return auctionNumber;
    }

    public void setAuctionNumber(String auctionNumber) {
        this.auctionNumber = auctionNumber;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getAuctionDate() {
        return auctionDate;
    }

    public void setAuctionDate(String auctionDate) {
        this.auctionDate = auctionDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEdrpo() {
        return edrpo;
    }

    public void setEdrpo(String edrpo) {
        this.edrpo = edrpo;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlatSale)) return false;

        FlatSale flatSale = (FlatSale) o;

        if (getHouse() != flatSale.getHouse()) return false;
        if (getFlat() != flatSale.getFlat()) return false;
        if (getAuctionNumber() != null ? !getAuctionNumber().equals(flatSale.getAuctionNumber()) : flatSale.getAuctionNumber() != null)
            return false;
        if (getLotNumber() != null ? !getLotNumber().equals(flatSale.getLotNumber()) : flatSale.getLotNumber() != null)
            return false;
        return getContractNumber() != null ? getContractNumber().equals(flatSale.getContractNumber()) : flatSale.getContractNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = getAuctionNumber() != null ? getAuctionNumber().hashCode() : 0;
        result = 31 * result + (getLotNumber() != null ? getLotNumber().hashCode() : 0);
        result = 31 * result + (getContractNumber() != null ? getContractNumber().hashCode() : 0);
        result = 31 * result + getHouse();
        result = 31 * result + getFlat();
        return result;
    }

    @Override
    public String toString() {
        return "house=" + house +
                ", floor=" + floor +
                ", flat=" + flat +
                ", area=" + area +
                ", price='" + price + '\'' +
                ", isLast=" + isLast +
                ", lotNumber='" + lotNumber + '\'' +
                ", date='" + date + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", lotName='" + lotName + '\'' +
                ", auctionDate='" + auctionDate + '\'' +
                ", customer='" + customer + '\'' +
                ", edrpo='" + edrpo + '\'' +
                ", startPrice='" + startPrice + '\'' +
                ", contractNumber='" + contractNumber + '\'' +
                ", auctionNumber=" + auctionNumber;
    }
}
