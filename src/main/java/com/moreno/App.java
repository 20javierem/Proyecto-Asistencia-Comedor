package com.moreno;

import com.moreno.utilities.Propiedades;
import com.moreno.utilities.Utilities;
import com.moreno.views.VPrincipal;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Propiedades propiedades=new Propiedades();
        Utilities.setTema(propiedades.getTema());
        VPrincipal vPrincipal=new VPrincipal(propiedades);
        vPrincipal.setVisible(true);
    }
}
