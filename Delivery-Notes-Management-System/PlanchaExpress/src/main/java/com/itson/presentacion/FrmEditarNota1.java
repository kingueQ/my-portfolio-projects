/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.itson.presentacion;

import com.itson.dominio.Cliente;
import com.itson.dominio.NotaRemision;
import com.itson.dominio.NotaServicio;
import com.itson.dominio.Servicio;
import com.itson.dominio.Usuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import negocio.ILogica;
import negocio.LogicaNegocio;

/**
 *
 * @author alexasoto
 */
public class FrmEditarNota1 extends javax.swing.JFrame {

    ILogica logica = new LogicaNegocio();
    List<Servicio> listaServicios = logica.recuperarServicios();
    List<Cliente> listaClientes = logica.recuperarClientes();
    List<Servicio> serviciosSeleccionados = new ArrayList<>();
    List<NotaServicio> referencias = new ArrayList<>();
    private int indice = 0;
    private float total = 0;
    NotaRemision nota1;
    NotaRemision nota2;
    private Date fechaSelec;
    Long user;

    /**
     * Creates new form FrmCrearNota
     */
    public FrmEditarNota1(NotaRemision nota1, Long user) {
        initComponents();
        this.setLocationRelativeTo(null);
        setResizable(false);
        this.nota1 = nota1;
        referencias = nota1.getNotaServicios();
        indice = referencias.size();
        nota2 = nota1;
        total = nota1.getTotal();
        this.user = user;
        agregarBotonesServicios(listaServicios); // Llama al método para agregar los botones correspondientes
//        detallesServicios();
//        txtAnticipo.setText(String.valueOf(0.00));
        this.llenarDatos();

        tblServicios.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int column = e.getColumn();

                    if (column == 5) { // Columna de pérdidas
                        actualizarTotal();
                    }
                }
            }
        });
        // Configuración para ajuste automático de filas y columnas
        tblServicios.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblServicios.setRowHeight(30);  // Ajusta la altura inicial de las filas

