import com.google.type.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
    public static void main(String[] args) {
        Random random = new Random();
        int studyTime = random.nextInt(3*3600)+ 19*3600;

        // 随机输出19点-22点之间的时刻
        System.out.println("钰回寝室的时刻为:" + LocalTime.ofSecondOfDay(studyTime));

    }


}
