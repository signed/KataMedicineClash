package medicine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
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
        if (medicineNames.size() < 2) {
            return Collections.emptySet();
        }
        Map<LocalDate, List<String>> medicationRegimen = medicationRegimen();
        return range(1, daysBack + 1).mapToObj(i -> LocalDate.now().minusDays(i))
                .filter(date -> hasClash(medicationRegimen, medicineNames, date))
                .collect(Collectors.toList());
    }

    private boolean hasClash(Map<LocalDate, List<String>> medicationRegimen, Collection<String> medicineNames, LocalDate yesterday) {
        return medicationRegimen.getOrDefault(yesterday, emptyList()).containsAll(medicineNames);
    }

    private Map<LocalDate, List<String>> medicationRegimen() {
        Map<LocalDate, List<String>> days = new LinkedHashMap<>();

        for (Medicine medicine : medicines) {
            for (LocalDate localDate : medicine.daysCoveredByPrescriptions()) {
                days.computeIfAbsent(localDate, day -> new ArrayList<>()).add(medicine.name());
            }
        }
        return days;
    }


}
