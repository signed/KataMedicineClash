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

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalDate anyDateWithinTheInspectionPeriod() {
        return today().minusDays(1);
    }

    public static LocalDate anyOtherDateWithinTheInspectionPeriod(LocalDate otherPrescriptionStartDate) {
        return otherPrescriptionStartDate.minusDays(1);
    }

    public static LocalDate anyPrescriptionStartDate() {
        return LocalDate.now().plusDays(3472);
    }

    public static LocalDate anyOtherPrescriptionStartDate(LocalDate otherPrescriptionStartDate) {
        return otherPrescriptionStartDate.plusDays(43);
    }
}
