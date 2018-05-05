package medicine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

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
        Map<LocalDate, List<String>> days = medicationRegimen();
        return IntStream.range(1, daysBack + 1).mapToObj(i -> LocalDate.now().minusDays(i))
                .filter(day -> hasClash(medicineNames, days, day))
                .collect(Collectors.toList());
    }

    private boolean hasClash(Collection<String> medicineNames, Map<LocalDate, List<String>> days, LocalDate yesterday) {
        List<String> dasfsa = days.getOrDefault(yesterday, emptyList());
        return dasfsa.containsAll(medicineNames);
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
