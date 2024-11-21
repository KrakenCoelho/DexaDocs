package com.dexadocs.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dexadocs.repository.FicheiroRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/upload")
@Slf4j
public class ArquivoUploadController {
	
	@Autowired
	static FicheiroRepository ficheiroRepository;
	
	
	
	 @RequestMapping(value="/", method=RequestMethod.GET)
	   public String Home(){
	    return "/index";
	    
	    }
	 
	 
	//-------- admin Funcionarios
	 @RequestMapping(value="/index",method=RequestMethod.GET)
	   public String Index(){
	    return "/index";
	    
	    }
	 

	 
	 
	 public String singleFileUpload (HttpServletRequest request,MultipartFile file, String pasta) {
	        String novoNome="sem-foto.png";
	        if(!(file.isEmpty())) {
	        try {
	             String realPathtoUploads =  request.getServletContext().getRealPath("upload/");
	             //novoNome = new Date().getTime() +file.getOriginalFilename().substring(file.getOriginalFilename().length()-5);
	             //novoNome = new Date().getTime() +file.getOriginalFilename();
	             novoNome = file.getOriginalFilename();
	             novoNome = novoNome.replaceAll("()", "");
	             //novoNome = novoNome.replace(" ", "");
	             System.out.println(realPathtoUploads+"--"+novoNome);
	                String filePath = realPathtoUploads +pasta +"/"+ novoNome;
	                 File dest = new File(filePath);
	                 file.transferTo(dest);    
	            } catch (IOException e) {
	                
	                System.out.println("ERRO DE UPLOADS: "+ e.getMessage());
	            }}
	         return novoNome;  

	    }
	 
	 
	 public String singleFileUploadEdit (HttpServletRequest request,MultipartFile file, String pasta, String prev_image) {
	        String novoNome=prev_image;
	        if(!(file.isEmpty())) {
	        try {
	             String realPathtoUploads =  request.getServletContext().getRealPath("imgs/");
	             novoNome = new Date().getTime() +file.getOriginalFilename().substring(file.getOriginalFilename().length()-5);
	             novoNome = novoNome.replace(")", "");
	             System.out.println(realPathtoUploads+"--"+novoNome);
	                String filePath = realPathtoUploads +pasta +"/"+ novoNome;
	                 File dest = new File(filePath);
	                 file.transferTo(dest);    
	            } catch (IOException e) {
	                
	                System.out.println("ERRO DE UPLOADS: "+ e.getMessage());
	            }}
	         return novoNome;  

	    }

	    
	 
	 @ResponseBody
	    @RequestMapping("/ckeditorUpload")
	    public String ckeditorUpload(@RequestParam("upload") MultipartFile file,HttpServletRequest request) throws Exception {
		 
		 String newFilename = singleFileUpload(request,file,"uploads");
		 
	
	
		 String ret = "{\n " +
			        "    \"uploaded\": 1,\n" +
			        "    \"fileName\": \""+newFilename+"\",\n" +
			        "    \"url\": \"http://localhost:8080/imgs/uploads/"+newFilename+"\"\n" +
			        "}";
		 
	     return ret;
	     
	  // To sent server
	     
	    /* String ret = "{\n " +
	                "    \"uploaded\": 1,\n" +
	                "    \"fileName\": \""+newFilename+"\",\n" +
	                "    \"url\": \"http://clientes.dexa.ao/imgs/uploads/"+newFilename+"\"\n" +
	                "}";
	                
	        return ret; */      
	     
		 
		 
	  // To sent server Rellevant
		    /*String ret = "{\n " +
		                "    \"uploaded\": 1,\n" +
		                "    \"fileName\": \""+newFilename+"\",\n" +
		                "    \"url\": \"https://dexa_hr.appteste.info/imgs/uploads/"+newFilename+"\"\n" +
		                "}";
		                
		        return ret;*/    
	     
	 	
	    }
	
	 
	 
	 
	 public String DataActual() {
		 Date date = new Date();
		 SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
		
		 return formatter.format(date);
	}
	 
	 
	 
	 public static String contra(String nome_ficheiro) {
			
		 String  ter= new String(ficheiroRepository.en(nome_ficheiro));
		 return ter;
	} 
	 
	 
	 
	 
	 
	

}