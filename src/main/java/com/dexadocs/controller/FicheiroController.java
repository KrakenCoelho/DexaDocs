package com.dexadocs.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.BaseStream;
import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dexadocs.model.ActiUsua;
import com.dexadocs.model.Admin;
import com.dexadocs.model.Ficheiro;
import com.dexadocs.model.Pasta;
import com.dexadocs.model.Pastainside;
import com.dexadocs.model.Usuario;
import com.dexadocs.repository.ActusuaRepository;
import com.dexadocs.repository.AdminRepository;
import com.dexadocs.repository.FicheiroRepository;
import com.dexadocs.repository.PastaRepository;
import com.dexadocs.repository.PastainsideRepository;
import com.dexadocs.repository.UsuarioRepository;

@Controller
@RequestMapping()
public class FicheiroController extends HttpServlet{

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PastaRepository pastaRepository;
	
	@Autowired
	private FicheiroRepository ficheiroRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	
	@Autowired
	private PastainsideRepository pastainsideRepository;
	
	@Autowired
	private ActusuaRepository auRepository;
	
	
	ArquivoUploadController anexo = new ArquivoUploadController();
	
	@Autowired
	EmailService  em;
	
	
	@GetMapping("/index2")
	public String inicio1 (Model model, HttpSession session) {
		model.addAttribute("usuario", new Usuario());
		return "index2";}
	
	//DOCUMENTO
	
		@GetMapping("/documento/criar-documento")
		public String CriarDocumento (Model model, HttpServletRequest request, Ficheiro ficheiro) {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					
					model.addAttribute("ficheiro", new Ficheiro());
					
					model.addAttribute("todaspastas", pastaRepository.vertodos());
					model.addAttribute("pastainsidetodas", pastainsideRepository.vertodos());
				}
			}
			else {
				
				return "redirect:/index";
				
			}
			
			return "/documento/criar-documento";
		}
		
		
		 @RequestMapping(value = "/pastainsideespec", method = RequestMethod.GET)
		 public @ResponseBody Iterable <Pastainside> espec2(@RequestParam(value = "pasta_mae", required = true) Long pasta_mae,HttpSession session) {
			 
		//System.out.println(pastainsideRepository.espec2(pasta_mae));
		     //return  pastainsideRepository.espec();
		     return  pastainsideRepository.espec2(pasta_mae);
		 }
		

			
		 @RequestMapping(value="/salvardocumento", method = RequestMethod.POST)
			public String Salvardocumento(HttpServletRequest request, Model model, @Valid Ficheiro ficheiro, @RequestParam ("files") MultipartFile files, RedirectAttributes attributes) throws IOException{
			
					
				try {	
					
					StringBuilder fileNames = new StringBuilder();
					
					Iterable <Pastainside> kr=pastainsideRepository.fidById(ficheiro.getSub_pasta());
					String submain = kr.iterator().next().getNome_pastainside();
					Long idsm = kr.iterator().next().getPasta_mae();
					
					
					Iterable <Pasta> pr=pastaRepository.fidById(idsm);
					String main = pr.iterator().next().getNome_pasta();
					
					String name_document = anexo.singleFileUpload(request, files, "documentos/"+main+"/"+submain);
					  System.out.println(name_document);
					  ficheiro.setArquivo(name_document);
					  ficheiroRepository.save(ficheiro);
					System.out.println("Ficheiro adicionado com sucesso!!!");
					
					
					
					
					System.out.println(anexo.DataActual() + " Adicionou ficheiro " + name_document );
					
				}
				catch(Exception e) {
					System.out.println("erro!!!: " + e);
					return "redirect:/documento/criar-documento";
				}
				return "redirect:/main-dashboard";
			}
		 
		 
		@RequestMapping(value="/apagardocumento/{id_ficheiro}")
		public String Apargardocumento(@PathVariable("id_ficheiro") Long id_ficheiro,HttpServletRequest request, Model model) throws IOException{
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					
					Iterable <Ficheiro> ficheiro = ficheiroRepository.fidById(id_ficheiro);
					Long idf = ficheiro.iterator().next().getPasta_mae();
					Long idsf = ficheiro.iterator().next().getSub_pasta();
					
					Iterable <Pastainside> kr=pastainsideRepository.fidById(idsf);
					String submain = kr.iterator().next().getNome_pastainside();
					
					
					Iterable <Pasta> pr=pastaRepository.fidById(idf);
					String main = pr.iterator().next().getNome_pasta();
					
					model.addAttribute("Ficheiro",ficheiro);
					
					
					
					String realPathtoUploads =  request.getServletContext().getRealPath("upload/documentos/"+main+"/"+submain+"/");
					//System.out.println(ficheiroRepository.fidById(id_ficheiro));
					File f= new File(realPathtoUploads+ficheiro.iterator().next().getArquivo());	
					System.out.println(realPathtoUploads+ficheiro.iterator().next().getArquivo());
					try{
						f.delete();
						System.out.println("ficheiro eliminado");
					}catch(Exception e)
					{System.out.println("errro" + e);}
				
					ficheiroRepository.deleteById(id_ficheiro);
					System.out.println("Ficheiro eliminado com sucesso!!!");
				}
			}
			else {
				
				return "redirect:/index";
				
			}		
			
			return "redirect:/main-dashboard";
		}
		
		
		@GetMapping("/documento/criar-multi-documento")
		public String CriarMultiDocumento (Model model, HttpSession session) {
			model.addAttribute("usuario", new Usuario());
			return "/documento/criar-multi-documento";
		}
		
		@GetMapping("/documento/editar-documento/{id_ficheiro}")
		public String EditarDocumento (@PathVariable("id_ficheiro") Long id_ficheiro, Model model, HttpServletRequest request) {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					
					/*Iterable<Ficheiro> ficha =ficheiroRepository.tipo(id_ficheiro);
					
					System.out.println(ficha);
					String f = (ficha.iterator().next().getArquivo());
					if(f.contains(".pdf")) {
						model.addAttribute("Ficheiro",ficha);
						System.out.println("Existe");
					}else {
						System.out.println(" Não Existe");
					}*/
					
					model.addAttribute("ficheiros",ficheiroRepository.fidById(id_ficheiro));
					model.addAttribute("pm",ficheiroRepository.pm(id_ficheiro));
					model.addAttribute("sp",ficheiroRepository.sp(id_ficheiro));
					
					model.addAttribute("todaspastas", pastaRepository.vertodos());
					model.addAttribute("pastainsidetodas", pastainsideRepository.vertodos());
					
					
				}
			}
			else {
				
				return "redirect:/index";
				
			}		
			
			//model.addAttribute("usuario", new Usuario());
			return "/documento/editar-documento";
		}

		
