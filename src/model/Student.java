/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author lecturer
 */
public class Student implements Serializable {
    private String name;
    private char gender;
    private int password;
    private Account bank;
    public Student(String x1, char x2, int y, double z){
        name = x1;
        gender = x2;
        password = y;
        bank = new Account(z);
    }
    public boolean Login(int pin){
        if(password==pin){
            return true;
        }
        return false;
    }
    public int getPin(){
        return password;
    }
    public char getGender(){
        return gender;
    }
    public String getName(){
        return name;
    }
    public void deposit(double x){
        bank.deposit(x);
    }
    public void withdraw(double x){
        bank.withdraw(x);
    }
    public double getBalance(){
        return bank.getBalance();
    }    
    public void update(){
        String x = JOptionPane.showInputDialog("Enter new name:");
        int y = Integer.parseInt(JOptionPane.showInputDialog("Enter new password:"));
        name = x;
        password = y;
    }
}
