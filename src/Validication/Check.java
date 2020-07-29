/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validication;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class Check {

    public static boolean checkEmpty(JTextField Text, StringBuilder sb, String s) {
        if (Text.getText().length() == 0) {
            sb.append(s);
            sb.append("\n");
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkEmpty(JTextField Text) {
        if (Text.getText().length() == 0) {

            return false;
        } else {
            return true;
        }
    }

    //Kiểm tra Trùng lặp
    public static boolean CheckDuplicate(String s, Vector data, StringBuilder sb, String error) {
        for (int i = 0; i < data.size(); i++) {
            if (((String) data.get(i)).equalsIgnoreCase(s)) {
                sb.append(error + "\n");
                return false;
            }
        }
        return true;
    }

    //Kiểm tra ký tự đặc biệt
    public static boolean checkKTDB(JTextField text, StringBuilder sb, String s) {
        if (!text.getText().matches("[\\w]+")) {
            sb.append(s + "\n");
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkKTDB(JTextField text) {
        if (!text.getText().matches("[\\w]+")) {
            return false;
        } else {
            return true;
        }
    }
}
