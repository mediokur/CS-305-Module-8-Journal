package com.snhu.sslserver;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
//FIXME: Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";

//We are going to spin up a rest controller that will allow us to actually post and retrieve http requests that return domain objects
//once the application is launched by the class up above, down below we use the controller to request mapping of the data that gets spit out of
//our methods.For this project, we are encrypting a static input, i.e. text that will stay the same once the program is compiled.
@RestController
class ServerController{
	//request mapping lets us actually return the string values from these two methods below into strings that will get attached to our domain object model for the application 
	@RequestMapping("/hash")
  public String myHash() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String input = "Hello World Check Sum!";
		String algorithm = "SHA-256"; //the algorithm being used, a static field implemented to make string concatenation simpler when I change algorithms
		String hashCheck = null;
		MessageDigest message = MessageDigest.getInstance(algorithm);
		byte[] byteArray = message.digest(input.getBytes(StandardCharsets.UTF_8));
		hashCheck = bytesToHex(byteArray);
		return "Data: " + input +" ---- "+ " Name of Cipher Algorithm Used: " + algorithm +" ---- "+ " Checksum: " + hashCheck ;
  }
	
	private String bytesToHex(byte[] hash) {
		StringBuilder stringCheckSum = new StringBuilder();
      for (byte bit : hash) {
          stringCheckSum.append(String.format("%02x", bit));
      }
      return stringCheckSum.toString();
	}
}