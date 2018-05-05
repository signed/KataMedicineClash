package support;

import java.util.Collection;

import static java.util.Arrays.asList;

public class MedicineMother {

    public static String anyMedicineName() {
        return "any medicine name";
    }

    public static Collection<String> anyMedicineNames() {
        return asList("one", "two");
    }
}
