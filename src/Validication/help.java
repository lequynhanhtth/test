/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validication;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrator
 */
public class help {

    public static void ExportExcel(File file, ResultSet rs) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Thông tin nhân viên");
            XSSFRow row;
            ResultSetMetaData rsmd = rs.getMetaData();
            XSSFCell cell1;
            XSSFCell cell2;
            XSSFCell cell3;
            XSSFCell cell4;
            XSSFCell cell5;
            XSSFCell cell6;
            XSSFCell cell7;
            XSSFCell cell8;
            XSSFCell cell9;
            XSSFCell cell10;
            XSSFCell cell11;
            XSSFCell cell12;
            XSSFCell cell13;
            row = sheet.createRow(0);
            cell1 = row.createCell(0);
            cell1.setCellValue(rsmd.getColumnName(1));
            cell2 = row.createCell(1);
            cell2.setCellValue(rsmd.getColumnName(2));
            cell3 = row.createCell(2);
            cell3.setCellValue(rsmd.getColumnName(3));
            cell4 = row.createCell(3);
            cell4.setCellValue(rsmd.getColumnName(4));
            cell5 = row.createCell(4);
            cell5.setCellValue(rsmd.getColumnName(5));
            cell6 = row.createCell(5);
            cell6.setCellValue(rsmd.getColumnName(6));
            cell7 = row.createCell(6);
            cell7.setCellValue(rsmd.getColumnName(7));
            cell8 = row.createCell(7);
            cell8.setCellValue(rsmd.getColumnName(8));
            cell9 = row.createCell(8);
            cell9.setCellValue(rsmd.getColumnName(9));
            cell10 = row.createCell(9);
            cell10.setCellValue(rsmd.getColumnName(10));
            cell11 = row.createCell(10);
            cell11.setCellValue(rsmd.getColumnName(11));
            cell12 = row.createCell(11);
            cell12.setCellValue(rsmd.getColumnName(12));
            cell13 = row.createCell(12);
            cell13.setCellValue(rsmd.getColumnName(13));
            int i = 1;
            rs.beforeFirst();
            while (rs.next()) {
                row = sheet.createRow(i);
                cell1 = row.createCell(0);
                cell1.setCellValue(rs.getString(1));
                cell2 = row.createCell(1);
                cell2.setCellValue(rs.getString(2));
                cell3 = row.createCell(2);
                cell3.setCellValue(rs.getString(3));
                cell4 = row.createCell(3);
                cell4.setCellValue(rs.getString(4));
                cell5 = row.createCell(4);
                cell5.setCellValue(rs.getString(5));
                cell6 = row.createCell(5);
                cell6.setCellValue(rs.getString(6));
                cell7 = row.createCell(6);
                cell7.setCellValue(rs.getString(7));
                cell8 = row.createCell(7);
                cell8.setCellValue(rs.getString(8));
                cell9 = row.createCell(8);
                cell9.setCellValue(rs.getString(9));
                cell10 = row.createCell(9);
                cell10.setCellValue(rs.getString(10));
                cell11 = row.createCell(10);
                cell11.setCellValue(rs.getString(11));
                cell12 = row.createCell(11);
                cell12.setCellValue(rs.getString(12));
                cell13 = row.createCell(12);
                cell13.setCellValue(rs.getString(13));
                i++;
            }
            workbook.write(out);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