// Ajuste de filas basado en el contenido de cada celda
        tblServicios.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        //Listener para el evento de cierre
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                FrmConsulNotas notas = new FrmConsulNotas(user);
                notas.setVisible(true);
            }
        });

        pack();

        JTextField dateTextField = (JTextField) fechaEntrega.getDateEditor().getUiComponent();
        dateTextField.getDocument().addDocumentListener(new DocumentListener() {

            private void updateValue() {
                String text = dateTextField.getText();
                System.out.println("Valor editado en el JDateChooser: " + text);

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    fechaSelec = dateFormat.parse(text);
                    System.out.println("Fecha convertida: " + fechaSelec);
                } catch (ParseException ex) {
                    System.out.println("Formato de fecha inválido");
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateValue();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateValue();
            }
        });
    }

    class MultiLineCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JTextArea textArea = new JTextArea();
            textArea.setText(value == null ? "" : value.toString());
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setOpaque(true);  // Asegura que el fondo del JTextArea sea pintado

            // Ajuste de la altura de la fila según el contenido
            int preferredHeight = textArea.getPreferredSize().height;
            table.setRowHeight(row, preferredHeight);

            // Configuración de colores de fondo y fuente
            if (isSelected) {
                textArea.setBackground(table.getSelectionBackground());
                textArea.setForeground(table.getSelectionForeground());
            } else {
                textArea.setBackground(table.getBackground());
                textArea.setForeground(table.getForeground());
            }

            // Retorno del JTextArea configurado como componente del renderizador
            return textArea;
        }
    }

    private void actualizarTotal() {
        float nuevoTotal = 0;
        for (int i = 0; i < tblServicios.getRowCount(); i++) {
            Float precioTotal = Float.parseFloat(tblServicios.getValueAt(i, 3).toString());
            Float perdidas = Float.parseFloat(tblServicios.getValueAt(i, 5).toString());
            if (perdidas != null && precioTotal != null) {
                nuevoTotal = nuevoTotal + precioTotal - perdidas;
            }
        }
        txtTotal.setText(String.valueOf(nuevoTotal));
    }

    private void llenarDatos() {
        if (nota2 != null) {
            this.txtCliente.setText(nota2.getCliente().getNombre());
            this.txtDireccion.setText(nota2.getCliente().getDireccion());
            this.txtTelefono.setText(nota2.getCliente().getTelefono());
            this.fechaEntrega.setDate(nota2.getFecha_entrega());
            this.txtTotal.setText(String.valueOf(nota2.getTotal()));
            this.txtAnticipo.setText(String.valueOf(nota2.getAnticipo()));
            for (int i = 0; i < nota2.getNotaServicios().size(); i++) {
                DefaultTableModel model = (DefaultTableModel) tblServicios.getModel();
                model.addRow(new Object[]{nota2.getNotaServicios().get(i).getServicio().getDescripcion(),
                    nota2.getNotaServicios().get(i).getCant(),
                    nota2.getNotaServicios().get(i).getServicio().getPrecio(),
                    nota2.getNotaServicios().get(i).getPrecio(),
                    nota2.getNotaServicios().get(i).getDetalles(),
                    nota2.getNotaServicios().get(i).getPerdidas()});
//                this.tblServicios.setValueAt(nota2.getNotaServicios().get(i).getServicio().getDescripcion(), i, 0);
//                this.tblServicios.setValueAt(nota2.getNotaServicios().get(i).getCant(), i, 1);
//                this.tblServicios.setValueAt(nota2.getNotaServicios().get(i).getServicio().getPrecio(), i, 2);
//                this.tblServicios.setValueAt(nota2.getNotaServicios().get(i).getPrecio(), i, 3);
//                this.tblServicios.setValueAt(nota2.getNotaServicios().get(i).getDetalles(), i, 4);
//                this.tblServicios.setValueAt(nota2.getNotaServicios().get(i).getPerdidas(), i, 5);
            }
        }
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
        lblUsuarios = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        lblTelefono = new javax.swing.JLabel();
        btnRegistrar1 = new javax.swing.JButton();
        lblCliente1 = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtAnticipo = new javax.swing.JTextField();
        txtCliente = new javax.swing.JTextField();
        lblServicios1 = new javax.swing.JLabel();
        lblAnticipo = new javax.swing.JLabel();
        lblEntrega1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnRestaurar = new javax.swing.JButton();
        txtTelefono = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        scrlServicios = new javax.swing.JScrollPane();
        tblServicios = new javax.swing.JTable();
        pnlServicios = new javax.swing.JPanel();
        txtDireccion = new javax.swing.JTextField();
        fechaEntrega = new com.toedter.calendar.JDateChooser();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Crear nota de remisión");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlFondo.setBackground(new java.awt.Color(255, 255, 255));
        pnlFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsuarios.setFont(new java.awt.Font("Kannada MN", 0, 20)); // NOI18N
        lblUsuarios.setText("Editar nota de remisión");
        pnlFondo.add(lblUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

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
        pnlFondo.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 620, 100, -1));

        lblTelefono.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        lblTelefono.setText("Teléfono:");
        pnlFondo.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        btnRegistrar1.setBackground(new java.awt.Color(153, 204, 255));
        btnRegistrar1.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        btnRegistrar1.setText("Registrar cliente");
        btnRegistrar1.setFocusable(false);
        btnRegistrar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegistrar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegistrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrar1ActionPerformed(evt);
            }
        });
        pnlFondo.add(btnRegistrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 150, -1));

        lblCliente1.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        lblCliente1.setText("Cliente:");
        pnlFondo.add(lblCliente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        lblDireccion.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        lblDireccion.setText("Dirección:");
        pnlFondo.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        txtAnticipo.setFont(new java.awt.Font("Kannada Sangam MN", 0, 14)); // NOI18N
        pnlFondo.add(txtAnticipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 550, 190, -1));

        txtCliente.setFont(new java.awt.Font("Kannada Sangam MN", 0, 14)); // NOI18N
        txtCliente.setEnabled(false);
        pnlFondo.add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 190, -1));

        lblServicios1.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        lblServicios1.setText("Servicios:");
        pnlFondo.add(lblServicios1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        lblAnticipo.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        lblAnticipo.setText("Anticipo:");
        pnlFondo.add(lblAnticipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 560, -1, -1));

        lblEntrega1.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        lblEntrega1.setText("Fecha de entrega:");
        pnlFondo.add(lblEntrega1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, -1, -1));

        lblTotal.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        lblTotal.setText("Total:");
        pnlFondo.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 520, -1, -1));

        btnRestaurar.setBackground(new java.awt.Color(153, 204, 255));
        btnRestaurar.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        btnRestaurar.setText("Restaurar");
        btnRestaurar.setFocusable(false);
        btnRestaurar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRestaurar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestaurarActionPerformed(evt);
            }
        });
        pnlFondo.add(btnRestaurar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 620, 110, -1));

        txtTelefono.setFont(new java.awt.Font("Kannada Sangam MN", 0, 14)); // NOI18N
        txtTelefono.setEnabled(false);
        pnlFondo.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 190, -1));

        txtTotal.setFont(new java.awt.Font("Kannada Sangam MN", 0, 14)); // NOI18N
        txtTotal.setEnabled(false);
        pnlFondo.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 510, 190, -1));

        scrlServicios.setEnabled(false);
        scrlServicios.setFont(new java.awt.Font("Kannada Sangam MN", 0, 13)); // NOI18N

        tblServicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descripción", "Cantidad", "Precio unitario", "Total", "Detalles", "Perdidas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrlServicios.setViewportView(tblServicios);

        pnlFondo.add(scrlServicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, 430, 160));
        pnlFondo.add(pnlServicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 410, 410));

        txtDireccion.setFont(new java.awt.Font("Kannada Sangam MN", 0, 14)); // NOI18N
        txtDireccion.setEnabled(false);
        pnlFondo.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 190, -1));

        fechaEntrega.setDateFormatString("yyyy-MM-dd HH:mm");
        pnlFondo.add(fechaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 150, -1));

        btnGuardar.setBackground(new java.awt.Color(153, 204, 255));
        btnGuardar.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setFocusable(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        pnlFondo.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 620, 90, -1));

        btnEliminar.setBackground(new java.awt.Color(153, 204, 255));
        btnEliminar.setFont(new java.awt.Font("Kannada MN", 1, 14)); // NOI18N
        btnEliminar.setText("Eliminar Servicio");
        btnEliminar.setFocusable(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        pnlFondo.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 160, -1));

        getContentPane().add(pnlFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 670));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        FrmConsulNotas frm = new FrmConsulNotas(user);
        frm.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnRegistrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrar1ActionPerformed
        // TODO add your handling code here:
        FrmRegistrarCliente frm = new FrmRegistrarCliente();
        frm.setVisible(true);
    }//GEN-LAST:event_btnRegistrar1ActionPerformed

    private void btnRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestaurarActionPerformed
        // TODO add your handling code here:
        this.llenarDatos();
    }//GEN-LAST:event_btnRestaurarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        Calendar calendarioActual = Calendar.getInstance();
        Date fechaActual = calendarioActual.getTime();

        Date fechaSeleccionada = fechaSelec;

        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "La fecha de entrega no puede estar vacía");
            return;
        }

        if (fechaSeleccionada.compareTo(fechaActual) <= 0) {
            JOptionPane.showMessageDialog(this, "La fecha de entrega debe ser posterior a la fecha y hora actual");
            return;
        }
        String anticipoText = txtAnticipo.getText().trim();
        String totalText = txtTotal.getText().trim();

        if (!anticipoText.matches("[-]?\\d*\\.?\\d+") || !totalText.matches("\\d*\\.?\\d+")) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos");
            return;
        }

        float anticipo = Float.parseFloat(anticipoText);
        float total = Float.parseFloat(totalText);

        if (anticipo < 0) {
            JOptionPane.showMessageDialog(this, "El anticipo debe ser un valor numérico mayor o igual a 0");
            return;
        }

        if (anticipo > total) {
            JOptionPane.showMessageDialog(this, "El anticipo no debe ser mayor al monto total");
            return;
        }

        if (tblServicios.getValueAt(0, 0) != null) {
            Cliente cliente = nota1.getCliente();
            Usuario usuario = nota1.getUsuario();
            SimpleDateFormat fecha = new SimpleDateFormat("dd/mm/yy");
            Date fecha_recepcion = new Date();
            nota1.setFecha_recepcion(fecha_recepcion);
            nota1.setFecha_entrega(fechaSeleccionada);
            nota1.setAnticipo(Float.parseFloat(this.txtAnticipo.getText()));
            nota1.setTotal(total);
            for (int i = 0; i < referencias.size(); i++) {
                referencias.get(i).setDetalles(this.tblServicios.getValueAt(i, 4).toString());
                referencias.get(i).setPerdidas(Float.parseFloat(this.tblServicios.getValueAt(i, 5).toString()));
                referencias.get(i).setNota(nota1);
            }
            nota1.setNotaServicios(referencias);
            if (logica.actualizarNotaRemision(nota1)) {
                JOptionPane.showMessageDialog(this, "La nota se actualizo correctamente");
                this.setVisible(false);
                FrmNotasRemision notas = new FrmNotasRemision(user);
                notas.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Ocurrio un error al editar la nota");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un servicio");
        }


    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        // Obtenemos el índice de la fila seleccionada
        int filaSeleccionada = tblServicios.getSelectedRow();

        // Verificamos si hay alguna fila seleccionada
        if (filaSeleccionada != -1) {
            // Mostramos el mensaje de confirmación
            int res = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar el servicio de la lista?");
            if (res == JOptionPane.YES_OPTION) {
                // Eliminamos la fila seleccionada
                DefaultTableModel model = (DefaultTableModel) tblServicios.getModel();
                total = total - Float.parseFloat(tblServicios.getValueAt(filaSeleccionada, 3).toString())
                        + Float.parseFloat(tblServicios.getValueAt(filaSeleccionada, 5).toString());
                txtTotal.setText(String.valueOf(total));
                model.removeRow(filaSeleccionada);
                referencias.remove(filaSeleccionada);
                // Recorremos las filas restantes hacia arriba
                for (int i = filaSeleccionada; i < model.getRowCount(); i++) {
                    // Por ejemplo, si quieres mantener la información en la columna 1:
                    Object valorColumna1 = model.getValueAt(i, 0); // Obtén el valor de la columna 1 antes de eliminar la fila
                    model.setValueAt(valorColumna1, i, 0); // Asigna el valor de la columna 1 en la fila actual
                    Object valorColumna2 = model.getValueAt(i, 1); // Obtén el valor de la columna 1 antes de eliminar la fila
                    model.setValueAt(valorColumna2, i, 1);
                    Object valorColumna3 = model.getValueAt(i, 2); // Obtén el valor de la columna 1 antes de eliminar la fila
                    model.setValueAt(valorColumna3, i, 2);
                    Object valorColumna4 = model.getValueAt(i, 3); // Obtén el valor de la columna 1 antes de eliminar la fila
                    model.setValueAt(valorColumna4, i, 3);
                    Object valorColumna5 = model.getValueAt(i, 2); // Obtén el valor de la columna 1 antes de eliminar la fila
                    model.setValueAt(valorColumna5, i, 4);
                    Object valorColumna6 = model.getValueAt(i, 3); // Obtén el valor de la columna 1 antes de eliminar la fila
                    model.setValueAt(valorColumna6, i, 5);

                }
                this.indice--;
            }
        } else {
            // Si no hay fila seleccionada, mostramos un mensaje de error
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void agregarBotonesServicios(List<Servicio> servicios) {
        for (Servicio servicio : servicios) {
            JButton botonServicio = new JButton(servicio.getDescripcion());
            botonServicio.setSize(100, 50);
            botonServicio.setBackground(new Color(153, 204, 255));
            botonServicio.setForeground(Color.BLACK);

            botonServicio.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String nombreServicio = servicio.getDescripcion();
                    float precio = servicio.getPrecio();

                    DlgCantidad cantidad = new DlgCantidad(FrmEditarNota1.this, true, nombreServicio);
                    cantidad.setVisible(true);
                    int cant = cantidad.getCantidad();
                    if (cant > 0) {
                        tblServicios.setValueAt(nombreServicio, indice, 0);
                        tblServicios.setValueAt(cant, indice, 1);
                        tblServicios.setValueAt(precio, indice, 2);
                        tblServicios.setValueAt(precio * cant, indice, 3);
                        indice++;
                        total = total + precio * cant;
                        txtTotal.setText(String.valueOf(total));
                        NotaServicio notaS = new NotaServicio();
                        notaS.setCant(cant);
                        notaS.setDetalles("");
                        notaS.setPrecio(precio * cant);
                        notaS.setServicio(servicio);
                        notaS.setPerdidas(0);
                        referencias.add(notaS);
                    }
//                    FrmCantidad frmCantidad = new FrmCantidad();
//                    frmCantidad.getLblNombreServicio().setText(nombreServicio);
//                    frmCantidad.setVisible(true);
                }

            });

            pnlServicios.add(botonServicio);
        }

        pnlServicios.revalidate();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRegistrar1;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnRestaurar;
    private com.toedter.calendar.JDateChooser fechaEntrega;
    private javax.swing.JLabel lblAnticipo;
    private javax.swing.JLabel lblCliente1;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEntrega1;
    private javax.swing.JLabel lblServicios1;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPanel pnlServicios;
    private javax.swing.JScrollPane scrlServicios;
    private javax.swing.JTable tblServicios;
    private javax.swing.JTextField txtAnticipo;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
