package org.example.po;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import be.fgov.minfin.beps13.notification.v1.ObjectFactory;
import be.fgov.minfin.beps13.notification.v1.Declaration275CBCNOTType;

public class App {
   private static JAXBContext jaxbContext;
	private static ObjectFactory objectFactory;
	private static EntityManagerFactory entityManagerFactory;

   public static void main(String[] args) {
      try {
         jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
         objectFactory = new ObjectFactory();

         /* sample unmarshalling */
         final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
         // final Object object = unmarshaller.unmarshal(new File("src/sample/samples/BEPS13_Notification_sample_v1.5.xml"));
         final Object object = unmarshaller.unmarshal(new File("src/test/samples/sample.xml"));
         final Declaration275CBCNOTType sample = ((JAXBElement<Declaration275CBCNOTType>) object).getValue();

         /* sample persisting after unmarshalling */
         final Properties persistenceProperties = new Properties();
         InputStream inputStream = null;
         try {
            inputStream = new FileInputStream("src/main/resources/persistence-h2.properties");
            persistenceProperties.load(inputStream);
         } catch (IOException e) {
         } finally {
            if (inputStream != null) {
               try {
                  inputStream.close();
               } catch (IOException ignored) {
               }
            }
         }

         entityManagerFactory = Persistence.createEntityManagerFactory("be.fgov.minfin.beps13.notification.v1:oecd.ties.isocbctypes.v1", persistenceProperties);

         final EntityManager entityManager = entityManagerFactory.createEntityManager();
         entityManager.getTransaction().begin();
         entityManager.remove(sample);
         entityManager.getTransaction().commit();

         entityManager.getTransaction().begin();
         entityManager.persist(sample);
         entityManager.getTransaction().commit();
         entityManager.close();

         System.out.println("CompanyNumber : " + sample.getDeclarer().getCompanyNumber());
         System.out.println("CountryCodes :" + sample.getDeclarer().getCompanyName());
         System.out.println("sample.getId() : " + sample.getHjid());

      } catch (JAXBException e) {
         System.out.println(e.getMessage());
      }
      System.out.println("*** Terminated ***");
   }

   
}
