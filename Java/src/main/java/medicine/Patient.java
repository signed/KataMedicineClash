package medicine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Patient {

    private final Collection<Medicine> medicines = new ArrayList<Medicine>();

    public void addMedicine(Medicine medicine) {
        this.medicines.add(medicine);
    }

    public Collection<LocalDate> clash(Collection<String> medicineNames) {
        return clash(medicineNames, 90);
    }

    public Collection<LocalDate> clash(Collection<String> medicineNames, int daysBack) {
        ArrayList<LocalDate> localDates = new ArrayList<>();
        List<String> dasfsa = medicines.stream().map(Medicine::name).collect(Collectors.toList());
        if (dasfsa.containsAll(medicineNames)) {
            localDates.add(LocalDate.now().minusDays(1));
        }
        return localDates;
    }


}
