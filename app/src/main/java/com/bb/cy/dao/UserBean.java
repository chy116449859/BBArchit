package com.bb.cy.dao;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserBean {

    @PrimaryKey
    public long id;

    public String firstName;

    public String lastName;

    public String age;

    //注解@Embedded，允许在一个实体中嵌入另外一个实体，创建的表使用的是当前实体和嵌入实体的所有字段，
    //所以我们可以修改上面的User实体
    @Embedded(prefix = "main")
    public AddressBean addressBean;

    //当一个类中嵌套多个类，并且这些类具有相同的字段，则需要调用@Embedded的属性prefix 添加一个前缀，生成的列名为前缀+列名
    @Embedded(prefix = "vic")
    public AddressBean addressBean2;

    public UserBean() {

    }

    public UserBean(long id, String firstName, String lastName, String age, AddressBean addressBean, AddressBean addressBean2) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.addressBean = addressBean;
        this.addressBean2 = addressBean2;
    }
}
