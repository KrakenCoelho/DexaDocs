package com.dexadocs.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
public class UsuarioController extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	ActusuaRepository actrepo;
	
	ArquivoUploadController anexo = new ArquivoUploadController();
	
	@Autowired
	EmailService  emailService;
	
	
	
	@GetMapping("/index")
	public String inicio (Model model, HttpSession session) {
		model.addAttribute("usuario", new Usuario());
		return "index";
		
	}
	
	
	@RequestMapping("/login")
	public String loginUser(@Valid Admin admin, @Valid Usuario usuario, Model model, HttpServletRequest request) {
		
		for(Admin user: adminRepository.login(admin.getEmail(), admin.getPalavrapasse())) {
			
			//String role1 = user.getFuncao();
			//System.out.println("User função "+ role1);
			
			
				//System.out.println("Esta a funcionar para " + role1);
				return loginAdmin(admin, model, request);
			
		}
		
		for(Usuario usua: usuarioRepository.login(usuario.getEmail(), usuario.getPalavrapasse())) {
			
			//String role1 = usua.getNome();
			//System.out.println("User's Role(before): "+ role1);
			
			
				//System.out.println("IT Ran the CODE for "+ role1);
				return loginUsuario(usuario, model, request);
			
		}

		
		return "/errologin";
			
	} 
	


	private String loginAdmin(@Valid Admin admin, Model model, HttpServletRequest request) {
		
		String sessionID;
	    boolean control=false;
		for(Admin user: adminRepository.login(admin.getEmail(), admin.getPalavrapasse())) {

			
			control=true;
			
				HttpSession session = request. getSession();
				session.setAttribute("email", user.getEmail());
				session.setAttribute("palavrapasse", user.getPalavrapasse());

				sessionID = session.getId();
				System.out.println(sessionID);
				
			
			}
		
		
		if(control) 
			return "redirect:/main-dashboard";
		
		else {
			return "/errorlogin";}
		
	}
	
	
	@GetMapping("/errologin")
	public String ErroLogin (Model model, HttpSession session) {
		model.addAttribute("usuario", new Usuario());
		return "/errologin";
	}
	
	
	@GetMapping("/main-dashboard")
	public String MainDashboard (Model model, HttpServletRequest request) throws ParseException {
	
		HttpSession session = request. getSession(false);
		boolean control=false;
	if (session != null) {
		for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))){
			
		
			model.addAttribute("nome", user.getNome());
			model.addAttribute("imagem", user.getImagem());
			model.addAttribute("id", user.getId());
			
			//System.out.println(user.getId());
			model.addAttribute("pastas", pastaRepository.vertodos());
			//model.addAttribute("anopasta", pastaRepository.porano("2015"));		
			//System.out.println(pastaRepository.porano("ano_pasta"));
			
			control=true;
			}
		}
	if(control) 
		
		return "/main-dashboard";

	else {
		return "redirect:/index";
		}
		
		
			
	}
			
	
	
	@GetMapping("/definicoes-gerais")
	public String DefinicoesGerais (Model model, HttpServletRequest request) {
		
		HttpSession session = request. getSession(false);
		boolean control=false;
	if (session != null) {
		for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
			
		
			model.addAttribute("nome", user.getNome());
			model.addAttribute("id", user.getId());
			model.addAttribute("email", user.getEmail());
			model.addAttribute("palavrapasse", user.getPalavrapasse());
			model.addAttribute("imagem", user.getImagem());
			
					
			
			control=true;
			}
		}
	if(control) 
		
		return "/definicoes-gerais";

	else {
		return "redirect:/index";
		}
						
		
	}	
	
	@RequestMapping(value="/editaradmin")
	public String editarAdmin(Model model,@Valid Admin admin, @RequestParam ("files") MultipartFile files, HttpServletRequest request) {
		HttpSession session = request. getSession(false);
		
		if (session != null) {
			for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
				
			 
		if (files.isEmpty()) {
       	 	
       		 admin.setImagem(user.getImagem());
       		 System.out.println(user.getImagem());
       	 }
       	
        else {
       	 admin.setImagem(anexo.singleFileUpload(request, files, "admin"));
       	System.out.println("Não esta a ler");
        }
       	 
		
		
		//admin.setPrev_imagem(name_document);
		model.addAttribute("admin",adminRepository.save(admin));
			}}	
		return "redirect:/definicoes-gerais"; 
	}

	
	@GetMapping("/search")
	public String Search (Model model, HttpServletRequest request, @RequestParam("query") String search) {
		
		HttpSession session = request. getSession(false);
		
		if (session != null) {
			for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
				
				model.addAttribute("ficheiro", new Ficheiro());
				model.addAttribute("procura", ficheiroRepository.buscaPornome(search));
				//model.addAttribute("procura", ficheiroRepository.findByNome_ficheiroContains(search));
				System.out.println(ficheiroRepository.buscaPornome(search));
			}

			
		} else {return "redirect:/index";}
		
		
		return "/search";
	}
	
	@GetMapping("/2-step-auth")
	public String StepAuth (Model model, HttpSession session) {
		model.addAttribute("usuario", new Usuario());
		return "/2-step-auth";
	}
	
	
	///// USER
	
	@GetMapping("/user/aceitacao-page")
	public String AceitacaoPage (Model model, HttpServletRequest request) {
		
		
		return "/user/aceitacao-page";
	}
	
	

		
	
	@GetMapping("/user/new-user")
	public String NewUser (Model model,Admin admin, HttpServletRequest request ) {
		
		
		HttpSession session = request. getSession(false);
		
		if (session != null) {
			for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
				model.addAttribute("usuario", new Usuario());
				model.addAttribute("nome", user.getNome());
				model.addAttribute("imagem", user.getImagem());
				
				/*Random random = new Random();
		        
		        int number = random.nextInt(999999);
		        System.out.println(number);*/
				
		        
			}

			
		} else {return "redirect:/index";}
	
		return "/user/new-user";
	
	}
	
	
	

