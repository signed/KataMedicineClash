package support;

import medicine.Patient;

import java.time.LocalDate;

public class PatientMother {

    public static PatientBuilder patientWithoutPrescriptions() {
        return new PatientBuilder();
    }

    public static int defaultInspectionDaysInThePast() {
        return Patient.DefaultNumberOfDaysBack;
    }

    public static LocalDate anyDateWithinTheInspectionPeriod() {
        return today().minusDays(20);
    }

    public static LocalDate anyOtherDateWithinTheInspectionPeriod(LocalDate otherPrescriptionStartDate) {
        return otherPrescriptionStartDate.minusDays(1);
    }

    public static LocalDate anyDateBeforeTheInspectionPeriod() {
        return today().minusDays(defaultInspectionDaysInThePast()+43);
    }

    private static LocalDate today() {
        return LocalDate.now();
    }
}
