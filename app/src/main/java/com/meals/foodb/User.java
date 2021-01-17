package com.meals.foodb;

import android.content.Context;
import android.content.SharedPreferences;

//nepamirsti parasyti public, nes automatiskai imes protected
public class User {
    //1. objekto kintamieji (pozymiai, argumentai)
    private String username;
    private String password;
    private String email;

    //shared preferences(bendros nuostatos) - privatus duomenys prisijungimui prie aplikacijos
    private SharedPreferences sharedPreferences;//private-matomi klases ribose tiktai
    private static final String PREFERENCES_PACKAGE_NAME="com.corona.coronazp20t";
    private static final String USERNAME_KEY="username";//final - reiskia, kad reiksme nekis (kaip const javascript'e), negalima jos visoje programoje pakeisti
    private static final String PASSWORD_KEY="password";//static - kad nekurdami objekto galim prie jo prieiti
    private static final String REMEMBERME_KEY="rememberMe";

    //2. konstruktorius
//spaudem desini peles klavisa ir generate->constructor
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //3. klases metodai (get and set)
    //vel desini peles generate-> getter and settes
    //konstruktorius skirtas vartotojo prisijungimui

    public User(Context context) {//context-langas kuriame esame, perduodame langa konstruktoriui kuriame esame ir tokiu budu sukuriame vieta kur saugome duomenis
        this.sharedPreferences = context.getSharedPreferences(User.PREFERENCES_PACKAGE_NAME,
                Context.MODE_PRIVATE);
    }

    //3. klases metodai (get and set)
    //vel desini peles generate-> getter and setter
    // set, get naudodami prieiname prie pozymiu
    public String getUsernameForRegistration() {
        return username;
    }

    public void setUsernameForRegistration(String username) {
        this.username = username;
    }

    public String getPasswordForRegistration() {
        return password;
    }

    public void setPasswordForRegistration(String password) {
        this.password = password;
    }

    public String getEmailForRegistration() {
        return email;
    }

    public void setEmailForRegistration(String email) {
        this.email = email;
    }

    public String getUsernameForLogin() {
        return this.sharedPreferences.getString(USERNAME_KEY, "");
    }

    public void setUsernameForLogin(String username) {
        this.sharedPreferences.edit().putString(USERNAME_KEY,username).commit();//commit reikalingas pakeitimu issaugojimui
    }

    public String getPasswordForLogin() {
        return this.sharedPreferences.getString(PASSWORD_KEY,"");
    }

    public void setPasswordForLogin(String password) {
        this.sharedPreferences.edit().putString(PASSWORD_KEY, password).commit();
    }

    public boolean isRememberedForLogin() {
        return this.sharedPreferences.getBoolean(REMEMBERME_KEY,false);//getBoolean, nes checkbox yra boolean
    }

    public void setRemembermeKeyForLogin(boolean remembermeKey) {
        this.sharedPreferences.edit().putBoolean(REMEMBERME_KEY,remembermeKey).commit();
    }
}