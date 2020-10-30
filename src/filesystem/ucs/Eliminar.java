/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem.ucs;

import filesystem.ModeloTabela;
import filesystem.utils.Utils;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Classe que trata do caso de uso "Eliminar ficheiro/diretório"
 * @author Nelson
 */
public class Eliminar {

    public Eliminar(File ficheiroAtual, JTree tree, DefaultTreeModel modeloTree,
            FileSystemView fsv, ModeloTabela modeloTabela, JTable table, ListSelectionListener lsl) {

        if (ficheiroAtual == null) {
            JOptionPane.showMessageDialog(null, "Tem de selecionar um ficheiro para eliminar");
            return;
        }

        int result = JOptionPane.showConfirmDialog(null,
                "Deseja eliminar o ficheiro?",
                "Eliminar Ficheiro",
                JOptionPane.ERROR_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
           // try {
                TreePath pathPai = Utils.encontraPath(ficheiroAtual.getParentFile(), tree);
                DefaultMutableTreeNode noPai = (DefaultMutableTreeNode) pathPai.getLastPathComponent();

                boolean diretorio = ficheiroAtual.isDirectory();
                boolean eliminado = ficheiroAtual.delete();

                if (eliminado) {
                    if (diretorio) {
                        TreePath path = Utils.encontraPath(ficheiroAtual, tree);
                        DefaultMutableTreeNode no = (DefaultMutableTreeNode) path.getLastPathComponent();
                        //Remove-se o nó correspondente ao ficheiro/diretorio
                        modeloTree.removeNodeFromParent(no);
                    }
                    modeloTabela = Utils.modeloTabelaAux2;
                    //Atualiza-se a informação para a UI
                    Utils.mostrarInfo(noPai, tree, fsv, table, lsl);
                } else {
                    JOptionPane.showMessageDialog(null, "Eliminar Falhou");
                }
            /*} catch (Throwable t) {
                JOptionPane.showMessageDialog(null, "ERRO EM ELIMINAR!");
            }*/
        }
    }

}
