/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem.ucs;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * Classe que trata do caso de uso "Abrir ficheiro/diretório"
 * @author Nelson
 */
public class Abrir {

    public Abrir(File ficheiroAtual, Desktop desktop) throws IOException {
        desktop.open(ficheiroAtual);
    }

}
