package medicine;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static medicine.PatientMother.patientWithoutSubscriptions;
import static org.assertj.core.api.Assertions.assertThat;

class PatientTests {

    private PatientBuilder patient;
    private int daysBack = defaultPeriod();

    @Test
    void patient_without_subscriptions_has_no_clashes() {
        patient = patientWithoutSubscriptions();

        assertThat(clashFor(anyMedicineNames())).isEmpty();
    }

    private Collection<Date> clashFor(Collection<String> medicineNames) {
        return patient.build().clash(medicineNames, daysBack);
    }

    private Collection<String> anyMedicineNames() {
        return Arrays.asList("one", "two");
    }

    private static int defaultPeriod() {
        return 90;
    }

}