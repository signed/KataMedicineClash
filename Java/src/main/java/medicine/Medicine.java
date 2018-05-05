package medicine;

import java.util.ArrayList;
import java.util.Collection;

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
}
