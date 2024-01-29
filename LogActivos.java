package com.mycompany.sistemaactivos;

import interfaz.Inicio;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class LogActivos {

    private int numeroControl;
    private int serie;
    private String descripcion;
    private String categoria;
    private String ubicacion;
    private String resguardatario;
    private int añoAdquisicion;
    private double costoAdquisicion;
    private String proveedor;
    private String estadoActal;
    private double porcentajeDepreciacion;
    private double depreciacionAnual;
    private double depreciacionMensual;

    public int getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(int numeroControl) {
        this.numeroControl = numeroControl;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getResguardatario() {
        return resguardatario;
    }

    public void setResguardatario(String resguardatario) {
        this.resguardatario = resguardatario;
    }

    public int getAñoAdquisicion() {
        return añoAdquisicion;
    }

    public void setAñoAdquisicion(int añoAdquisicion) {
        this.añoAdquisicion = añoAdquisicion;
    }

    public double getCostoAdquisicion() {
        return costoAdquisicion;
    }

    public void setCostoAdquisicion(double costoAdquisicion) {
        this.costoAdquisicion = costoAdquisicion;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getEstadoActal() {
        return estadoActal;
    }

    public void setEstadoActal(String estadoActal) {
        this.estadoActal = estadoActal;
    }

    public double getPorcentajeDepreciacion() {
        return porcentajeDepreciacion;
    }

    public void setPorcentajeDepreciacion(double porcentajeDepreciacion) {
        this.porcentajeDepreciacion = porcentajeDepreciacion;
    }

    public double getDepreciacionAnual() {
        return depreciacionAnual;
    }

    public void setDepreciacionAnual(double depreciacionAnual) {
        this.depreciacionAnual = depreciacionAnual;
    }

    public double getDepreciacionMensual() {
        return depreciacionMensual;
    }

    public void setDepreciacionMensual(double depreciacionMensual) {
        this.depreciacionMensual = depreciacionMensual;
    }

    public double depreciacionAnual() {
        double depreciacionAnual = (getCostoAdquisicion() * getPorcentajeDepreciacion()) / 100;
        return depreciacionAnual;
    }

    public double depreciacionMensual() {
        double depreciacionMensual = depreciacionAnual() / 12;
        return depreciacionMensual;
    }

    //---------------------------------------------ACTIVOS------------------------------------------------
    //metodo en donde se crea el activo y lo va registrando en la bd
    public void agragarActivo(JTextField paramNumeroControl, JTextField paramSerie,
            JTextField paramDescripcion, JTextField paramUbicacion, JTextField paramResguardatario,
            JTextField paramAñoAdquisicion, JTextField paramCostoAdquisicion, JTextField paramProveedor,
            JTextField paramEstadoActual, JTextField paramPorcentajeDepreciacion) {

        setNumeroControl(Integer.parseInt(paramNumeroControl.getText()));
        setSerie(Integer.parseInt(paramSerie.getText()));
        setDescripcion(paramDescripcion.getText());
        Inicio ini = new Inicio();
        setCategoria(ini.cbxCategoria.getSelectedItem().toString());
        setUbicacion(paramUbicacion.getText());
        setResguardatario(paramResguardatario.getText());
        setAñoAdquisicion(Integer.parseInt(paramAñoAdquisicion.getText()));
        setCostoAdquisicion(Double.parseDouble(paramCostoAdquisicion.getText()));
        setProveedor(paramProveedor.getText());
        setEstadoActal(paramEstadoActual.getText());
        setPorcentajeDepreciacion(Double.parseDouble(paramPorcentajeDepreciacion.getText()));
        setDepreciacionAnual(depreciacionAnual());
        setDepreciacionMensual(depreciacionMensual());

        CConexion objetoConexion = new CConexion();

        String consulta = "INSERT INTO registro(\n"
                + "    numeroControl,\n"
                + "    serie,\n"
                + "    descripcion,\n"
                + "    categoria,\n"
                + "    ubicacion,\n"
                + "    resguardatario,\n"
                + "    añoAdquisicion,\n"
                + "    costoAdquisicion,\n"
                + "    proveedor,\n"
                + "    estadoActual,\n"
                + "    porcentajeDepreciacion,\n"
                + "    depreciacionAnual,\n"
                + "    depreciacionMensual\n"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            CallableStatement ps = objetoConexion.estableceConexion().prepareCall(consulta);
            ps.setInt(1, getNumeroControl());
            ps.setInt(2, getSerie());
            ps.setString(3, getDescripcion());
            ps.setString(4, getCategoria());
            ps.setString(5, getUbicacion());
            ps.setString(6, getResguardatario());
            ps.setInt(7, getAñoAdquisicion());
            ps.setDouble(8, getCostoAdquisicion());
            ps.setString(9, getProveedor());
            ps.setString(10, getEstadoActal());
            ps.setDouble(11, getPorcentajeDepreciacion());
            ps.setDouble(12, getDepreciacionAnual());
            ps.setDouble(13, getDepreciacionMensual());

            ps.execute();

            JOptionPane.showMessageDialog(null, "Se insertó correctamente el activo");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se insertó correctamente el activo, error: " + e.toString());
        }
    }

    //metodo en donde se muestra todos los datos de una activo registrado
    public void mostrarActivo(JTable paramTablaTotalActivos) {
        CConexion objetoConexion = new CConexion();

        DefaultTableModel modelo = new DefaultTableModel();

        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        paramTablaTotalActivos.setRowSorter(OrdenarTabla);

        String sql = "";

        modelo.addColumn("Numero de Control");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Serie");
        modelo.addColumn("Categoria");
        modelo.addColumn("Ubicacion");
        modelo.addColumn("Proveedor");
        modelo.addColumn("Año de Adquisicion");
        modelo.addColumn("Estado Actual");
        modelo.addColumn("Resguardatario");
        modelo.addColumn("Costo de Adquisicion");
        modelo.addColumn("Porcentaje de Depreciacion");
        modelo.addColumn("Depreciacion Anual");
        modelo.addColumn("Depreciacion Mensual");
        paramTablaTotalActivos.setModel(modelo);

        sql = "select * from registro;";

        String[] datos = new String[13];
        Statement st;

        try {
            st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                datos[9] = rs.getString(10);
                datos[10] = rs.getString(11);
                datos[11] = rs.getString(12);
                datos[12] = rs.getString(13);

                modelo.addRow(datos);
            }

            paramTablaTotalActivos.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo Mostrar los datos, error:" + e.toString());
        }
    }
    //---------------------------------------Resguardatario------------------------------------------------

    private int noControl;
    private String nombreResg;
    private String area;
    private String departamento;
    private String puesto;
    private int activosRegistrados;

    public int getNoControl() {
        return noControl;
    }

    public void setNoControl(int noControl) {
        this.noControl = noControl;
    }

    public String getNombreResg() {
        return nombreResg;
    }

    public void setNombreResg(String nombreResg) {
        this.nombreResg = nombreResg;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getActivosRegistrados() {
        return activosRegistrados;
    }

    public void setActivosRegistrados(int activosRegistrados) {
        this.activosRegistrados = activosRegistrados;
    }

    public int obtenerTotalActivosPorPersona(String nombreResg) throws SQLException {
        CConexion conexion = new CConexion();
        int totalActivos = 0;
        String consulta = "SELECT SUM(ActivosResg) FROM Resguardatario WHERE nombreResg = ?";

        try (Connection conn = conexion.estableceConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {

            ps.setString(1, nombreResg);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalActivos = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalActivos;
    }

    public void InsertarResguardatario(JTextField paramNoControl, JTextField paramNombreResg, JTextField paramArea, JTextField paramDepartamento, JTextField paramPuesto) throws SQLException {
        setNoControl(Integer.parseInt(paramNoControl.getText()));
        setNombreResg(paramNombreResg.getText());
        setArea(paramArea.getText());
        setDepartamento(paramDepartamento.getText());
        setPuesto(paramPuesto.getText());

        setActivosRegistrados(obtenerTotalActivosPorPersona(nombreResg));

        CConexion objetoConexion = new CConexion();

        String consulta = "INSERT INTO Resguardatario (nControl, nombre, area, departamento, puesto, ActivosResg)\n"
                + "VALUES (?, ?, ?, ?, ?, ? );";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, getNoControl());
            cs.setString(2, getNombreResg());
            cs.setString(3, getArea());
            cs.setString(4, getDepartamento());
            cs.setString(5, getPuesto());
            cs.setInt(6, getActivosRegistrados());

            cs.execute();

            JOptionPane.showMessageDialog(null, "Se inserto correctamente el alumno");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se inserto correctamente el alumno, erro:" + e.toString());
        }

    }

    public void mostrarResguardatario(JTable paramTablaResguardatarios) {
        CConexion objetoConexion = new CConexion();

        DefaultTableModel modelo = new DefaultTableModel();

        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        paramTablaResguardatarios.setRowSorter(OrdenarTabla);

        String sql = "";

        modelo.addColumn("No.control");
        modelo.addColumn("Nombre");
        modelo.addColumn("Area");
        modelo.addColumn("Departamento");
        modelo.addColumn("puesto");
        modelo.addColumn("Activos Resguardados");

        paramTablaResguardatarios.setModel(modelo);

        sql = "select*from Resguardatario;";

        String[] datos = new String[6];
        Statement st;

        try {
            st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);

                modelo.addRow(datos);
            }

            paramTablaResguardatarios.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo ostrar los datos, error:" + e.toString());
        }
    }

    public void SeleccionarResguardatarios(JTable paramTablaResguardatarios, JTextField paramNoControl, JTextField paramNombreResg, JTextField paramArea, JTextField paramDepartamento, JTextField paramPuesto) {
        try {
            int fila = paramTablaResguardatarios.getSelectedRow();
            if (fila >= 0) {
                paramNoControl.setText((String) paramTablaResguardatarios.getValueAt(fila, 0).toString());
                paramNombreResg.setText((String) paramTablaResguardatarios.getValueAt(fila, 1).toString());
                paramArea.setText((String) paramTablaResguardatarios.getValueAt(fila, 2).toString());
                paramDepartamento.setText((String) paramTablaResguardatarios.getValueAt(fila, 3).toString());
                paramPuesto.setText((String) paramTablaResguardatarios.getValueAt(fila, 4).toString());

            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de seleccion,Error: " + e.toString());
        }
    }

    public void modificarResguardatario(JTextField paramNoControl, JTextField paramNombreResg, JTextField paramArea, JTextField paramDepartamento, JTextField paramPuesto) {
        try {
            setNoControl(Integer.parseInt(paramNoControl.getText()));
            setNombreResg(paramNombreResg.getText());
            setArea(paramArea.getText());
            setDepartamento(paramDepartamento.getText());
            setPuesto(paramPuesto.getText());

            CConexion objetoConexion = new CConexion();

            String consulta = "UPDATE Resguardatario SET nControl=?, area=?, departamento=?, puesto=?, ActivosResg=? WHERE nombre=?";

            try (CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta)) {
                cs.setInt(1, getNoControl());
                cs.setString(2, getArea());
                cs.setString(3, getDepartamento());
                cs.setString(4, getPuesto());
                cs.setInt(5, getActivosRegistrados());
                cs.setString(6, getNombreResg());

                cs.executeUpdate();

                JOptionPane.showMessageDialog(null, "Modificación exitosa");
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "No se modificó, error: " + e.toString());
        }
    }

    public void EliminarResguardatario( JTextField paramNombreResg) {
        setNombreResg(paramNombreResg.getText());
        CConexion objetoConexion = new CConexion();
        String consulta = "delete from Resguardatario where nombre=?;";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);

            cs.setString(1, getNombreResg());
            cs.execute();

            JOptionPane.showMessageDialog(null, "se elimino correctamente");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se elimino correctamente, error:" + e.toString());

        }

    }

}
