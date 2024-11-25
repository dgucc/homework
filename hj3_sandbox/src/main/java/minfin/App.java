package minfin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.namespace.QName;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import be.fgov.minfin.beps13.notification.v1.ObjectFactory;
import be.fgov.minfin.beps13.notification.v1.Declaration275CBCNOTType;

public class App {
   private static JAXBContext jaxbContext;
   private static ObjectFactory objectFactory;
   private static EntityManagerFactory entityManagerFactory;

   public static void main(String[] args) {
      System.out.println("*** Started main ***");

      try {
         jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
         objectFactory = new ObjectFactory();

         /* sample unmarshalling */
         final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
         // final Object object = unmarshaller.unmarshal(new
         // File("src/sample/samples/BEPS13_Notification_sample_v1.5.xml"));
         final Object object = unmarshaller.unmarshal(new File("src/test/samples/BEPS13_Notification_test_v1.5.xml"));
         final Declaration275CBCNOTType sample = ((JAXBElement<Declaration275CBCNOTType>) object).getValue();

         /* sample persisting after unmarshalling */
         final Properties persistenceProperties = new Properties();
         InputStream inputStream = null;
         try {
            inputStream = new FileInputStream("src/main/resources/persistence-hsqldb.properties");
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

         entityManagerFactory = Persistence.createEntityManagerFactory(
               "be.fgov.minfin.beps13.notification.v1:oecd.ties.isocbctypes.v1", persistenceProperties);

         final EntityManager entityManager = entityManagerFactory.createEntityManager();
         entityManager.getTransaction().begin();
         entityManager.remove(sample);
         entityManager.getTransaction().commit();

         entityManager.getTransaction().begin();
         entityManager.persist(sample);
         entityManager.getTransaction().commit();

         /* marshalling declaration275CBCNOT temp/export/<bce>_<declarationId>.xml */
         jaxbContext = JAXBContext.newInstance(Declaration275CBCNOTType.class);
         final Marshaller marshaller = jaxbContext.createMarshaller();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
         marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

         marshaller.marshal(
               new JAXBElement<Declaration275CBCNOTType>(
                     new QName("be.fgov.minfin.beps13.entity.notification.v1_51", "Declaration275CBCNOT"),
                     Declaration275CBCNOTType.class,
                     sample),
               // new FileOutputStream(new File("target/" + String.format("%010d",
               // sample.getDeclarer().getCompanyNumber()) + "_" + String.format("%010d",
               // sample.getHjid()) + "_notif.xml")));
               System.out);

         entityManager.close();

         entityManager.close();

      } catch (JAXBException e) {
         System.out.println(e.getMessage());
      }
      System.out.println("*** Terminated ***");
   }

}
