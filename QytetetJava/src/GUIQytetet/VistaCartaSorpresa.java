/*
 * Proyecto: Qytetet - PDOO
 * Alumno: David Infante Casas
 * ETSIIT, UGR, Granada, España
 */
package GUIQytetet;

/**
 *
 * @author David Infante Casas
 */
public class VistaCartaSorpresa extends javax.swing.JPanel {

    /**
     * Creates new form VistaCartaSorpresa
     */
    public VistaCartaSorpresa() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtTextoSorpresa = new javax.swing.JTextArea();

        jtTextoSorpresa.setEditable(false);
        jtTextoSorpresa.setColumns(20);
        jtTextoSorpresa.setRows(5);
        jScrollPane1.setViewportView(jtTextoSorpresa);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jtTextoSorpresa;
    // End of variables declaration//GEN-END:variables

    public void actualizar(String descripcionCartaSorpresa) {
        this.jtTextoSorpresa.setText(descripcionCartaSorpresa);
        this.repaint();
        this.revalidate();
    }
    
}