//		
//		@RequestMapping(value="/editar-documento/{id_ficheiro}", method = RequestMethod.POST)
//		public String SalvarEditado(HttpServletRequest request, Model model, @Valid Ficheiro ficheiro,@RequestParam ("id_ficheiro") Long id_ficheiro, @RequestParam ("files") MultipartFile files, RedirectAttributes attributes) throws IOException{
//			
//			
//			
//			
//			
//			if (files.isEmpty() || files.equals("Null") ) {
//				Iterable <Ficheiro> ade = ficheiroRepository.fidById(id_ficheiro);
//				model.addAttribute("Ade",ade);
//				
//				ficheiro.setArquivo(ade.iterator().next().getArquivo());
//				
//			}else {
//				ficheiro.setArquivo(anexo.singleFileUpload(request, files, "documentos"));
//			}
//			
//			ficheiroRepository.save(ficheiro);
//			
//			return "redirect:/main-dashboard";
//		}
		
		@RequestMapping(value="/editar-documento/{id_ficheiro}", method = RequestMethod.POST)
		public String SalvarEditado(HttpServletRequest request, Model model, @Valid Ficheiro ficheiro,@RequestParam ("id_ficheiro") Long id_ficheiro, @RequestParam ("files") MultipartFile files, RedirectAttributes attributes) throws IOException{
			
	
			if (files.isEmpty() || files.equals("Null") ) {
				
				Iterable <Ficheiro> ade = ficheiroRepository.fidById(id_ficheiro);
				Long idf= ade.iterator().next().getPasta_mae();
				Long idsf= ade.iterator().next().getSub_pasta();
				
				Iterable <Pasta> opr=pastaRepository.fidById(idf);
				String omain = opr.iterator().next().getNome_pasta();
				
				Iterable <Pastainside> okr=pastainsideRepository.fidById(idsf);
				String osubmain = okr.iterator().next().getNome_pastainside();
				
				
				
				
				Iterable <Pastainside> kr=pastainsideRepository.fidById(ficheiro.getSub_pasta());
				String submain = kr.iterator().next().getNome_pastainside();
				
				
				Iterable <Pasta> pr=pastaRepository.fidById(ficheiro.getPasta_mae());
				String main = pr.iterator().next().getNome_pasta();
				
				model.addAttribute("Ade",ade);
				
				ficheiro.setArquivo(ade.iterator().next().getArquivo());
				
				File arq = new File(request.getServletContext().getRealPath("upload/documentos/"+main+"/"+submain+"/"),ade.iterator().next().getArquivo());
				System.out.println(arq);
				Path orig= Paths.get(request.getServletContext().getRealPath("upload/documentos/"+omain+"/"+osubmain ),ficheiro.getArquivo());
				System.out.println("caminho origem: "+ orig );
				
				Path dest=Paths.get(request.getServletContext().getRealPath("upload/documentos/"+main+"/"+submain), ficheiro.getArquivo());
				System.out.println("caminho destino: "+ dest );
				
				if (arq.exists()) {
					Files.move(orig,dest , StandardCopyOption.REPLACE_EXISTING);
					 System.out.println(Files.move(orig,dest , StandardCopyOption.ATOMIC_MOVE));
				 }
			}else {
				ficheiro.setArquivo(anexo.singleFileUpload(request, files, "documentos"));
			}
			
			//ficheiroRepository.save(ficheiro);
			
			return "redirect:/main-dashboard";
		}
	
		
		 @RequestMapping(value = "/espec/{id_ficheiro}", method = RequestMethod.GET)
		 public @ResponseBody Iterable <Ficheiro> espec3(@PathVariable(value = "id_ficheiro", required = true) Long id_ficheiro,HttpSession session) {
			 
		System.out.println(ficheiroRepository.fidById(id_ficheiro));
		     //return  pastainsideRepository.espec();
		     return  ficheiroRepository.fidById(id_ficheiro);
		     
		 }
		 
		 @RequestMapping(value="/enviar")
			public String enviar(HttpServletRequest request, Model model, @Valid Ficheiro ficheiro,@RequestParam ("id")Long id,@RequestParam ("arquivo")String arquivo,@RequestParam ("pm")String pm,@RequestParam ("endereco") String endereco, @RequestParam ("nome_ficheiro") String nome_ficheiro, RedirectAttributes attributes) throws IOException, MessagingException{
			
			 
			 HttpSession session = request.getSession(false);
				if (session != null) {
					for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
						
						Iterable <Ficheiro> fi = ficheiroRepository.fidById(id);
						Long idf = fi.iterator().next().getPasta_mae();
						System.out.println(idf);
						
						Long idsf = fi.iterator().next().getSub_pasta();
						
						Iterable <Pastainside> kr=pastainsideRepository.fidById(idsf);
						String submain = kr.iterator().next().getNome_pastainside();
						System.out.println(idsf);
						
						Iterable <Pasta> pr=pastaRepository.fidById(idf);
						String main = pr.iterator().next().getNome_pasta();
						
						
						
			 String realPathtoUploads =  request.getServletContext().getRealPath("upload/documentos/"+main+"/"+submain+"/");
			 String from="krakencoelho@gmail.com";
			 String to=endereco;
			 String subject= "Nova Partilha de ficheiro";
			 String text = user.getNome()+ " Partilhou apartir do Dexa Docs o seguinte ficheiro " +"'"+ nome_ficheiro+ "'"+" consigo.";
			 String att = realPathtoUploads + arquivo;
			 //System.out.println(to+"__" + att+"__" + arquivo);
			 
			 
			 //em.send(from, to, subject, text);
			FileSystemResource resource = new FileSystemResource(new File(att));
			em.sendWithAttach(from, to, subject, text,arquivo,resource);
				
				//ficheiroRepository.save(ficheiro);
					}
					}
				return "redirect:/pastas/inside-folder/"+pm ;
			}
		 
		 @RequestMapping(value = "/especlog/{id_ficheiro}", method = RequestMethod.GET)
		 public @ResponseBody Iterable <ActiUsua> espec4(@PathVariable(value = "id_ficheiro", required = true) Long id_ficheiro,HttpSession session) {
			 
		System.out.println(auRepository.fidById2(id_ficheiro));
		     //return  pastainsideRepository.espec();
		     return  auRepository.fidById2(id_ficheiro);
		     
		 }
}
