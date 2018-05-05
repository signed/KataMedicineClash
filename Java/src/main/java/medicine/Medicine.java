package medicine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Medicine {

    private final Collection<Prescription> prescriptions = new ArrayList<>();
    private final String name;

    public Medicine(String name) {
        this.name = name;
    }

    public void addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
    }

    public String name() {
        return name;
    }

    public List<LocalDate> daysCoveredByPrescriptions() {
        return prescriptions.stream().
                flatMap(prescription -> prescription.daysCoveredByPrescription().stream())
                .distinct()
                .collect(toList());
    }
}
