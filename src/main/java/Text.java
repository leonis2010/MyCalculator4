import java.util.HashMap;

public class Text {
    private static final HashMap<String, String> data = new HashMap<>();
    public static String get (String key){
        if (data.containsKey(key)){
            return data.get(key);
        }else {
            System.out.println("������ ����� �� ����������");//��� �������
            return "";
        }
//        public static void init(){
//            data.put("ADD", "��������");
//        }
    }
}
