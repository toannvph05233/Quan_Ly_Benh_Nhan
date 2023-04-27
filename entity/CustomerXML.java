package qlsv_swing.qlsv.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerXML {
    
    private List<Customer> customer;

    public List<Customer> getStudent() {
        return customer;
    }

    public void setStudent(List<Customer> customer) {
        this.customer = customer;
    }
}