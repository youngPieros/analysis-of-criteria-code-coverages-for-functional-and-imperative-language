package utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper<T> {
    private ObjectMapper mapper;
    private Class<T> className;
    public JsonMapper(Class<T> className){
        mapper = new ObjectMapper();
        this.className = className;
    }
    public T readJson(String json){
        Object obj = null;
        try {
            obj =  mapper.readValue(json, className);
        }
        catch (JsonProcessingException e){
            System.out.println(e.getMessage());
        }
        return (T) obj;
    }

    public String toJson(T t){
        String jsonString = "";
        try{
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        }
        catch(JsonProcessingException e){
            System.out.println(e.getMessage());
        }
        return jsonString;
    }


}