//	@RequestMapping(value="/user/new-user", method = RequestMethod.POST)
//	public String Salvar(HttpServletRequest request, Model model, @Valid Usuario usuario, @RequestParam ("files") MultipartFile files, RedirectAttributes attributes) throws IOException{
//		
//	
//		try {	
//			
//			StringBuilder fileNames = new StringBuilder();
//			String name_document = anexo.singleFileUpload(request, files, "imagens");
//			  System.out.println(name_document);
//			  usuario.setImagem(name_document);
//			  usuario.setPrev_image(name_document);
//			usuarioRepository.save(usuario);
//			System.out.println("Adicionado com sucesso!!!");
//			
//			
//			 
//			
//		}
//		catch(Exception e) {
//			System.out.println("erro!!!: " + e);
//			return "redirect:/user/new-user";
//		}
//		
//		String from ="krakencoelho@gmail.com";
//		String to = usuario.getEmail() ;
//		String subject="Convite de acesso ao DexaDocs.";
//		String text="Saudações "+usuario.getNome()+" !" +"\r\n"
//				+ "\r\n"
//				+ "O seu cadastro foi realizado com sucesso. Abaixo os credencias de acesso: \r\n"
//				+ "\r\n"
//				+ "\r\n"
//				
//				+ "Nome de usuário: " + usuario.getEmail() + "\r\n"
//				
//				+ "Senha: "+ usuario.getPalavrapasse() + "\r\n"
//				+ "\r\n"
//				+ "Atenciosamente, Dexa Docs.\r\n"
//				+ "\r\n"
//				+ "";
//		
//		emailService.send (from,to,subject,text);
//		
//		return "redirect:/user/utilizadores";
//	}
	
	
	
