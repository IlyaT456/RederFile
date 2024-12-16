package readerFile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReaderFileJackson {
    PeopleJackson peopleJackson = new PeopleJackson();

    @Test
    @DisplayName("Преобразование из формат Jackson в class")
    public void conversionFromJacksonFormat() {
        JsonHelper.fromJson("src/test/resources/jacksonFile.json", PeopleJackson.class);
    }

    @Test
    @DisplayName("Преобразование в формат Jackson")
    public void сonversionToJacksonFormat() {
        JsonHelper.toJson(peopleJackson);
    }
}
