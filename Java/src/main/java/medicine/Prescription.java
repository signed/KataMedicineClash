package medicine;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Prescription {
    
    private LocalDate dispenseDate;
    private int daysSupply;
    
    public Prescription(LocalDate dispenseDate, int daysSupply) {
        this.dispenseDate = dispenseDate;
        this.daysSupply = daysSupply;
    }

    public List<LocalDate> daysCoveredByPrescription() {
        return range(0, daysSupply).mapToObj(i -> dispenseDate.plusDays(i)).collect(toList());
    }
}
