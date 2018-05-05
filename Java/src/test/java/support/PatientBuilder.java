package support;

import medicine.Patient;

import java.util.LinkedHashMap;
import java.util.Map;

public class PatientBuilder {

    private final Map<String, MedicineBuilder> medicines = new LinkedHashMap<>();

    public PatientBuilder withPrescriptionFor(String medicineName, PrescriptionBuilder prescription) {
        MedicineBuilder medicineBuilder = medicines.computeIfAbsent(medicineName, name -> new MedicineBuilder().withName(name));
        medicineBuilder.withPrescription(prescription);
        return this;
    }

    public PatientBuilder withoutSubscriptions() {
        medicines.clear();
        return this;
    }

    public Patient build() {
        Patient patient = new Patient();
        medicines.values().stream().map(MedicineBuilder::build).forEach(patient::addMedicine);
        return patient;
    }
}