//	@PostMapping("/user/new-user")
//	public String createUser(HttpServletRequest request, Model model, @Valid Usuario usuario,
//	                         @RequestParam(value="files", required=false) MultipartFile files,
//	                         RedirectAttributes attributes) throws IOException {
//	    try {
//	        if (files != null) {
//	            String name_document = anexo.singleFileUpload(request, files, "imagens");
//	            logger.info("File uploaded successfully: {}", name_document);
//	            usuario.setImagem(name_document);
//	            usuario.setPrev_image(name_document);
//	        } else {
//	            usuario.setImagem("sem-foto.png");
//	            usuario.setPrev_image("sem-foto.png");
//	        }
//
//	        usuarioRepository.save(usuario);
//
//	        String from = "krakencoelho@gmail.com";
//	        String to = usuario.getEmail();
//	        String subject = "Convite de acesso ao DexaDocs.";
//	        String text = "Saudações " + usuario.getNome() + "!\r\n"
//	                    + "\r\n"
//	                    + "O seu cadastro foi realizado com sucesso. Abaixo os credencias de acesso:\r\n"
//	                    + "\r\n"
//	                    + "Nome de usuário: " + usuario.getEmail() + "\r\n"
//	                    + "Senha: " + usuario.getPalavrapasse() + "\r\n"
//	                    + "\r\n"
//	                    + "Atenciosamente, Dexa Docs.\r\n"
//	                    + "\r\n";
//
//	        emailService.send(from, to, subject, text);
//
//	        attributes.addFlashAttribute("message", "User created successfully.");
//	        return "redirect:/user/utilizadores";
//	    } catch (Exception e) {
//	        logger.error("Error creating user: {}", e.getMessage());
//	        attributes.addFlashAttribute("error", "Error creating user: " + e.getMessage());
//	        return "redirect:/user/new-user";
//	    }
//	}

	
	@RequestMapping(value="/user/new-user", method = RequestMethod.POST)
	public String Salvar(HttpServletRequest request, Model model, @Valid Usuario usuario, @RequestParam (value="files",required=false) MultipartFile files, RedirectAttributes attributes) throws IOException{
		
	
		try {	
			if(files!=null) {
				StringBuilder fileNames = new StringBuilder();
				String name_document = anexo.singleFileUpload(request, files, "imagens");
				  System.out.println(name_document);
				  usuario.setImagem(name_document);
				  usuario.setPrev_image(name_document);
				//usuarioRepository.save(usuario);
				System.out.println("Adicionado com sucesso!!!");
			}else {
				usuario.setImagem("sem-foto.png");
				usuario.setPrev_image("sem-foto.png");
			}
		
//			
			
			//usuarioRepository.save(usuario);
			
			String from ="krakencoelho@gmail.com";
			String to = usuario.getEmail() ;
			String subject="Convite de acesso ao DexaDocs.";
			String text="Saudações "+usuario.getNome()+" !" +"\r\n"
					+ "\r\n"
					+ "O seu cadastro foi realizado com sucesso. Abaixo os credencias de acesso: \r\n"
					+ "\r\n"
					+ "\r\n"
					
					+ "Nome de usuário: " + usuario.getEmail() + "\r\n"
					
					+ "Senha: "+ usuario.getPalavrapasse() + "\r\n"
					+ "\r\n"
					+ "Atenciosamente, Dexa Docs.\r\n"
					+ "\r\n"
					+ "";
			
			//emailService.send (from,to,subject,text);
			//return "redirect:/user/utilizadores";
			
		}
		catch(Exception e) {
			System.out.println("erro!!!: " + e);
			return "redirect:/user/new-user";
		}
		
		
		
		return "redirect:/user/utilizadores";
	}
		
	@GetMapping("/user/utilizadores")
	public String Utilizadores (Model model, HttpServletRequest request) {	
			
		HttpSession session = request. getSession(false);
			
		if (session != null) {
			for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
				model.addAttribute("nome", user.getNome());
				model.addAttribute("usuarios", usuarioRepository.vertodos());
				model.addAttribute("imagem", user.getImagem());
			}

				
			} else {return "redirect:/index";}
		
		return "/user/utilizadores";
		
		
		
	
	}
	
	
	
	
	@GetMapping("/user/user-log/{id}")
	public String UserLog (@PathVariable("id") Long id, Model model, HttpServletRequest request ) {
		
		HttpSession session = request. getSession(false);
		
		if (session != null) {
			for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
				model.addAttribute("nome", user.getNome());
				model.addAttribute("imagem", user.getImagem());
				model.addAttribute("usuario",usuarioRepository.fidById(id));
				model.addAttribute("act",actrepo.fidById(id) );
				//model.addAttribute("act",actrepo.fid(id) );
				//System.out.println(actrepo.fid(id));
			
			}

				
			} else {return "redirect:/index";}
		
		return "/user/user-log";
		
	}
	
	
	
	@GetMapping("/user/edit-user/{id}")
	public String EditUser (@PathVariable("id") Long id,Model model, HttpServletRequest request) {
		
		HttpSession session = request. getSession(false);
		
		if (session != null) {
			for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
				model.addAttribute("nome", user.getNome());
				model.addAttribute("imagemadmin", user.getImagem());
				
				model.addAttribute("usuario",usuarioRepository.fidById(id));
				//System.out.println(usuarioRepository.fidById(id));
				/*
				user.setFuncao("apagou"+ user.getNome());
				user.setEmail(user.getEmail());
				user.setImagem(user.getImagem());
				user.setPrev_imagem(user.getImagem());
				user.setPalavrapasse(user.getPalavrapasse());
				user.setId(user.getId());
				adminRepository.save(user);*/
			}

				
			} else if(session==null) {return "redirect:/index";}
		
		return "/user/edit-user";
		
	}
	
	
	
	@RequestMapping(value="/user/edit-users/{id}", method = RequestMethod.POST)
	public String actualizar(@PathVariable("id") Long id, HttpServletRequest request, @RequestParam ("files") MultipartFile files,@RequestParam(name = "prev_image") String prev_image, Model model, Usuario usuario) throws IOException{
		try {
			
			StringBuilder fileNames = new StringBuilder();
			String name_document = anexo.singleFileUploadEdit(request, files, "imagens", prev_image);
			  System.out.println(name_document);
			  usuario.setImagem(name_document);
			  usuario.setPrev_image(name_document);
			model.addAttribute("usuario", usuarioRepository.save(usuario));
			System.out.println("Actualizado com sucesso!!!");

			
		}
		catch(Exception e) {
			System.out.println("erro!!!: " + e);
			return "redirect:/user/edit-user";
		}
		return "redirect:/user/utilizadores";
	}
	
	
	/*@RequestMapping(value="/user/edit-users/{id}", method = RequestMethod.POST)
	public String actualizar(@PathVariable("id") Long id, HttpServletRequest request, @RequestParam ("files") MultipartFile files, Model model, Usuario usuario) throws IOException{
		try {
			
			StringBuilder fileNames = new StringBuilder();
			String name_document = anexo.singleFileUpload(request, files, "imagens");
			  System.out.println(name_document);
			  usuario.setImagem(name_document);
			model.addAttribute("usuario", usuarioRepository.save(usuario));
			System.out.println("Actualizado com sucesso!!!");

			
		}
		catch(Exception e) {
			System.out.println("erro!!!: " + e);
			return "redirect:/user/edit-user";
		}
		return "redirect:/user/utilizadores";
	}*/
	
	
	@RequestMapping(value="/user/edit-users/{id}/deleteapagar")
	public String eliminar(@PathVariable("id") Long id, Model model,@Valid Usuario usuario, @Valid Admin admin, HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
				
				String realPathtoUploads =  request.getServletContext().getRealPath("upload/imagens/");
				Iterable <Usuario> usada = usuarioRepository.fidById(id);
				//model.addAttribute("Conteudo",conteudo);
				File f= new File(realPathtoUploads +usada.iterator().next().getImagem());	
				f.delete();
				
				usuarioRepository.delete(usuario);
			}
		}
		else {
			
			return "redirect:/index";
			
		}
		
		
		return "redirect:/user/utilizadores"; 
	}

	
	
	
	
	
	
		

	
	private String loginUsuario(@Valid Usuario usuario, Model model, HttpServletRequest request) {
		
		String sessionID;
		boolean control=false;
		for(Usuario usua: usuarioRepository.login(usuario.getEmail(), usuario.getPalavrapasse())) {
			
			
			control=true;
			
			HttpSession session = request. getSession();
			session.setAttribute("email", usua.getEmail());
			session.setAttribute("palavarapasse", usua.getPalavrapasse());
			
			sessionID = session.getId();
			System.out.println(sessionID);
			
			
		}
		
		
		if(control) 
			return "redirect:/user/user-main-dashboard";
		
		else {
			return "/errorlogin";}
	}
	
	
	
		
		
			
	
	
	@GetMapping("/user/user-main-dashboard")
	public String MainDashboardusua (Model model, HttpServletRequest request, @Valid Usuario usuario ) throws ParseException {
	
		HttpSession session = request. getSession(false);
		boolean control=false;
		if (session != null) {
			for(Usuario usua: usuarioRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
			
				
				model.addAttribute("nome", usua.getNome());
				model.addAttribute("imagem", usua.getImagem());
				model.addAttribute("pastass", pastaRepository.vertodos());
				//System.out.println(pastaRepository.vertodos());
				//System.out.println("casaco");
				
			}
		
		
			control=true;
		}
	
		if(control) { 
	
			return "/user/user-main-dashboard";}

		else {
			return "redirect:/index";}
	
		}
	
	@GetMapping("/user/user-edit-user")
	public String useredit (Model model, HttpServletRequest request) {
		
		HttpSession session = request. getSession(false);
		boolean control=false;
	if (session != null) {
		for(Usuario usua: usuarioRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
			
		
			model.addAttribute("nome", usua.getNome());
			model.addAttribute("id", usua.getId());
			model.addAttribute("email", usua.getEmail());
			model.addAttribute("palavrapasse", usua.getPalavrapasse());
			model.addAttribute("imagem", usua.getImagem());
			
					
			
			control=true;
			}
		}
	if(control) 
		
		return "/user/user-edit-user";

	else {
		return "redirect:/index";
		}
						
		
	}	
	
	
	@RequestMapping("/logout")
	private String sair(Model model, HttpServletRequest request, @Valid Admin admin) throws ServletException, IOException {
		
		//System.exit(0);
		HttpSession session = request. getSession(true);
		
		
		
		if (session != null) {
			for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
		//session.removeAttribute("palavrapasse");
		//session.removeAttribute("email");
				session=null;
			}		
		}	
	
		return"redirect:/index";
		}
				
			
		
		
	

	
}	
	
	
	
	
	
	
	
	

