package support;

public class PatientMother {

    public static PatientBuilder patientWithoutPrescriptions() {
        return new PatientBuilder();
    }

    public static int defaultInspectionDaysInThePast() {
        return 90;
    }
}
