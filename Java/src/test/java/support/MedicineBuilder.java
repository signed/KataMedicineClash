package support;

import medicine.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineBuilder {

    private final List<PrescriptionBuilder> prescriptions = new ArrayList<>();
    private String name;

    public MedicineBuilder withName(String medicineName) {
        this.name = medicineName;
        return this;
    }

    public MedicineBuilder withPrescription(PrescriptionBuilder prescription) {
        prescriptions.add(prescription);
        return this;
    }

    public Medicine build() {
        if (null == name) {
            throw new IllegalArgumentException("medicine needs a name");
        }
        Medicine medicine = new Medicine(name);
        prescriptions.stream().map(PrescriptionBuilder::build).forEach(medicine::addPrescription);
        return medicine;
    }
}
