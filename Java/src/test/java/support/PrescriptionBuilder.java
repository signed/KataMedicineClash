package support;

import medicine.Prescription;

import java.time.LocalDate;

public class PrescriptionBuilder {
    private LocalDate dispenseDate;
    private int daysSupply;

    public PrescriptionBuilder starting(LocalDate date) {
        dispenseDate = date;
        return this;
    }

    public PrescriptionBuilder supplyFor(int days) {
        daysSupply = days;
        return this;
    }

    public Prescription build() {
        if (dispenseDate == null) {
            throw new IllegalArgumentException("need a dispenseDate");
        }

        if (daysSupply < 1) {
            throw new IllegalArgumentException("should have at least one day of supplies");
        }
        return new Prescription(dispenseDate, daysSupply);
    }
}
