package org.example.props;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    public static String getConnectionUrlH2(){
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("application.properties")){
            Properties property = new Properties();

            if(input==null){
                System.out.println("Cant find application.properties!");
                return null;
            }

            property.load(input);

            return new StringBuilder("jdbc:")
                    .append(property.getProperty("h2.db.host"))
                    .append(":./")
                    .append(property.getProperty("h2.db.database"))
                    .toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
