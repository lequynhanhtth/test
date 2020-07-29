        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validication;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Administrator
 */
public class Icon {

    public void SetIcon(JButton btn, String path, int px) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(px, px, image.SCALE_DEFAULT));
            btn.setIcon(icon);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void SetImage(JLabel lbl, String path, int H, int W, StringBuilder sb) {

        try {
            BufferedImage image = ImageIO.read(new File(path));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(W, H, image.SCALE_DEFAULT));
            lbl.setIcon(icon);
        } catch (Exception e) {
            sb.append("Vui lòng chọn ảnh");
        }

    }

    public static void SetImage(JLabel lbl, String path, int H, int W) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(W, H, image.SCALE_DEFAULT));
            lbl.setIcon(icon);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
