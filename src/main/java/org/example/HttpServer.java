package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    static Map<String, Method> componentes = new HashMap<String, Method>();
    static String ruta = " ";
    static Method method = null;

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=fb&apikey=Q1QZFVJQ21K7C6XM";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {

        Class<?> c = Class.forName(args[0]);

        if (c.isAnnotationPresent(Component.class)){
            for(Method m: c.getDeclaredMethods()){
                System.out.println("Loading class: "+c.getName());
                if(m.isAnnotationPresent(GetMapping.class)){
                    System.out.println("Load Method: " + m.getName());
                    method = m;
                    ruta = m.getAnnotation(GetMapping.class).value();
                    componentes.put(ruta, method);

                }
            }
        }

        String pathLlamadoGet = "/component/hello";
        String query = "Camiloo";
        if(pathLlamadoGet.startsWith("/component/")){
            Method miLlamado = componentes.get(pathLlamadoGet.substring(10));
            if (miLlamado != null){
                if (miLlamado.getParameterCount() == 1){
                    //String name = miLlamado.getParameters().toString();
                    String[] margs = new String[]{query};
                    System.out.println("Salida: "+ method.invoke(null, (Object)query ));

                }else {
                    System.out.println("Salida: "+ method.invoke(null));
                }

            }
        }

        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
    }

}