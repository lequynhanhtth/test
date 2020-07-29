/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validication;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class getdate {
    public static String Xdate(JDateChooser date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date.getDate());
    }
    public static Date getXdate(String s){
        try{
        Date date =new SimpleDateFormat("dd/MM/yyyy").parse(s);
        return date;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}
