package com.meals.foodb;

import java.io.Serializable;

public class Meals implements Serializable {//reiskia, kad ne bazinis tipas, objekto klasems reikia nurodyti
    private String ingredients;
    private String name;
    private String tags;
    private String category;
    private String area;

    public Meals(String ingridients, String name, String tags, String category, String area) {
        this.ingredients = ingridients;
        this.name = name;
        this.tags = tags;
        this.category = category;
        this.area = area;
    }

    //skirtas atvaizdavimui duomenu is new_entry_activity
    public Meals(String ingridients, String area, String tags, String category) {
        this.ingredients = ingridients;
        this.area = area;
        this.tags = tags;
        this.category = category;
    }

    public String getIngridients() {
        return ingredients;
    }

    public void setIngredients(String ingridients) {
        this.ingredients = ingridients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String glass) {
        this.area = area;
    }

}