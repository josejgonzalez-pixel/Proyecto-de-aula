/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author Camil
 */
public class NLPService {
    public String limpiarTexto(String texto){

        if(texto == null){
            return "";
        }

        return texto
                .toLowerCase()
                .trim();
    }
}
