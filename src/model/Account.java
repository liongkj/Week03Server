/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author lecturer
 */
public class Account implements Serializable {
    private double balance;
    public Account(double x){
        balance = x;
    }
    public void deposit(double x){
        balance = balance + x;
    }
    public void withdraw(double x){
        balance = balance - x;
    }
    public double getBalance(){
        return balance;
    }
    public void setBalance(double x){
        balance = x;
    }    
}
    

