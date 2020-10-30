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
 * Classe que trata do caso de uso "Editar ficheiro/diretório"
 * @author Nelson
 */
public class Editar {

    public Editar(File ficheiroAtual, Desktop desktop) throws IOException {
        desktop.edit(ficheiroAtual);
    }
}
