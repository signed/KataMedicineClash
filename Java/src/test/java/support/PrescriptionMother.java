package support;

import java.time.LocalDate;

public class PrescriptionMother {

    public static PrescriptionBuilder oneDayPrescription() {
        return anyPrescription().supplyFor(1);
    }

    public static PrescriptionBuilder prescriptionStarting(LocalDate prescriptionStartDay) {
        return anyPrescription().starting(prescriptionStartDay);
    }

    public static PrescriptionBuilder anyPrescription() {
        return new PrescriptionBuilder();
    }

    public static int oneDay() {
        return 1;
    }

    public static int threeDays() {
        return 3;
    }

    public static int toSmallNumberOfDays() {
        return 0;
    }

}
