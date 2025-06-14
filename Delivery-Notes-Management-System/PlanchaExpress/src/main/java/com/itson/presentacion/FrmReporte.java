/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.itson.presentacion;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itson.dominio.NotaRemision;
import com.itson.dominio.NotaServicio;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import negocio.DateLabelFormatter;
import negocio.ILogica;
import negocio.LogicaNegocio;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author alexasoto
 */
public class FrmReporte extends javax.swing.JFrame {

    ILogica logica = new LogicaNegocio();
    NotaRemision nota;
    Double total = 0.0;
    Double anticipos = 0.0;
    Double pagos = 0.0;
    Integer notasCreadas = 0;
    Integer notasEntregadas = 0;
    private JDatePickerImpl datePickerFrom;
    private JDatePickerImpl datePickerTo;
    private String tipoReporte;

    /**
     * Creates new form FrmConsulServicios
     */
    public FrmReporte() {
        initComponents();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initializeDatePickers();
        addComponentsToPanel();
        addEventListeners();
        pack();
    }

    private void initializeDatePickers() {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        UtilDateModel modelFrom = new UtilDateModel();
        JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, p);
        datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());

        UtilDateModel modelTo = new UtilDateModel();
        JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, p);
        datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
    }

    private void addComponentsToPanel() {
        pnlFondo.add(datePickerFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));
        pnlFondo.add(datePickerTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                llenarTablaNotas();
            }
        });
        pnlFondo.add(btnFiltrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, -1, -1));
    }

    private void addEventListeners() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FrmReportes frmReportes = new FrmReportes();
                frmReportes.setVisible(true);
            }
        });
    }

    public void llenarTablaNotas() {
        Date dateFrom = (Date) datePickerFrom.getModel().getValue();
        Date dateTo = (Date) datePickerTo.getModel().getValue();
        LocalDate fechaDesde = dateFrom != null ? dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate fechaHasta = dateTo != null ? dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

        List<NotaRemision> notas = logica.recuperarnotas();
        DefaultTableModel creadas = (DefaultTableModel) this.tblCreadas.getModel();
        DefaultTableModel entregadas = (DefaultTableModel) this.tblEntregadas.getModel();
        creadas.setRowCount(0);
        entregadas.setRowCount(0);

        int anticipos = 0;
        int pagos = 0;
        int notasCreadas = 0;
        int notasEntregadas = 0;
        int total = 0;

        for (NotaRemision nota : notas) {
            LocalDate fechaRecepcion = nota.getFecha_recepcion().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            StringBuilder servicios = new StringBuilder();
            StringBuilder perdidas = new StringBuilder();
            for (NotaServicio notaServicio : nota.getNotaServicios()) {
                servicios.append(notaServicio.getServicio().getDescripcion())
                        .append(", ").append(notaServicio.getCant())
                        .append(", ").append(notaServicio.getServicio().getPrecio())
                        .append(", ").append(notaServicio.getPrecio()).append("\n");
                perdidas.append(notaServicio.getDetalles()).append(", ").append(notaServicio.getPerdidas()).append("\n");
            }

            if ((fechaDesde == null || !fechaRecepcion.isBefore(fechaDesde)) && (fechaHasta == null || !fechaRecepcion.isAfter(fechaHasta))) {
                Object[] filaNueva = {nota.getFolio(), nota.getCliente(), servicios.toString().trim(), perdidas.toString().trim(), nota.getTotal(), nota.getAnticipo(), nota.getFecha_recepcion()};
                creadas.addRow(filaNueva);
                anticipos += nota.getAnticipo();
                notasCreadas++;
            }

            if (nota.getFecha_entregada() != null) {
                LocalDate fechaEntregada = nota.getFecha_entregada().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if ((fechaDesde == null || !fechaEntregada.isBefore(fechaDesde)) && (fechaHasta == null || !fechaEntregada.isAfter(fechaHasta))) {
                    Object[] filaNueva = {nota.getFolio(), nota.getCliente(), servicios.toString().trim(), perdidas.toString().trim(), nota.getTotal(), nota.getTotal() - nota.getAnticipo(), nota.getFecha_entregada()};
                    entregadas.addRow(filaNueva);
                    pagos += nota.getTotal() - nota.getAnticipo();
                    notasEntregadas++;
                }
            }
        }

        total = anticipos + pagos;
        this.txtAnticipos.setText(String.valueOf(anticipos));
        this.txtPagos.setText(String.valueOf(pagos));
        this.txtGanancias.setText(String.valueOf(total));
        this.txtCreadas.setText(String.valueOf(notasCreadas));
        this.txtEntregadas.setText(String.valueOf(notasEntregadas));

        // Asignar el MultiLineCellRenderer a la columna de servicios (suponiendo que la tercera columna es la de servicios)
        tblCreadas.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer());
        tblCreadas.getColumnModel().getColumn(3).setCellRenderer(new MultiLineCellRenderer());
        tblEntregadas.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer());
        tblEntregadas.getColumnModel().getColumn(3).setCellRenderer(new MultiLineCellRenderer());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFondo = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();
        lblNota1 = new javax.swing.JLabel();
        scrlNotas1 = new javax.swing.JScrollPane();
        tblEntregadas = new javax.swing.JTable();
        lblNota2 = new javax.swing.JLabel();
        scrlNotas2 = new javax.swing.JScrollPane();
        tblCreadas = new javax.swing.JTable();
        lblNota3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCreadas = new javax.swing.JTextField();
        txtAnticipos = new javax.swing.JTextField();
        txtEntregadas = new javax.swing.JTextField();
        txtPagos = new javax.swing.JTextField();
        txtGanancias = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnDiario = new javax.swing.JButton();
        btnSemanal = new javax.swing.JButton();
        btnMensual = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlFondo.setBackground(new java.awt.Color(255, 255, 255));
        pnlFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnRegresar.setBackground(new java.awt.Color(153, 204, 255));
        btnRegresar.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.setFocusable(false);
        btnRegresar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegresar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        pnlFondo.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 480, 100, -1));

        lblNota1.setFont(new java.awt.Font("Kannada MN", 0, 20)); // NOI18N
        lblNota1.setText("Reportes");
        pnlFondo.add(lblNota1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        tblEntregadas.setFont(new java.awt.Font("Kannada MN", 0, 14)); // NOI18N
        tblEntregadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Folio", "Cliente", "Servicios", "Perdidas", "Total", "Resto Pagado", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrlNotas1.setViewportView(tblEntregadas);

        pnlFondo.add(scrlNotas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 860, 90));

        lblNota2.setFont(new java.awt.Font("Kannada MN", 0, 20)); // NOI18N
        lblNota2.setText("Entregadas");
        pnlFondo.add(lblNota2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, -1, -1));

        tblCreadas.setFont(new java.awt.Font("Kannada MN", 0, 14)); // NOI18N
        tblCreadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Folio", "Cliente", "Servicios", "Perdidas", "Total", "Anticipo", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrlNotas2.setViewportView(tblCreadas);

        pnlFondo.add(scrlNotas2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 860, 90));

        lblNota3.setFont(new java.awt.Font("Kannada MN", 0, 20)); // NOI18N
        lblNota3.setText("Creadas");
        pnlFondo.add(lblNota3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Ganancias totales:");
        pnlFondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 470, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Total de notas creadas:");
        pnlFondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Total de notas entregadas:");
        pnlFondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Total en anticipos recibido:");
        pnlFondo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 270, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Total en pagos recibido:");
        pnlFondo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 440, -1, -1));
        pnlFondo.add(txtCreadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, 60, -1));
        pnlFondo.add(txtAnticipos, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 270, 60, -1));
        pnlFondo.add(txtEntregadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 60, -1));
        pnlFondo.add(txtPagos, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 440, 60, -1));
        pnlFondo.add(txtGanancias, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 470, 60, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Generar Pdf");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pnlFondo.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 480, -1, -1));

        btnDiario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDiario.setText("Diario");
        btnDiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiarioActionPerformed(evt);
            }
        });
        pnlFondo.add(btnDiario, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 20, -1, -1));

        btnSemanal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSemanal.setText("Semanal");
        btnSemanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSemanalActionPerformed(evt);
            }
        });
        pnlFondo.add(btnSemanal, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 70, -1, -1));

        btnMensual.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnMensual.setText("Mensual");
        btnMensual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMensualActionPerformed(evt);
            }
        });
        pnlFondo.add(btnMensual, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 120, -1, -1));

        getContentPane().add(pnlFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 530));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        dispose();
        FrmReportes frmReportes = new FrmReportes();
        frmReportes.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        exportarAPDF();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnDiarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiarioActionPerformed
        // TODO add your handling code here:
        tipoReporte="Diario";
        setRangoFechasDiario();
        llenarTablaNotas();
    }//GEN-LAST:event_btnDiarioActionPerformed

    private void btnSemanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSemanalActionPerformed
        // TODO add your handling code here:
        tipoReporte="Semanal";
        setRangoFechasSemanal();
        llenarTablaNotas();
    }//GEN-LAST:event_btnSemanalActionPerformed

    private void btnMensualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMensualActionPerformed
        // TODO add your handling code here:
        tipoReporte="Mensual";
        setRangoFechasMensual();
        llenarTablaNotas();
    }//GEN-LAST:event_btnMensualActionPerformed

    private void setRangoFechasDiario() {
        LocalDate hoy = LocalDate.now();
        setFechasEnDatePickers(hoy, hoy);
    }

    private void setRangoFechasSemanal() {
        LocalDate hoy = LocalDate.now();
        LocalDate primerDiaSemana = hoy.with(java.time.DayOfWeek.MONDAY);
        LocalDate ultimoDiaSemana = hoy.with(java.time.DayOfWeek.SUNDAY);
        setFechasEnDatePickers(primerDiaSemana, ultimoDiaSemana);
    }

    private void setRangoFechasMensual() {
        LocalDate hoy = LocalDate.now();
        LocalDate primerDiaMes = hoy.withDayOfMonth(1);
        LocalDate ultimoDiaMes = hoy.withDayOfMonth(hoy.lengthOfMonth());
        setFechasEnDatePickers(primerDiaMes, ultimoDiaMes);
    }

    private void setFechasEnDatePickers(LocalDate fechaDesde, LocalDate fechaHasta) {
        // Convertir LocalDate a Calendar
        Calendar calDesde = Calendar.getInstance();
        calDesde.set(fechaDesde.getYear(), fechaDesde.getMonthValue() - 1, fechaDesde.getDayOfMonth());

        Calendar calHasta = Calendar.getInstance();
        calHasta.set(fechaHasta.getYear(), fechaHasta.getMonthValue() - 1, fechaHasta.getDayOfMonth());

        // Asignar las fechas a los DatePickers
        datePickerFrom.getModel().setDate(calDesde.get(Calendar.YEAR), calDesde.get(Calendar.MONTH), calDesde.get(Calendar.DAY_OF_MONTH));
        datePickerFrom.getModel().setSelected(true);

        datePickerTo.getModel().setDate(calHasta.get(Calendar.YEAR), calHasta.get(Calendar.MONTH), calHasta.get(Calendar.DAY_OF_MONTH));
        datePickerTo.getModel().setSelected(true);
    }

    public void exportarAPDF() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Reporte" + tipoReporte + ".pdf"));
            document.open();

            // Configuración de fuente
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font subtituloFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font textoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            // Agregar título del reporte
            Paragraph titulo = new Paragraph("Reporte " + tipoReporte + " de Actividades", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            
            String rango=this.datePickerFrom.getModel().getDay() + "/" 
                    + this.datePickerFrom.getModel().getMonth() + "/"  
                    + this.datePickerFrom.getModel().getYear() + " - " 
                    + this.datePickerTo.getModel().getDay() + "/" 
                    + this.datePickerTo.getModel().getMonth() + "/"  
                    + this.datePickerTo.getModel().getYear();
            Paragraph range = new Paragraph(rango, textoFont);
            range.setAlignment(Element.ALIGN_CENTER);
            document.add(range);
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Agregar fecha del reporte
            String fechaReporte = "Fecha del reporte: " + java.time.LocalDate.now();
            Paragraph fecha = new Paragraph(fechaReporte, textoFont);
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);

            // Agregar espacio
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Agregar información de encabezado adicional (opcional)
            Paragraph autor = new Paragraph("Generado por: Sistema de Gestión de Lavandería", textoFont);
            autor.setAlignment(Element.ALIGN_CENTER);
            document.add(autor);

            // Agregar más espacio
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Agregar subtítulo para Notas Creadas
            Paragraph subtituloCreadas = new Paragraph("Notas Creadas", subtituloFont);
            document.add(subtituloCreadas);
            document.add(new Paragraph(" ")); // Espacio en blanco
            // Agregar tabla de notas creadas
            PdfPTable tableCreadas = new PdfPTable(tblCreadas.getColumnCount());
            for (int i = 0; i < tblCreadas.getColumnCount(); i++) {
                PdfPCell headerCell = new PdfPCell(new Phrase(tblCreadas.getColumnName(i), textoFont));
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY); // Color de fondo para encabezados
                tableCreadas.addCell(headerCell);
            }
            for (int i = 0; i < tblCreadas.getRowCount(); i++) {
                for (int j = 0; j < tblCreadas.getColumnCount(); j++) {
                    tableCreadas.addCell(new PdfPCell(new Phrase(tblCreadas.getValueAt(i, j).toString(), textoFont)));
                }
            }
            document.add(tableCreadas);

            // Agregar más espacio
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Agregar subtítulo para Notas Entregadas
            Paragraph subtituloEntregadas = new Paragraph("Notas Entregadas", subtituloFont);
            document.add(subtituloEntregadas);
            document.add(new Paragraph(" ")); // Espacio en blanco
            // Agregar tabla de notas entregadas
            PdfPTable tableEntregadas = new PdfPTable(tblEntregadas.getColumnCount());
            for (int i = 0; i < tblEntregadas.getColumnCount(); i++) {
                PdfPCell headerCell = new PdfPCell(new Phrase(tblEntregadas.getColumnName(i), textoFont));
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY); // Color de fondo para encabezados
                tableEntregadas.addCell(headerCell);
            }
            for (int i = 0; i < tblEntregadas.getRowCount(); i++) {
                for (int j = 0; j < tblEntregadas.getColumnCount(); j++) {
                    tableEntregadas.addCell(new PdfPCell(new Phrase(tblEntregadas.getValueAt(i, j).toString(), textoFont)));
                }
            }
            document.add(tableEntregadas);

            // Agregar más espacio
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Agregar secciones de totales
            Paragraph totales = new Paragraph("Totales", subtituloFont);
            document.add(totales);

            document.add(new Paragraph("Total de notas creadas: " + txtCreadas.getText(), textoFont));
            document.add(new Paragraph("Total en anticipos recibidos: " + txtAnticipos.getText(), textoFont));
            document.add(new Paragraph("Total de notas entregadas: " + txtEntregadas.getText(), textoFont));
            document.add(new Paragraph("Total en pagos recibidos: " + txtPagos.getText(), textoFont));
            document.add(new Paragraph("Ganancias totales: " + txtGanancias.getText(), textoFont));

            // Cerrar el documento
            document.close();
            JOptionPane.showMessageDialog(this, "El reporte ha sido creado con exito");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) < getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDiario;
    private javax.swing.JButton btnMensual;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnSemanal;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblNota1;
    private javax.swing.JLabel lblNota2;
    private javax.swing.JLabel lblNota3;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JScrollPane scrlNotas1;
    private javax.swing.JScrollPane scrlNotas2;
    private javax.swing.JTable tblCreadas;
    private javax.swing.JTable tblEntregadas;
    private javax.swing.JTextField txtAnticipos;
    private javax.swing.JTextField txtCreadas;
    private javax.swing.JTextField txtEntregadas;
    private javax.swing.JTextField txtGanancias;
    private javax.swing.JTextField txtPagos;
    // End of variables declaration//GEN-END:variables
}
