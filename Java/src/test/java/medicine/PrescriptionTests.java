package medicine;

import org.junit.jupiter.api.Test;
import support.PrescriptionBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static support.PrescriptionMother.SupplyDays.oneDay;
import static support.PrescriptionMother.prescriptionStarting;
import static support.PrescriptionMother.SupplyDays.threeDays;
import static support.PrescriptionMother.SupplyDays.toSmallNumberOfDays;

class PrescriptionTests {

    private final LocalDate prescriptionStartDay = anyDay();
    private final PrescriptionBuilder prescription = prescriptionStarting(prescriptionStartDay);

    @Test
    void one_day_prescription_only_covers_the_dispense_day() {
        prescription.supplyFor(oneDay());

        assertThat(daysCoveredByPrescription()).containsExactly(prescriptionStartDay);
    }

    @Test
    void longer_lasting_prescriptions_cover_dispense_day_and_subsequent_days() {
        prescription.supplyFor(threeDays());

        assertThat(daysCoveredByPrescription()).containsExactly(prescriptionStartDay, prescriptionStartDay.plusDays(1), prescriptionStartDay.plusDays(2));
    }

    @Test
    void prescriptions_with_not_allowed_amount_of_days_covers_no_days() {
        Prescription prescription = new Prescription(prescriptionStartDay, toSmallNumberOfDays());

        assertThat(prescription.daysCoveredByPrescription()).isEmpty();
    }

    private List<LocalDate> daysCoveredByPrescription() {
        return this.prescription.build().daysCoveredByPrescription();
    }

    private LocalDate anyDay() {
        return LocalDate.now();
    }
}