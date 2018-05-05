package support;

import java.util.Collection;

import static java.util.Arrays.asList;

public class MedicineMother {

    public static MedicineBuilder anyMedicine() {
        return new MedicineBuilder();
    }

    public static class Name {
        public static final String AnyMedicineName = "any medicine name";
        public static final String Medicine_A = "medicine A";
        public static final String Medicine_B = "medicine B";

        public static Collection<String> anyDistinctMedicineNames() {
            return asList(Medicine_A, Medicine_B);
        }

    }

}
