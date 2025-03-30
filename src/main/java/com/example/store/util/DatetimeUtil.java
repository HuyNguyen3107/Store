package com.example.store.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DatetimeUtil {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    /**
     * Chuyển đổi chuỗi ngày tháng (dd/MM/yyyy) sang mili giây.
     * @param dateString Chuỗi ngày tháng cần chuyển đổi.
     * @return Giá trị mili giây tương ứng.
     * @throws ParseException Nếu chuỗi không hợp lệ.
     */
    public static long convertDateToMillis(String dateString) throws ParseException {
        Date date = sdf.parse(dateString);
        return date.getTime();
    }

    /**
     * Chuyển đổi mili giây sang chuỗi ngày tháng (dd/MM/yyyy).
     * @param millis Giá trị mili giây cần chuyển đổi.
     * @return Chuỗi ngày tháng tương ứng.
     */
    public static String convertMillisToDate(long millis) {
        Date date = new Date(millis);
        return sdf.format(date);
    }

    /**
     * Tính tổng số buổi học và lịch học dựa trên ngày bắt đầu, ngày kết thúc và danh sách các thứ trong tuần.
     * @param startDate Ngày bắt đầu (dd/MM/yyyy).
     * @param endDate Ngày kết thúc (dd/MM/yyyy).
     * @param weekdays Chuỗi chứa các thứ trong tuần (ngăn cách bằng khoảng trắng, ví dụ: "2 4 6").
     * @return Object chứa tổng số buổi học và danh sách lịch học cụ thể.
     * @throws ParseException Nếu có lỗi khi chuyển đổi ngày.
     */
    public static Map<String, Object> calculateTotalClasses(String startDate, String endDate, String weekdays) throws ParseException {
        long startMillis = convertDateToMillis(startDate);
        long endMillis = convertDateToMillis(endDate);

        Set<Integer> classDays = Arrays.stream(weekdays.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        List<String> schedule = new ArrayList<>();
        int totalClasses = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startMillis);

        while (calendar.getTimeInMillis() <= endMillis) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int normalizedDay = (dayOfWeek == Calendar.SUNDAY) ? 8 : dayOfWeek; // Chuyển đổi Chủ Nhật thành 8 để phù hợp với định dạng VN
            if (classDays.contains(normalizedDay - 1)) { // Trừ đi 1 để phù hợp với chuẩn VN
                schedule.add(sdf.format(calendar.getTime()));
                totalClasses++;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalClasses", totalClasses);
        result.put("schedule", schedule);
        return result;
    }

    public static void main(String[] args) {
        // try {
        //     String dateString = "30/03/2025";
        //     long millis = convertDateToMillis(dateString);
        //     System.out.println("Mili giây: " + millis);
            
        //     String formattedDate = convertMillisToDate(millis);
        //     System.out.println("Chuỗi ngày tháng: " + formattedDate);
            
        //     // Kiểm tra hàm tính số buổi học
        //     Map<String, Object> result = calculateTotalClasses("01/04/2025", "30/04/2025", "2 4 6");
        //     System.out.println("Tổng số buổi học: " + result.get("totalClasses"));
        //     System.out.println("Lịch học: " + result.get("schedule"));
        // } catch (ParseException e) {
        //     System.err.println("Lỗi định dạng ngày tháng: " + e.getMessage());
        // }
    }
}
