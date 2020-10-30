/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem.ucs;

import filesystem.ModeloTabela;
import filesystem.utils.Utils;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Nelson
 */
public class Renomear {

    String novoNome = JOptionPane.showInputDialog(null, "New Name");

    public Renomear(File ficheiroAtual, JTree tree, DefaultTreeModel modeloTree,
            FileSystemView fsv, ModeloTabela modeloTabela, JTable table, ListSelectionListener lsl) {

        if (ficheiroAtual == null) {
            JOptionPane.showMessageDialog(null, "Tem de selecionar um ficheiro para o renomear");
            return;
        }

        try {
            boolean directory = ficheiroAtual.isDirectory();

            File novoFicheiro = new File(ficheiroAtual.getParent(), novoNome);
            Files.move(ficheiroAtual.toPath(), novoFicheiro.toPath());

            TreePath pathPai = Utils.encontraPath(ficheiroAtual.getParentFile(), tree);
            DefaultMutableTreeNode noPai = (DefaultMutableTreeNode) pathPai.getLastPathComponent();

            boolean renamed = ficheiroAtual.renameTo(novoFicheiro);

            if (renamed) {
                if (directory) {
                    TreePath path = Utils.encontraPath(ficheiroAtual, tree);
                    DefaultMutableTreeNode no = (DefaultMutableTreeNode) path.getLastPathComponent();
                    //Remove-se o nó correspondente ao ficheiro/diretorio
                    modeloTree.removeNodeFromParent(no);

                    DefaultMutableTreeNode novoNo = new DefaultMutableTreeNode(novoFicheiro);
                    //Adiciona-se o novo nó correspondente ao ficheiro/diretorio novo
                    modeloTree.insertNodeInto(novoNo, noPai, noPai.getChildCount());
                }

                modeloTabela = Utils.modeloTabelaAux2;
                //Atualiza-se a informação para a UI
                Utils.mostrarInfo(noPai, tree, fsv, table, lsl);
            } else {
                JOptionPane.showMessageDialog(null, "Renomear Falhou");
            }
        } catch (Throwable t) {
            JOptionPane.showMessageDialog(null, "ERRO EM RENOMEAR");
        }
    }

}
