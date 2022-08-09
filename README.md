# pdf-watermark-writer
PDF Generator with watermark to enforce content rights protection

Run Spring boot application from Maven:

```
mvn spring-boot:run
```

Run a specific class in the project using Maven:

```
mvn compile
mvn compile exec:java -'Dexec.mainClass=com.github.ifsantos.PdfWatermarkWriterApplication'
```

## Backlog
- Implement async PDF generation and let the conclusion notification available as a websocket
- Remode database configuration
- Make http2 work on Heroku environemnt



### Refferences
 - <https://stackhowto.com/how-to-add-text-to-an-image-in-java/>
 - <https://www.baeldung.com/java-pdf-creation>
 - <https://www.codejava.net/java-se/file-io/programmatically-extract-a-zip-file-using-java>
 - <https://spring.io/guides/gs/uploading-files/>
 - <https://stackoverflow.com/questions/12239868/whats-the-correct-way-to-send-a-file-from-rest-web-service-to-client>
 - [HTTP2](https://byte27.com/2020/02/03/using-http-2-in-your-spring-boot-application/)

 <!--
  openssl pkcs12 -export -in heroku_b64.cer  -name herokuapp.com -out localhost.p12 
  curl -kvI https://www.example.com
 -->
