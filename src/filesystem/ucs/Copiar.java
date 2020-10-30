/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem.ucs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import javax.swing.JOptionPane;

/**
 * Classe que trata do caso de uso "Copiar ficheiro/diretório"
 * @author Nelson
 */
public class Copiar {

    public Copiar(File ficheiroAtual) throws IOException {
        
        if (ficheiroAtual == null) {
            JOptionPane.showMessageDialog(null, "Tem de selecionar um ficheiro para copiar");
            return;
        }
        
        String novoPath = JOptionPane.showInputDialog(null,"Escolha a pasta caminho");
        File copiado = new File(novoPath + ficheiroAtual.getName());
        
        boolean criado = true;

        if (criado) {
            FileChannel fromChannel = null;
            FileChannel toChannel = null;
            try {
                fromChannel = new FileInputStream(ficheiroAtual).getChannel();
                toChannel = new FileOutputStream(copiado).getChannel();

                toChannel.transferFrom(fromChannel, 0, fromChannel.size());

            } finally {
                if (fromChannel != null) {
                    fromChannel.close();
                }
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }
    
}
