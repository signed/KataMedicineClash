package medicine;

import org.junit.jupiter.api.Test;
import support.MedicineMother;
import support.PatientBuilder;
import support.PatientMother;
import support.PrescriptionBuilder;

import java.time.LocalDate;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static support.PatientMother.anyDateBeforeTheInspectionPeriod;
import static support.PatientMother.anyDateWithinTheInspectionPeriod;
import static support.PatientMother.defaultInspectionDaysInThePast;
import static support.PatientMother.patientWithoutPrescriptions;
import static support.PrescriptionMother.oneDayPrescription;

class PatientTests {

    private final int daysBack = defaultInspectionDaysInThePast();
    private PatientBuilder patient = patientWithoutPrescriptions();

    @Test
    void patient_without_prescription_has_no_clashes() {
        patient = patientWithoutPrescriptions();

        assertThat(clashFor(MedicineMother.anyMedicineNames())).isEmpty();
    }

    @Test
    void querying_for_a_clash_with_only_one_medic_name_results_can_not_cause_clashes() {
        PrescriptionBuilder oneDayPrescription = oneDayPrescription().starting(anyDateWithinTheInspectionPeriod());

        patient.withPrescriptionFor("one", oneDayPrescription);

        assertThat(clashFor(asList("one"))).isEmpty();
    }

    @Test
    void patient_with_two_clashing_prescriptions() {
        LocalDate dateOfPrescription = anyDateWithinTheInspectionPeriod();
        PrescriptionBuilder oneDayPrescription = oneDayPrescription().starting(dateOfPrescription);

        patient.withPrescriptionFor("one", oneDayPrescription);
        patient.withPrescriptionFor("two", oneDayPrescription);

        assertThat(clashFor(asList("one", "two"))).containsOnly(dateOfPrescription);
    }

    @Test
    void patient_taking_two_clashing_medicines_but_on_different_days_is_not_a_clash() {
        LocalDate dateOfPrescriptionOne = anyDateWithinTheInspectionPeriod();
        PrescriptionBuilder prescriptionOne = oneDayPrescription().starting(dateOfPrescriptionOne);
        LocalDate dateOfPrescriptionTwo = PatientMother.anyOtherDateWithinTheInspectionPeriod(dateOfPrescriptionOne);
        PrescriptionBuilder prescriptionTwo = oneDayPrescription().starting(dateOfPrescriptionTwo);

        patient.withPrescriptionFor("one", prescriptionOne);
        patient.withPrescriptionFor("two", prescriptionTwo);

        assertThat(clashFor(asList("one", "two"))).describedAs("prescriptions are on different days and should not clash").isEmpty();
    }

    @Test
    void do_not_report_clashes_that_are_before_the_inspection_period() {
        PrescriptionBuilder oneDayPrescription = oneDayPrescription().starting(anyDateBeforeTheInspectionPeriod());
        patient.withPrescriptionFor("one", oneDayPrescription);
        patient.withPrescriptionFor("two", oneDayPrescription);

        assertThat(clashFor(asList("one", "two"))).isEmpty();
    }

    private Collection<LocalDate> clashFor(Collection<String> medicineNames) {
        return patient.build().clash(medicineNames, daysBack);
    }

}