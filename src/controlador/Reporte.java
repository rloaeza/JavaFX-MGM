package controlador;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reporte extends JFrame {
    public void mostrarReporte2(String reporte, ArrayList<Map<String, Object>>valores) throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(reporte);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(valores);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
        // System.out.print("Done!");



    }
    public void mostrarReporte(String reporte, ArrayList<HashMap<String, Object> >valores) throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(reporte);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(valores);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
       // System.out.print("Done!");



    }
}
