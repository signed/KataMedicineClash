package medicine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Patient {

    public static final int DefaultNumberOfDaysBack = 90;
    private final Collection<Medicine> medicines = new ArrayList<>();

    public void addMedicine(Medicine medicine) {
        this.medicines.add(medicine);
    }

    public Collection<LocalDate> clash(Collection<String> medicineNames) {
        return clash(medicineNames, DefaultNumberOfDaysBack);
    }

    public Collection<LocalDate> clash(Collection<String> medicineNames, int daysBack) {
        List<String> distinctMedicineNames = medicineNames.stream().distinct().collect(toList());
        if (distinctMedicineNames.size() < 2) {
            return Collections.emptySet();
        }
        MedicationRegimen medicationRegimen = medicationRegimen();
        return datesToInspect(daysBack)
                .filter(date -> medicationRegimen.hasTakenAllOfAt(date, distinctMedicineNames))
                .collect(toList());
    }

    private Stream<LocalDate> datesToInspect(int daysBack) {
        return range(1, daysBack + 1).mapToObj(i -> LocalDate.now().minusDays(i));
    }

    private MedicationRegimen medicationRegimen() {
        Map<LocalDate, List<String>> days = new LinkedHashMap<>();

        for (Medicine medicine : medicines) {
            for (LocalDate localDate : medicine.daysCoveredByPrescriptions()) {
                days.computeIfAbsent(localDate, day -> new ArrayList<>()).add(medicine.name());
            }
        }
        return new MedicationRegimen(days);
    }


}
