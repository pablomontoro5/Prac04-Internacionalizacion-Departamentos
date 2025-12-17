package util;

import com.itextpdf.text.Document;            // <-- FIX 1 (PDF)
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import data.Data;
import model.Departamento;

import com.google.gson.Gson;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;                               // <-- FIX 2 (BufferedReader, FileReader)
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Exporter {

    // -------------------------------------------------------
    // EXPORT CSV
    // -------------------------------------------------------
    public static void exportCSV(String file) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("ID;Nombre;Localidad\n");
            for (Departamento d : Data.getDepartamentos()) {
                fw.write(d.getId() + ";" + d.getNombre() + ";" + d.getLocalidad() + "\n");
            }
            Logger.log("Exportado CSV a " + file);
        } catch (Exception e) { e.printStackTrace(); }
    }


    // -------------------------------------------------------
    // EXPORT JSON
    // -------------------------------------------------------
    public static void exportJSON(String file) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(new Gson().toJson(Data.getDepartamentos()));
            Logger.log("Exportado JSON a " + file);
        } catch (Exception e) { e.printStackTrace(); }
    }


    // -------------------------------------------------------
    // EXPORT XML
    // -------------------------------------------------------
    public static void exportXML(String file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.newDocument();

            Element root = doc.createElement("departamentos");
            doc.appendChild(root);

            for (Departamento d : Data.getDepartamentos()) {
                Element dep = doc.createElement("departamento");
                root.appendChild(dep);

                Element e1 = doc.createElement("id");
                e1.setTextContent(String.valueOf(d.getId()));
                dep.appendChild(e1);

                Element e2 = doc.createElement("nombre");
                e2.setTextContent(d.getNombre());
                dep.appendChild(e2);

                Element e3 = doc.createElement("localidad");
                e3.setTextContent(d.getLocalidad());
                dep.appendChild(e3);
            }

            var transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(doc), new StreamResult(new File(file)));

            Logger.log("Exportado XML a " + file);

        } catch (Exception e) { e.printStackTrace(); }
    }


    // -------------------------------------------------------
    // EXPORT EXCEL
    // -------------------------------------------------------
    public static void exportExcel(String file) {
        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet("Departamentos");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Nombre");
            header.createCell(2).setCellValue("Localidad");

            int rowIndex = 1;
            for (Departamento d : Data.getDepartamentos()) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(d.getId());
                row.createCell(1).setCellValue(d.getNombre());
                row.createCell(2).setCellValue(d.getLocalidad());
            }

            FileOutputStream fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();

            Logger.log("Exportado Excel a " + file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // -------------------------------------------------------
    // EXPORT PDF
    // -------------------------------------------------------
    public static void exportPDF(String file) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            doc.add(new Paragraph("Listado de Departamentos\n\n"));

            for (Departamento d : Data.getDepartamentos()) {
                doc.add(new Paragraph(
                        "ID: " + d.getId() +
                                "   Nombre: " + d.getNombre() +
                                "   Localidad: " + d.getLocalidad()
                ));
            }

            doc.close();

            Logger.log("Exportado PDF a " + file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // -------------------------------------------------------
    // IMPORT CSV
    // -------------------------------------------------------
    public static void importCSV(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] p = line.split(";");

                Departamento d = new Departamento(p[1], p[2]);

                // Forzar ID
                var field = Departamento.class.getDeclaredField("id");
                field.setAccessible(true);
                field.setInt(d, Integer.parseInt(p[0]));

                Data.addDepartamento(d);
            }

            Logger.log("Importado CSV: " + file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // -------------------------------------------------------
    // IMPORT XML
    // -------------------------------------------------------
    public static void importXML(String file) {
        try {
            var doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new File(file));

            var nodes = doc.getElementsByTagName("departamento");

            for (int i = 0; i < nodes.getLength(); i++) {

                Element dep = (Element) nodes.item(i);

                int id = Integer.parseInt(dep.getElementsByTagName("id").item(0).getTextContent());
                String nombre = dep.getElementsByTagName("nombre").item(0).getTextContent();
                String localidad = dep.getElementsByTagName("localidad").item(0).getTextContent();

                Departamento d = new Departamento(nombre, localidad);

                var field = Departamento.class.getDeclaredField("id");
                field.setAccessible(true);
                field.setInt(d, id);

                Data.addDepartamento(d);
            }

            Logger.log("Importado XML: " + file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
