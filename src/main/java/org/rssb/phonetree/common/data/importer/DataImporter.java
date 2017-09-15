package org.rssb.phonetree.common.data.importer;

import org.rssb.phonetree.spring.config.DataImportConfigurations;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class DataImporter {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(DataImportConfigurations.class);
        ApplicationContext applicationContext = builder.run(args);
        applicationContext.getBean(DataImportBuilder.class).run();
        //String value="(732) 698-0331";
        /*String value = "9088521894 Ext #171";
        String data = value.replaceAll("[A-Za-z(\\s)-]","");
        System.out.println(data);
        if(data.length()>10 && data.length()<21){
            data = data.substring(0,10)+","+data.substring(11);
        }
        System.out.println(data);
        //"9738141508 (Mina)9738072845 (Hasmukh)"

        System.out.println(String.format("(%s) %s-%s",data.substring(0,3),data.substring(3,6),data.substring(6,10)));*/
    }
}
