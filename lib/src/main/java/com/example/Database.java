package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Database {
    public static void main(String []args)throws Exception{
        Schema schema = new Schema(1,"com.zxr.medicalaid");
        addUser(schema);
        new DaoGenerator().generateAll(schema,"app/src/main/java-gen");
    }
    public static void addUser(Schema schema){
        Entity user = schema.addEntity("User");
        user.addStringProperty("uId");
        user.addIdProperty().primaryKey();
        user.addStringProperty("userName");
        user.addStringProperty("phoneNum");
        user.addStringProperty("icon");
    }
}

