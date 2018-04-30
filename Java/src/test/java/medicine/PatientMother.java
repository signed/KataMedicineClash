package medicine;

public class PatientMother {

    public static PatientBuilder patientWithoutSubscriptions() {
        return new PatientBuilder();
    }
}
