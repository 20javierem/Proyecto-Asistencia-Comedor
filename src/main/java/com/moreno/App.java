package com.moreno;

import com.moreno.utilities.Moreno;
import com.moreno.utilities.Propiedades;
import com.moreno.utilities.Utilities;
import com.moreno.views.VPrincipal;
import com.moreno.common.Common;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Moreno.initialize();
        Common.initFireBase();
        Propiedades propiedades=new Propiedades();
        Utilities.setTema(propiedades.getTema());
        VPrincipal vPrincipal=new VPrincipal(propiedades);
        vPrincipal.setVisible(true);
    }
}
