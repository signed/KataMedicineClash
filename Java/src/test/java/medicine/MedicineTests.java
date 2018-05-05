package medicine;

import org.junit.jupiter.api.Test;
import support.MedicineBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static support.MedicineMother.Name.AnyMedicineName;
import static support.MedicineMother.anyMedicine;
import static support.PrescriptionMother.StartDate.anyOtherPrescriptionStartDate;
import static support.PrescriptionMother.StartDate.anyPrescriptionStartDate;
import static support.PrescriptionMother.oneDayPrescription;

class MedicineTests {

    private final MedicineBuilder medicine = anyMedicine().withName(AnyMedicineName);

    @Test
    void combine_all_prescription_dates_of_all_prescriptions() {
        LocalDate firstPrescriptionStartDate = anyPrescriptionStartDate();
        LocalDate secondPrescriptionStartDate = anyOtherPrescriptionStartDate(firstPrescriptionStartDate);
        thereIsAOneDayPrescription(firstPrescriptionStartDate);
        thereIsAOneDayPrescription(secondPrescriptionStartDate);

        assertThat(daysCoveredByPrescriptions()).containsExactly(firstPrescriptionStartDate, secondPrescriptionStartDate);
    }

    @Test
    void remove_duplicate_prescription_dates() {
        LocalDate dateWithDuplicatePrescription = anyPrescriptionStartDate();
        thereIsAOneDayPrescription(dateWithDuplicatePrescription);
        thereIsAOneDayPrescription(dateWithDuplicatePrescription);

        assertThat(daysCoveredByPrescriptions()).containsExactly(dateWithDuplicatePrescription);
    }

    private List<LocalDate> daysCoveredByPrescriptions() {
        return medicine.build().daysCoveredByPrescriptions();
    }

    private void thereIsAOneDayPrescription(LocalDate firstPrescriptionStartDate) {
        medicine.withPrescription(oneDayPrescription().starting(firstPrescriptionStartDate));
    }

}