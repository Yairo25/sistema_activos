package com.mycompany.sistemaactivos;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class CConexion {

    Connection conectar = null;
    String usuario = "root";
    String contraseña = "contraseña";
    String bd = "bdActivos";
    String ip = "localhost";
    String puerto = "3306";

    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    public Connection estableceConexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = (Connection) DriverManager.getConnection(cadena, usuario, contraseña);
            //JOptionPane.showMessageDialog(null, "Coneccion realizada con exito");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar al abase de datos. error:" + e.toString());
        }
        return conectar;
    }
}
