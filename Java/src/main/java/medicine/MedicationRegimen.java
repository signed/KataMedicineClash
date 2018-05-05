package medicine;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

class MedicationRegimen {

    private Map<LocalDate, List<String>> medicineNamesByDate;

    MedicationRegimen(Map<LocalDate, List<String>> medicineNamesByDate) {
        this.medicineNamesByDate = medicineNamesByDate;
    }

    boolean hasTakenAllOfAt(LocalDate day, Collection<String> medicineNames) {
        return medicineNamesByDate.getOrDefault(day, emptyList()).containsAll(medicineNames);
    }
}
