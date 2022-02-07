package org.zyt.entity;


import com.alibaba.excel.annotation.ExcelProperty;

public class People {
    @ExcelProperty({"人类","姓名"})
    private String name;
    @ExcelProperty({"人类","年龄"})
    private String age;
    @ExcelProperty({"人类","性别"})
    private String sex;
    @ExcelProperty({"人类","身高"})
    private String height;
    @ExcelProperty({"人类","体重"})
    private String weight;
    @ExcelProperty({"人类","体重1"})
    private String weight1;
    @ExcelProperty({"人类","体重2"})
    private String weight2;
    @ExcelProperty({"人类","体重3"})
    private String weight3;


    public String getName() {
        return name;
    }

    public People Name(String name) {
        this.name = name;
        return this;
    }

    public String getAge() {
        return age;
    }

    public People Age(String age) {
        this.age = age;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public People Sex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getHeight() {
        return height;
    }

    public People Height(String height) {
        this.height = height;
        return this;
    }

    public String getWeight() {
        return weight;
    }

    public People Weight(String weight) {
        this.weight = weight;
        return this;
    }

    public String getWeight1() {
        return weight1;
    }

    public People Weight1(String weight1) {
        this.weight1 = weight1;
        return this;
    }

    public String getWeight2() {
        return weight2;
    }

    public People Weight2(String weight2) {
        this.weight2 = weight2;
        return this;
    }

    public String getWeight3() {
        return weight3;
    }

    public People Weight3(String weight3) {
        this.weight3 = weight3;
        return this;
    }
}
