/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem;

import java.awt.Component;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Nelson
 * Esta classe é usada para tornar a árvore mais apelativa, com icons,
 * nome do ficheiro em vez do path e cores de seleção
 */
public class ModeloArvore extends DefaultTreeCellRenderer{
    
    private final FileSystemView fsv;
    private final JLabel label;

    public ModeloArvore() {
        label = new JLabel();
        label.setOpaque(true);
        fsv = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object valor,
        boolean selecionado,
        boolean expandido,
        boolean folha,
        int linha,
        boolean clicado) {

        DefaultMutableTreeNode no = (DefaultMutableTreeNode)valor;
        File ficheiro = (File)no.getUserObject();
        label.setIcon(fsv.getSystemIcon(ficheiro));
        label.setText(fsv.getSystemDisplayName(ficheiro));
        label.setToolTipText(ficheiro.getPath());

        if (selecionado) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
    
}
