package App;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import jakarta.json.*;

public class main2 {
    public static void main(String[] args) {
        jsonCuerpoMsg jsmsg = new jsonCuerpoMsg("01", "02");
        Gson json = new Gson();
        String response = json.toJson(jsmsg);
        System.out.println(response);
        /*jsonCuerpoMsg c2 = json.fromJson(response, jsonCuerpoMsg.class);
        System.out.println(c2.getDoc() + " " + c2.getPaciente());*/


    }


}
