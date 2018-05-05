package medicine;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import support.*;

import java.time.LocalDate;
import java.util.Collection;

import static java.util.Arrays.asList;
import static support.PatientMother.patientWithoutPrescriptions;
import static org.assertj.core.api.Assertions.assertThat;
import static support.PrescriptionMother.oneDayPrescription;

class PatientTests {

    // TODO patient with single prescription

    private PatientBuilder patient = patientWithoutPrescriptions();
    private int daysBack = PatientMother.defaultInspectionDaysInThePast();

    @Test
    void patient_without_prescription_has_no_clashes() {
        patient = patientWithoutPrescriptions();

        assertThat(clashFor(MedicineMother.anyMedicineNames())).isEmpty();
    }

    @Test
    void patient_with_with_two_clashing_prescriptions() {
        LocalDate dateOfPrescription = PrescriptionMother.anyDateWithinTheInspectionPeriod();
        PrescriptionBuilder oneDayPrescription = oneDayPrescription().starting(dateOfPrescription);

        patient.withPrescriptionFor("one", oneDayPrescription);
        patient.withPrescriptionFor("two", oneDayPrescription);

        assertThat(clashFor(asList("one", "two"))).containsOnly(dateOfPrescription);
    }

    @Test
    @Disabled("Too big a step right now")
    void patient_taking_two_clashing_medicines_but_on_different_days_is_not_a_clash() {
        LocalDate dateOfPrescriptionOne = PrescriptionMother.anyDateWithinTheInspectionPeriod();
        PrescriptionBuilder prescriptionOne = oneDayPrescription().starting(dateOfPrescriptionOne);
        LocalDate dateOfPrescriptionTwo = PrescriptionMother.anyOtherDateWithinTheInspectionPeriod(dateOfPrescriptionOne);
        PrescriptionBuilder prescriptionTwo = oneDayPrescription().starting(dateOfPrescriptionTwo);

        patient.withPrescriptionFor("one", prescriptionOne);
        patient.withPrescriptionFor("two", prescriptionTwo);

        assertThat(clashFor(asList("one", "two"))).describedAs("prescriptions are on different days and should not clash").isEmpty();
    }

    private Collection<LocalDate> clashFor(Collection<String> medicineNames) {
        return patient.build().clash(medicineNames, daysBack);
    }

}