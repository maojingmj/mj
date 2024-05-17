package test;
import java.io.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        // 读取地铁网数据
        Map<String, Map<String, Integer>> subwayMap = readSubwayData("subway.txt");
       
        
        // 测试功能2：输入某一站点，输出线路距离小于n的所有站点集合
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入站点名称：");
        String inputStation = scanner.nextLine();
        System.out.print("请输入距离n：");
        int distanceN = scanner.nextInt();
        scanner.close();
        
        // 调用函数实现测试功能
        testFindNearbyStations(subwayMap, inputStation, distanceN);
    }
    
    /**
     * 读取地铁网数据
     * @param filePath 文件路径
     * @return 地铁网数据
     */
    private static Map<String, Map<String, Integer>> readSubwayData(String filePath) {
        Map<String, Map<String, Integer>> subwayMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\28560\\Desktop\\subway.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    String[] startStation = parts[0].split(",");
                    String[] endStation = parts[1].split(",");
                    int distance = Integer.parseInt(parts[2]);
                    
                    Map<String, Integer> stationMap = subwayMap.computeIfAbsent(startStation[0], k -> new HashMap<>());
                    stationMap.put(endStation[0], distance);
                    
                    stationMap = subwayMap.computeIfAbsent(endStation[0], k -> new HashMap<>());
                    stationMap.put(startStation[0], distance);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subwayMap;
    }
    
   
    
    /**
     * 测试功能：输入某一站点，输出线路距离小于n的所有站点集合
     * @param subwayMap 地铁网数据
     * @param inputStation 输入站点名称
     * @param distanceN 距离n
     */
    private static void testFindNearbyStations(Map<String, Map<String, Integer>> subwayMap, String inputStation, int distanceN) {
        if (subwayMap == null || subwayMap.isEmpty()) {
            System.out.println("地铁网数据为空！");
            return;
        }
        
        if (inputStation == null || inputStation.isEmpty()) {
            System.out.println("输入站点名称不能为空！");
            return;
        }
        
        if (distanceN <= 0) {
            System.out.println("距离n必须大于0！");
            return;
        }
        
        Map<String, Integer> nearbyStations = subwayMap.get(inputStation);
        if (nearbyStations == null || nearbyStations.isEmpty()) {
            System.out.println("未找到输入站点或站点无连接线路！");
            return;
        }
        
        System.out.println("距离" + inputStation + "站距离小于" + distanceN + "的站点集合：");
        for (Map.Entry<String, Integer> entry : nearbyStations.entrySet()) {
            if (entry.getValue() < distanceN) {
                System.out.println("<" + entry.getKey() + "，" + entry.getValue() + ">");
                }
        }
    }
  
}