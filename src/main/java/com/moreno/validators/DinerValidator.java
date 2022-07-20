package com.moreno.validators;

import com.moreno.Notify;
import com.moreno.models.Diner;
import com.moreno.utilities.Utilities;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static com.moreno.validators.ProgramValidator.PROGRAMA_VALIDATOR;

public class DinerValidator {

    public static Set<ConstraintViolation<Diner>> loadViolations(Diner election) {
        Set<ConstraintViolation<Diner>> violations = PROGRAMA_VALIDATOR.validate(election);
        return violations;
    }

    public static void mostrarErrores(Set<ConstraintViolation<Diner>> errors){
        Object[] errores=errors.toArray();
        ConstraintViolation<Diner> error1= (ConstraintViolation<Diner>) errores[0];
        String error = "Verfique el campo: "+error1.getMessage();
        Notify.sendNotify(Utilities.getJFrame(), Notify.Type.WARNING, Notify.Location.BOTTOM_RIGHT,"ERROR",error);

    }
}
