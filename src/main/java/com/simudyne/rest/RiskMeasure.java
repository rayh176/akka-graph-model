package com.simudyne.rest;

import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

public class RiskMeasure implements Serializable{

    private static final long serialVersionUID = 1L;

    @Value(value="Agent_Breed")
    private String agentBreed;

    @Value(value="Policy_ID")
    private String policyId;

    @Value(value="Age")
    private int age;

    @Value(value="Social_Grade")
    private int socialGrade;

    @Value(value="Payment_at_Purchase")
    private int paymentAtPurchase;

    @Value(value="Attribute_Brand")
    private double attributeBrand;

    @Value(value="Attribute_Price")
    private double attributePrice;

    @Value(value="Attribute_Promotions")
    private double attributePromotions;

    @Value(value="Auto_Renew")
    private int autoRenew;

    @Value(value="Inertia_for_Switch")
    private int inertiaForSwitch;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAgentBreed() {
        return agentBreed;
    }

    public void setAgentBreed(String agentBreed) {
        this.agentBreed = agentBreed;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSocialGrade() {
        return socialGrade;
    }

    public void setSocialGrade(int socialGrade) {
        this.socialGrade = socialGrade;
    }

    public int getPaymentAtPurchase() {
        return paymentAtPurchase;
    }

    public void setPaymentAtPurchase(int paymentAtPurchase) {
        this.paymentAtPurchase = paymentAtPurchase;
    }

    public double getAttributeBrand() {
        return attributeBrand;
    }

    public void setAttributeBrand(double attributeBrand) {
        this.attributeBrand = attributeBrand;
    }

    public double getAttributePrice() {
        return attributePrice;
    }

    public void setAttributePrice(double attributePrice) {
        this.attributePrice = attributePrice;
    }

    public double getAttributePromotions() {
        return attributePromotions;
    }

    public void setAttributePromotions(double attributePromotions) {
        this.attributePromotions = attributePromotions;
    }

    public int getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(int autoRenew) {
        this.autoRenew = autoRenew;
    }

    public int getInertiaForSwitch() {
        return inertiaForSwitch;
    }

    public void setInertiaForSwitch(int inertiaForSwitch) {
        this.inertiaForSwitch = inertiaForSwitch;
    }

    public RiskMeasure deepClone(int age) {
        RiskMeasure riskMeasure =  new RiskMeasure();
        riskMeasure.agentBreed = agentBreed;
        riskMeasure.policyId= policyId;
        riskMeasure.age = age;
        riskMeasure.socialGrade = socialGrade;
        riskMeasure.paymentAtPurchase = paymentAtPurchase;
        riskMeasure.attributeBrand = attributeBrand;
        riskMeasure.attributePrice = attributePrice;
        riskMeasure.attributePromotions = attributePromotions;
        riskMeasure.autoRenew = autoRenew;
        riskMeasure.inertiaForSwitch = inertiaForSwitch;
        return riskMeasure;
    }

    @Override
    public String toString() {
        return "RiskMeasure{" +
                "agentBreed='" + agentBreed + '\'' +
                ", policyId=" + policyId +
                ", age=" + age +
                ", socialGrade=" + socialGrade +
                ", paymentAtPurchase=" + paymentAtPurchase +
                ", attributeBrand=" + attributeBrand +
                ", attributePrice=" + attributePrice +
                ", attributePromotions=" + attributePromotions +
                ", autoRenew=" + autoRenew +
                ", inertiaForSwitch=" + inertiaForSwitch +
                '}';
    }

}
