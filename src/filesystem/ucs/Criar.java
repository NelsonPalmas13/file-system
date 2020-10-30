/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem.ucs;

import filesystem.ModeloTabela;
import filesystem.utils.Utils;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Classe que trata do caso de uso "Criar ficheiro/diretório"
 * @author Nelson
 */
public class Criar {

    public Criar(File ficheiroAtual, JTree tree, DefaultTreeModel modeloTree,
            FileSystemView fsv,
            ModeloTabela modeloTabela, JTable table, ListSelectionListener lsl) {
        
        if (ficheiroAtual==null) {
            JOptionPane.showMessageDialog(null, "Tem de selecionar um ficheiro pai para criar");
            return;
        }

        String [] opcoes = {"File","Directory"};
        JComboBox optionList = new JComboBox(opcoes);
        optionList.setSelectedIndex(0);
        int resultado = JOptionPane.showConfirmDialog(null, optionList, "Selecione o tipo de ficheiro",
                     JOptionPane.OK_CANCEL_OPTION);
        
        if(resultado == 0){
            String respo = JOptionPane.showInputDialog(null, "Escolha o nome");
            try {
                boolean criado;
                
                File ficheiroPai = ficheiroAtual;
                if (!ficheiroPai.isDirectory()) {
                    ficheiroPai = ficheiroPai.getParentFile();
                }
                
                File novoFich = new File( ficheiroPai, respo );
                if (optionList.getSelectedIndex() == 0) {
                    criado = novoFich.createNewFile();
                } else {
                    criado = novoFich.mkdir();
                }
                
                if (criado) {
                    TreePath pathPai = Utils.encontraPath(ficheiroPai, tree);
                    DefaultMutableTreeNode noPai = (DefaultMutableTreeNode)pathPai.getLastPathComponent();

                    if (novoFich.isDirectory()) {
                        DefaultMutableTreeNode novoNo = new DefaultMutableTreeNode(novoFich);
                        //Adiciona-se o nó correspondente ao ficheiro/diretorio
                        modeloTree.insertNodeInto(novoNo, noPai, noPai.getChildCount());
                    }
                    modeloTabela = Utils.modeloTabelaAux2;
                    //Atualiza-se a informação para a UI
                    Utils.mostrarInfo(noPai,tree, fsv, table, lsl);
                } else {
                    JOptionPane.showMessageDialog(null, "Criar Falhou");
                }
            } catch (Throwable t) {
                JOptionPane.showMessageDialog(null, "ERRO EM CRIAR");
            }
        }
    }
    
}
