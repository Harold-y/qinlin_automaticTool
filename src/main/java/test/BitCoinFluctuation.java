package test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BitCoinFluctuation {
    public static ArrayList transferJSON(JSONObject json)
    {
        ArrayList<Object> resultList = new ArrayList<>();
        Iterator it =json.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            if(entry.getKey().equals("Time Series (Digital Currency Weekly)"))
            {
                resultList.add(entry.getValue());
            }
        }
        return resultList;
    }

    public static LinkedHashMap<String, LinkedHashMap> processJSON(JSONObject json)
    {
        LinkedHashMap<String, LinkedHashMap> outerMap = new LinkedHashMap<>();
        ArrayList<Object> list = transferJSON(json);
        JSONObject result1 = (JSONObject) list.get(0);
        Iterator it =result1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            LinkedHashMap<String, String> innerMap = new LinkedHashMap<>();
            JSONObject result2 = (JSONObject) entry.getValue();
            Iterator it2 =result2.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<String, Object> entry2 = (Map.Entry<String, Object>) it2.next();
                innerMap.put(entry2.getKey(), (String) entry2.getValue());
            }
            outerMap.put(entry.getKey(), innerMap);
        }
        return outerMap;
    }

    public static List<LinkedHashMap> processJSONList(JSONObject json)
    {
        ArrayList<Object> list = transferJSON(json);
        ArrayList<LinkedHashMap> returnList = new ArrayList<>();
        JSONObject result1 = (JSONObject) list.get(0);
        Iterator it =result1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();

            LinkedHashMap innerMap = new LinkedHashMap<>();
            JSONObject result2 = (JSONObject) entry.getValue();
            Iterator it2 =result2.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<String, Object> entry2 = (Map.Entry<String, Object>) it2.next();
                innerMap.put(entry2.getKey(), entry2.getValue());
            }
            returnList.add(innerMap);
        }
        return returnList;
    }

    public static double getVariation() throws IOException {
        String url = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_WEEKLY&symbol=BTC&market=CNY&apikey=41ZKJAGWRQF5844Z";
        File file = new File("src/main/java/query.json");
        String str = FileUtils.readFileToString(file, "UTF-8");
        JSONObject json = JSONObject.parseObject(str);
        // JSONObject json = ReadUrlUtil.readJsonFromUrl(url);
        List<LinkedHashMap> list = processJSONList(json);
        int g = new Random().nextInt(100);
        int g2 = new Random().nextInt(100);
        double value = Double.parseDouble((String) list.get(g).get("4b. close (USD)"));
        double value2 = Double.parseDouble((String) list.get(g2).get("4b. close (USD)"));
        double variation = (value - value2) / value2 / 3;
        return variation;
    }
    public static void main(String[] args) throws IOException {

    }
}
