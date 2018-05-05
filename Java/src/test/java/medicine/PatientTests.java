package medicine;

import org.junit.jupiter.api.Test;
import support.PatientBuilder;
import support.PrescriptionBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static support.MedicineMother.Name.Medicine_A;
import static support.MedicineMother.Name.Medicine_B;
import static support.MedicineMother.Name.anyMedicineNames;
import static support.PatientMother.anyDateBeforeTheInspectionPeriod;
import static support.PatientMother.anyDateWithinTheInspectionPeriod;
import static support.PatientMother.anyOtherDateWithinTheInspectionPeriod;
import static support.PatientMother.defaultInspectionDaysInThePast;
import static support.PatientMother.patientWithoutPrescriptions;
import static support.PrescriptionMother.oneDayPrescription;

class PatientTests {

    private final int daysBack = defaultInspectionDaysInThePast();
    private PatientBuilder patient = patientWithoutPrescriptions();

    @Test
    void patient_without_prescription_has_no_clashes() {
        patient = patientWithoutPrescriptions();

        assertThat(queryPrescriptionClashesOf(anyMedicineNames())).isEmpty();
    }

    @Test
    void querying_for_a_clash_with_only_one_medicine_name_can_not_cause_clashes_even_if_patient_has_a_prescription() {
        PrescriptionBuilder oneDayPrescription = oneDayPrescription().starting(anyDateWithinTheInspectionPeriod());

        patient.withPrescriptionFor(Medicine_A, oneDayPrescription);

        assertThat(queryPrescriptionClashesOf(Medicine_A)).isEmpty();
    }

    @Test
    void patient_with_two_clashing_prescriptions() {
        LocalDate dateOfPrescription = anyDateWithinTheInspectionPeriod();
        thereIsAMedicineClashAt(dateOfPrescription);

        assertThat(queryPrescriptionClashesOf(Medicine_A, Medicine_B)).containsOnly(dateOfPrescription);
    }

    @Test
    void patient_taking_two_clashing_medicines_but_on_different_days_is_not_a_clash() {
        LocalDate dateOfPrescriptionOne = anyDateWithinTheInspectionPeriod();
        PrescriptionBuilder prescriptionOne = oneDayPrescription().starting(dateOfPrescriptionOne);

        LocalDate dateOfPrescriptionTwo = anyOtherDateWithinTheInspectionPeriod(dateOfPrescriptionOne);
        PrescriptionBuilder prescriptionTwo = oneDayPrescription().starting(dateOfPrescriptionTwo);

        patient.withPrescriptionFor(Medicine_A, prescriptionOne);
        patient.withPrescriptionFor(Medicine_B, prescriptionTwo);

        assertThat(queryPrescriptionClashesOf(Medicine_A, Medicine_B))
                .describedAs("prescriptions are on different days and should not clash").isEmpty();
    }

    @Test
    void do_not_report_clashes_that_are_before_the_inspection_period() {
        thereIsAMedicineClashAt(anyDateBeforeTheInspectionPeriod());

        assertThat(queryPrescriptionClashesOf(Medicine_A, Medicine_B))
                .describedAs("should not be reported as a clash because it is before the inspection period").isEmpty();
    }

    private void thereIsAMedicineClashAt(LocalDate clashDay) {
        PrescriptionBuilder oneDayPrescription = oneDayPrescription().starting(clashDay);
        patient.withPrescriptionFor(Medicine_A, oneDayPrescription);
        patient.withPrescriptionFor(Medicine_B, oneDayPrescription);
    }

    private Collection<LocalDate> queryPrescriptionClashesOf(String ... medicineNames) {
        return queryPrescriptionClashesOf(Arrays.asList(medicineNames));
    }

    private Collection<LocalDate> queryPrescriptionClashesOf(Collection<String> medicineNames) {
        return patient.build().clash(medicineNames, daysBack);
    }

}