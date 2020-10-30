/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem;

import java.io.File;
import java.util.Date;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

/**
 * Esta classe é usada para completar os campos da tabela (tamanho, ultima modificacao,
 * nome, permissões e o path do ficheiro) 
 * @author Nelson
 */
public class ModeloTabela extends AbstractTableModel {

    private File[] ficheiros;
    private FileSystemView fsv = FileSystemView.getFileSystemView();
    private String[] colunas = {
        "File",
        "Size",
        "Path/name",
        "Last Modified",
        "R",
        "D",
        "W"
    };

    public ModeloTabela() {
        this(new File[0]);
    }

    public ModeloTabela(File[] fich) {
        this.ficheiros = fich;
    }

    public Object getValueAt(int row, int column) {
        File file = ficheiros[row];
        switch (column) {
            case 0:
                return fsv.getSystemDisplayName(file);
            case 1:
                return file.length();
            case 2:
                return file.getPath();
            case 3:
                return file.lastModified();
            case 4:
                return file.canRead();
            case 5:
                return file.canExecute();
            case 6:
                return file.canWrite();
            default:
                System.err.println("Erro na tabela");
        }
        return "";
    }

    public int getColumnCount() {
        return colunas.length;
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 1:
                return Long.class;
            case 3:
                return Date.class;
            case 5:
            case 6:
            case 7:
            case 8:
        }
        return String.class;
    }

    public String getColumnName(int column) {
        return colunas[column];
    }

    public int getRowCount() {
        return ficheiros.length;
    }

    public File getFicheiro(int row) {
        return ficheiros[row];
    }

    public void setFicheiros(File[] ficheiros) {
        this.ficheiros = ficheiros;
        fireTableDataChanged();
    }
}
