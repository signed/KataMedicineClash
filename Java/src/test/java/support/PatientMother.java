package support;

import support.PatientBuilder;

public class PatientMother {

    public static PatientBuilder patientWithoutSubscriptions() {
        return new PatientBuilder();
    }
}
