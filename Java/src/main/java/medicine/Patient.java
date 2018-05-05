package medicine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Patient {

    private final Collection<Medicine> medicines = new ArrayList<>();

    public void addMedicine(Medicine medicine) {
        this.medicines.add(medicine);
    }

    public Collection<LocalDate> clash(Collection<String> medicineNames) {
        return clash(medicineNames, 90);
    }

    public Collection<LocalDate> clash(Collection<String> medicineNames, int daysBack) {
        List<LocalDate> daysWithClashingPrescriptions = new ArrayList<>();


        List<String> dasfsa = medicines.stream()
                .map(Medicine::name)
                .collect(toList());

        if (dasfsa.containsAll(medicineNames)) {
            daysWithClashingPrescriptions.add(LocalDate.now().minusDays(1));
        }
        return daysWithClashingPrescriptions;
    }


}
