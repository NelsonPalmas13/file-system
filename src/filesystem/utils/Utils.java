/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem.utils;

import filesystem.ModeloTabela;
import java.io.File;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Nelson
 */
public class Utils {
    
    public static ModeloTabela modeloTabelaAux2;

    /**
     * Metódo que preeche a árvore
     * @param raiz
     * @param jTree1
     * @param fsv 
     */
    public static void preencherArvore(DefaultMutableTreeNode raiz, JTree jTree1, FileSystemView fsv) {

        File[] roots = fsv.getRoots();
        
        for (File fileRoot : roots) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileRoot);
            raiz.add(node);

            File[] files = fsv.getFiles(fileRoot, true);
            for (File file : files) {
                if (file.isDirectory()) {
                    node.add(new DefaultMutableTreeNode(file));
                }
            }
        }
    }

    /**
     * Método responsável por mandar a informação para a árvore e para a tabela
     * @param no
     * @param tree
     * @param fsv
     * @param table
     * @param lsl 
     */
    public static void mostrarInfo(DefaultMutableTreeNode no, JTree tree, FileSystemView fsv,
            JTable table, ListSelectionListener lsl) {
        
        tree.setEnabled(false);
        
        /*
        SwingWorker é usado para tratar as várias threads a correr no programa.
        Em background, são encontrados os ficheiros (nós) e é atualizada a informação
        na tabela.
        O método publish manda os "data chunks" para  o método process, que fica
        encarregue de associar os ficheiros a cada nó da árvore.
        Quando o processamento estiver concluido, a árvore estará ativa nvoamente.
        */
        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
            @Override
            public Void doInBackground() {
                File ficheiro = (File) no.getUserObject();
                if (ficheiro.isDirectory()) {
                    File[] ficheiros = fsv.getFiles(ficheiro, true);
                    if (no.isLeaf()) {
                        for (File child : ficheiros) {
                            if (child.isDirectory()) {
                                publish(child);
                            }
                        }
                    }
                    infoTabela(ficheiros, table, lsl);
                }
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    no.add(new DefaultMutableTreeNode(child));
                }
            }

            @Override
            protected void done() {
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }

    /**
     * Método que atualiza a informação da tabela
     * @param ficheiros
     * @param fileTableModel
     * @param table
     * @param lsl 
     */
    private static void infoTabela(File[] ficheiros, JTable table, ListSelectionListener lsl) {

        /*
        Como o Swing não é uma estrutura de dados "thread-safe", o método invokeLater 
        é usado para prevenir o acesso concorrente.
        */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                if (modeloTabelaAux2 == null) {
                    modeloTabelaAux2 = new ModeloTabela();
                    table.setModel(modeloTabelaAux2);
                }
                
                table.getSelectionModel().removeListSelectionListener(lsl);
                modeloTabelaAux2.setFicheiros(ficheiros);
                table.getSelectionModel().addListSelectionListener(lsl);
            }
        });
    }
    
    /**
     * Devolve o path do ficheiro selecionado na árvore
     * @param selecionado
     * @param tree
     * @return treepath
     */
    public static TreePath encontraPath(File selecionado, JTree tree) {
        for (int i=0; i<tree.getRowCount(); i++) {
            TreePath treePath = tree.getPathForRow(i);
            Object objecto = treePath.getLastPathComponent();
            DefaultMutableTreeNode no = (DefaultMutableTreeNode)objecto;
            
            File aux = (File)no.getUserObject();
            System.out.println("" + i + " " + aux);
            if (aux==selecionado) {
                
                return treePath;
            }
        }
        return null;
    }

}

