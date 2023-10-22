package objects.head_hunter;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import objects.head_hunter.Salary;

@Data
public class Vacancy {
    String name;
    Salary salary;
    @SerializedName("alternate_url")
    String alternateUrl;
}
