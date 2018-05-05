package medicine;

import org.junit.jupiter.api.Test;
import support.PatientBuilder;
import support.PrescriptionBuilder;

import java.time.LocalDate;
import java.util.Collection;

import static java.util.Arrays.asList;
import static support.PatientMother.patientWithoutSubscriptions;
import static org.assertj.core.api.Assertions.assertThat;

class PatientTests {

    private PatientBuilder patient = patientWithoutSubscriptions();
    private int daysBack = defaultPeriod();

    @Test
    void patient_without_subscriptions_has_no_clashes() {
        patient = patientWithoutSubscriptions();

        assertThat(clashFor(anyMedicineNames())).isEmpty();
    }

    @Test
    void patient_with_with_two_clashing_prescriptions() {
        PrescriptionBuilder oneDayPrescription = new PrescriptionBuilder();
        oneDayPrescription.starting(LocalDate.now()).supplyFor(1);

        patient.withPrescriptionFor("one", oneDayPrescription);
        patient.withPrescriptionFor("two", oneDayPrescription);

        assertThat(clashFor(asList("one", "two"))).containsOnly(LocalDate.now().minusDays(1));
    }

    private Collection<LocalDate> clashFor(Collection<String> medicineNames) {
        return patient.build().clash(medicineNames, daysBack);
    }

    private Collection<String> anyMedicineNames() {
        return asList("one", "two");
    }

    private static int defaultPeriod() {
        return 90;
    }

}