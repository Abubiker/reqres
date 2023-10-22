package objects.head_hunter;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Salary {
   int from;
   int to;
   String currency;
   boolean gross;
}
