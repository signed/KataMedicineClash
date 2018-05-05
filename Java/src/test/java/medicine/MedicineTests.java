package medicine;

import org.junit.jupiter.api.Test;
import support.MedicineBuilder;
import support.MedicineMother;
import support.PatientMother;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static support.PrescriptionMother.oneDayPrescription;

class MedicineTests {

    private final MedicineBuilder medicine = new MedicineBuilder().withName(MedicineMother.anyMedicineName());

    @Test
    void combine_all_prescription_dates_of_all_prescriptions() {
        LocalDate firstPrescriptionStartDate = PatientMother.anyPrescriptionStartDate();
        medicine.withPrescription(oneDayPrescription().starting(firstPrescriptionStartDate));
        LocalDate secondPrescriptionStartDate = PatientMother.anyOtherPrescriptionStartDate(firstPrescriptionStartDate);
        medicine.withPrescription(oneDayPrescription().starting(secondPrescriptionStartDate));

        assertThat(medicine.build().daysCoveredByPrescriptions()).containsExactly(firstPrescriptionStartDate, secondPrescriptionStartDate);
    }

    @Test
    void remove_duplicate_prescription_dates() {
        LocalDate dayWithDuplicatePrescription = PatientMother.anyPrescriptionStartDate();

        medicine.withPrescription(oneDayPrescription().starting(dayWithDuplicatePrescription));
        medicine.withPrescription(oneDayPrescription().starting(dayWithDuplicatePrescription));

        assertThat(medicine.build().daysCoveredByPrescriptions()).containsExactly(dayWithDuplicatePrescription);
    }

}