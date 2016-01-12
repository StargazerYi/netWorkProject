package jsonAnalyzer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

import net.sf.json.*;

/**
 * JsonAnalyzer is used to build JSON messages or parse JSON messages.
 */
public class JsonAnalyzer {

    /**
     * Build message 0 by given parameters.
     * @param user user's common name
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type0(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 0);
        map.put("UserId", id);
        
        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 1 by given parameters.
     * @param user user's common name
     * @param pass user's password
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type1(String id, String name, String pass) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 1);
        map.put("UserId", id);
        map.put("UserName", name);
        map.put("Password", pass);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 2 by given parameters.
     * @param user user's common name
     * @param pass user's password
     * @param upke user's public key
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type2(String id, String pass) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 2);
        map.put("UserId", id);
        map.put("Password", pass);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 3 by given parameters.
     * @param user user's common name
     * @param list the users to be invited
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type3(String id, ArrayList<String> list) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 3);
        map.put("UserId", id);
        map.put("GroupList", list);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 4 by given parameters.
     * @param user user's common name
     * @param group the identifier of the group chat at server
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type4(String id, String group, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 4);
        map.put("UserId", id);
        map.put("GroupId", group);
        map.put("Message", message);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 5 by given parameters.
     * @param user user's common name
     * @param group the identifier of the group chat at server
     * @param message what user want to say at group chat
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type5(String userid, String talkid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 5);
        map.put("UserId", userid);
        map.put("TalkId", talkid);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 6 by given parameters.
     * @param user user's common name
     * @param target the user that current user want to chat with
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type6(String userid, String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 6);
        map.put("UserId", userid);
        map.put("UserName", username);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 7 by given parameters.
     * @param user user's common name
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type7(String userid, String group) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 7);
        map.put("UserId", userid);
        map.put("GroupId", group);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 8 by given parameters.
     * @param user user's common name
     * @param status the status of the request
     * @param message the feedback from server to user
     * @param sign server's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type8(String userid, String username,boolean status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 8);
        map.put("UserId", userid);
        map.put("UserName", username);
        map.put("Status", status);
        
        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 9 by given parameters.
     * @param user user's common name
     * @param status the status of the request
     * @param message the feedback from server to user
     * @param sign server's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type9(String userid, String username, boolean status, ArrayList<String> idlist, ArrayList<String> namelist) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 9);
        map.put("UserId", userid);
        map.put("UserName", username);
        map.put("Status", status);
        map.put("UserIDList", idlist);
        map.put("UserNameList", namelist);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 10 by given parameters.
     * @param group the identifier of the group chat at server
     * @param sign server's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type10(String group) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 10);
        map.put("GroupId", group);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 11 by given parameters.
     * @param user user's common name
     * @param group the identifier of the group chat at server
     * @param sign server's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type11(String userid, String username, String group, ArrayList<String> idlist, ArrayList<String> namelist) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 11);
        map.put("UserId", userid);
        map.put("UserName", username);
        map.put("GroupId", group);
        map.put("GroupIDList", idlist);
        map.put("GroupNameList", namelist);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 12 by given parameters.
     * @param group the identifier of the group chat at server
     * @paraam list the users in this group chat
     * @param sign server's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type12(String talkid, String talkname, String group, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 12);
        map.put("TalkId", talkid);
        map.put("TalkName", talkname);
        map.put("GroupId", group);
        map.put("Message", message);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 13 by given parameters.
     * @param group the identifier of the group chat at server
     * @paraam message the message that someone says at the group chat
     * @param sign server's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type13(String userid, String talkid, String talkname, String talkip, int talkport) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 13);
        map.put("UserId", userid);
        map.put("TalkId", talkid);
        map.put("TalkName", talkname);
        map.put("TalkIp", talkip);
        map.put("TalkPort", talkport);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 14 by given parameters.
     * @param user user's common name
     * @param target the user that cuurent user want to chat with
     * @param IP the target user's IP address
     * @param pkey the target user's public key
     * @param ticket the conversation ticket from server
     * @param state the value to show target user is online or not
     * @param message the feedback from server
     * @param sign server's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type14(String userid, String talkid, String talkname, String talkip, int talkport) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 14);
        map.put("UserId", userid);
        map.put("TalkId", talkid);
        map.put("TalkName", talkname);
        map.put("TalkIp", talkip);
        map.put("TalkPort", talkport);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 15 by given parameters.
     * @param user user's common name
     * @param IP the IP of the user who wants to chat with you
     * @param ticket the conversation ticket from server
     * @param pkey the target user's public key
     * @param sign server's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type15(String userid, String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 15);
        map.put("UserId", userid);
        map.put("UserName", username);


        JSONObject result = JSONObject.fromObject(map);
        return result;
    }
    
    
    public static JSONObject type16(String userid, String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 16);
        map.put("UserId", userid);
        map.put("UserName", username);


        JSONObject result = JSONObject.fromObject(map);
        return result;
    }
    
    public static JSONObject type17(String userid, String group) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 17);
        map.put("UserId", userid);
        map.put("GroupId", group);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 16 by given parameters.
     * @param user user's common name
     * @param IP the target user's IP address
     * @param ticket the conversation ticket from server
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type18(String userid, String username, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 18);
        map.put("UserId", userid);
        map.put("UserName", username);
        map.put("Message", message);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }

    /**
     * Build message 17 by given parameters.
     * @param user user's common name
     * @param message the message be sent to target user
     * @param sign user's digital signature
     * @return a JsonObject with given values
     */
    public static JSONObject type19(String userid, String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Type", 19);
        map.put("UserId", userid);
        map.put("UserName", username);

        JSONObject result = JSONObject.fromObject(map);
        return result;
    }


    /**
     * Build a java Map by given json form String.
     * @param jsonString the given String
     * @return a Map containing messages in the given String
     */
    public static Map<String, String> parse(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator<String> it = jsonObject.keys(); //warning
        String key;
        Map<String, String> result = new HashMap<String, String>();
        while (it.hasNext()) {
            key = it.next();
            result.put(key, jsonObject.getString(key));
        }
        return result;
    }
}