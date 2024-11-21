package com.dexadocs.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dexadocs.model.Admin;
import com.dexadocs.model.Pasta;
import com.dexadocs.model.Pastainside;
import com.dexadocs.model.Usuario;
import com.dexadocs.repository.AdminRepository;
import com.dexadocs.repository.FicheiroRepository;
import com.dexadocs.repository.PastaRepository;
import com.dexadocs.repository.PastainsideRepository;
import com.dexadocs.repository.UsuarioRepository;


@Controller
@RequestMapping()
public class PastaController extends HttpServlet{


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
	
	ArquivoUploadController anexo = new ArquivoUploadController();
	
	@GetMapping("/index1")
	public String inicio2 (Model model, HttpSession session) {
		model.addAttribute("usuario", new Usuario());
		return "index1";}
	
	//PASTAS Admin
	
		@GetMapping("/pastas/criar-pasta")
		public String CriarPasta (Model model, HttpServletRequest request, @Valid Admin admin  ) {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					model.addAttribute("pasta", new Pasta());
				}
			}
			else {
				
				return "redirect:/index";
				
			}
			
			
			return "/pastas/criar-pasta";
		}

		
			
			
		
		@RequestMapping(value="/pastas/criar-pasta", method = RequestMethod.POST)
		public String Salvar(HttpServletRequest request, Model model, @Valid Pasta pasta, RedirectAttributes attributes) throws IOException{
			try {	
				//model.addAttribute(usuarioRepository.save(usuario));
				
				
				File folder = new File(request.getServletContext().getRealPath("upload/documentos"),pasta.getNome_pasta());
				folder.mkdir();
//				if (!folder.exists()) {
//		            // Cria a pasta
//		            boolean pastaCriada = folder.mkdirs();
//
//		            if (pastaCriada) {
//		                System.out.println("Pasta criada com sucesso em: " + folder.getAbsolutePath());
//		            } else {
//		                System.out.println("Não foi possível criar a pasta.");
//		            }
//		        } else {
//		            System.out.println("A pasta já existe em: " + folder.getAbsolutePath());
//		        }

				
				pastaRepository.save(pasta);
				//System.out.println(folder);

				//return "redirect:/user/new-user";
			}
			catch(Exception e) {
				System.out.println("erro!!!: " + e);
				return "redirect:/pastas/criar-pasta";
			}
			return "redirect:/main-dashboard";
		}
		
		
		
		
		
		
		@GetMapping("/pastas/editar-pasta/{id_pasta}")
		public String EditarPasta (@PathVariable("id_pasta") Long id_pasta,Model model, HttpServletRequest request) {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					model.addAttribute("pasta", pastaRepository.findById(id_pasta));
					System.out.println(pastaRepository.findById(id_pasta));
				}
			}
			else {
				
				return "redirect:/index";
				
			}
			
			return "/pastas/editar-pasta";
		}
		
		
		@RequestMapping(value="/edit-pasta/{id_pasta}", method = RequestMethod.POST)
		public String EditarP (@PathVariable("id_pasta") Long id_pasta,Model model, HttpServletRequest request, Pasta pasta) {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					Iterable<Pasta> fr = pastaRepository.fidById(id_pasta);
					
					File folder = new File(request.getServletContext().getRealPath("upload/documentos"),fr.iterator().next().getNome_pasta());
					
					if (folder.exists()) {
			            // Cria um objeto File para o novo nome
			            File novaPasta = new File(folder.getParent(), pasta.getNome_pasta());

			            // Renomeia a pasta
			            boolean renomeado = folder.renameTo(novaPasta);

			            
			        } else {
			        	folder.mkdir();
			        	File novaPasta = new File(folder.getParent(), pasta.getNome_pasta());
			            System.out.println("A pasta foi novamente criada e editada");
			            boolean renomeado = folder.renameTo(novaPasta);
			        }
			    
					
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					model.addAttribute("pasta",pastaRepository.save(pasta));
					//System.out.println(id_pasta);
				}
			}
			else {
				
				return "redirect:/index";
				
			}
			
			return "/pastas/editar-pasta";
		}
		
		
		@RequestMapping(value="/apagarpasta/{id_pasta}")
		public String ApargarP(@PathVariable("id_pasta") Long id_pasta,HttpServletRequest request, Model model, @Valid Pasta pasta ) throws IOException{
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					
					
					Iterable <Pasta> pr=pastaRepository.fidById(id_pasta);
					
					File folder = new File(request.getServletContext().getRealPath("upload/documentos"),pr.iterator().next().getNome_pasta());
					//System.out.println(folder);

					
					folder.delete();
					pastaRepository.deleteById(id_pasta);
					//System.out.println("Pasta eliminado com sucesso!!!");
				}
			}
			else {
				
				return "redirect:/index";
				
			}		
			
			return "redirect:/main-dashboard";
		}
		
		@GetMapping("/pastas/criar-sub-pasta")
		public String CriarSubPasta (Model model, HttpServletRequest request, @Valid Admin admin, @Valid Pastainside pastainside) {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					model.addAttribute("pastainside", new Pastainside());
					model.addAttribute("todaspastas", pastaRepository.vertodos());
				}
			}
			else {
				
				return "redirect:/index";
				
			}
			
			
			return "/pastas/criar-sub-pasta";
		}
			
			
		
		@RequestMapping(value="/pastas/criar-sub-pasta1", method = RequestMethod.POST)
		public String Salvarinside(HttpServletRequest request, Model model, @Valid Pastainside pastainside, RedirectAttributes attributes) throws IOException{
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
				
					Iterable<Pasta> fr = pastaRepository.fidById(pastainside.getPasta_mae());
					
					String main = fr.iterator().next().getNome_pasta();
					
					File subfolder = new File(request.getServletContext().getRealPath("upload/documentos/"+main),pastainside.getNome_pastainside());
					subfolder.mkdir();
					
				pastainsideRepository.save(pastainside);
				System.out.println(" Pastainside adicionado com sucesso!!!");

				}
			}
			else {
				
				return "redirect:/index";
			}
			return "redirect:/pastas/criar-sub-pasta";
		}
		
		
		
		
		
		@GetMapping("/pastas/editar-sub-pasta/{id_pastainside}")
		public String EditarSubPasta (@PathVariable("id_pastainside") Long id_pastainside, Model model, HttpServletRequest request) {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					model.addAttribute("pastainside", pastainsideRepository.findById(id_pastainside));
					
					model.addAttribute("pastastodas", pastaRepository.findAll());
					
				}
							}
			else {
				
				return "redirect:/index";
				
			}
			
			return "/pastas/editar-sub-pasta";
			
		}
		
		
//		@RequestMapping(value="/edit-pastainside/{id_pastainside}", method = RequestMethod.POST)
//		public String EditarS (@PathVariable("id_pastainside") Long id_pastainside,Model model, HttpServletRequest request, Pastainside pastainside) throws IOException {
//			
//			HttpSession session = request.getSession(false);
//			if (session != null) {
//				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
//					
//					
//					model.addAttribute("nome", user.getNome());
//					model.addAttribute("imagem", user.getImagem());
//					
//					
//					Iterable <Pasta> pr=pastaRepository.fidById(pastainside.getPasta_mae());
//					String main = pr.iterator().next().getNome_pasta();
//					Long idm = pr.iterator().next().getId_pasta();
//					
//					Iterable <Pastainside> kr=pastainsideRepository.fidById(pastainside.getId_pastainside());
//					String submain = kr.iterator().next().getNome_pastainside();
//					Long idsm = kr.iterator().next().getPasta_mae();
//					
//					Iterable <Pasta> pre=pastaRepository.fidById(kr.iterator().next().getPasta_mae());
//					String lomain = pre.iterator().next().getNome_pasta();
//					
//					File subfolder = new File(request.getServletContext().getRealPath("upload/documentos/"+lomain),submain);
//					
//					System.out.println(subfolder);
//					
//					Path Destino=Paths.get(request.getServletContext().getRealPath("upload/documentos/"+main+"/"+ submain));
//					 
//					 Path Origem= Paths.get(request.getServletContext().getRealPath("upload/documentos/"+lomain));
//					if (subfolder.exists()) {
//					    File novaPasta = new File(subfolder.getParent(), pastainside.getNome_pastainside());
//					    
//					    if (idm != idsm) {
//					        if (subfolder.list() == null || subfolder.list().length == 0) {
//					            Files.move(Origem, Destino, StandardCopyOption.REPLACE_EXISTING);
//					            boolean renomeado = subfolder.renameTo(novaPasta);
//					            System.out.println("altera");
//					        } else {
//					            // Aqui você pode escolher a ação apropriada, como renomear a pasta existente.
//					        }
//					    } else {
//					        boolean renomeado = subfolder.renameTo(novaPasta);
//					        System.out.println("não altera");
//					    }
//					} else {
//					    System.out.println("A pasta antiga não existe.");
//					}
//					
//		
//					model.addAttribute("pastainside",pastainsideRepository.save(pastainside));
//				}
//			}
//			else {
//				
//				return "redirect:/index";
//				
//			}
//			
//			return "redirect:/pastas/inside-sub-folder"+"/"+ id_pastainside;
//			
//		}
//		
		
		
		@RequestMapping(value="/edit-pastainside/{id_pastainside}", method = RequestMethod.POST)
		public String EditarS (@PathVariable("id_pastainside") Long id_pastainside,Model model, HttpServletRequest request, Pastainside pastainside) throws IOException {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					
					
					Iterable <Pasta> pr=pastaRepository.fidById(pastainside.getPasta_mae());
					String main = pr.iterator().next().getNome_pasta();
					
					
					Iterable <Pastainside> kr=pastainsideRepository.fidById(pastainside.getId_pastainside());
					String submain = kr.iterator().next().getNome_pastainside();
					Long idsm = kr.iterator().next().getPasta_mae();
					
					Iterable <Pasta> pre=pastaRepository.fidById(kr.iterator().next().getPasta_mae());
					String lomain = pre.iterator().next().getNome_pasta();
					
					File subfolder = new File(request.getServletContext().getRealPath("upload/documentos/"+lomain),submain);
					
					Path orig= Paths.get(request.getServletContext().getRealPath("upload/documentos/"+lomain ), pastainside.getNome_pastainside());
					System.out.println("caminho origem: "+ orig );
					
					Path dest=Paths.get(request.getServletContext().getRealPath("upload/documentos/"+main), pastainside.getNome_pastainside());
					System.out.println("caminho destino: "+ dest );
					 
					 
					 Long idmp= pastainside.getPasta_mae();
					 System.out.println(idmp + " / " + idsm);
					 if (subfolder.exists()) {
				            // Cria um objeto File para o novo nome
				            File existePasta = new File(subfolder.getParent(), pastainside.getNome_pastainside());

				            // Renomeia a pasta
				            boolean renomeado = subfolder.renameTo(existePasta);
				            System.out.println("A pasta foi editada");
				            if(idmp!=idsm) {
				            Files.move(orig,dest , StandardCopyOption.REPLACE_EXISTING);
				            System.out.println("A pasta foi movida");
				            }else {}
				            
				        } else {
				        	
				        	File novaPasta = new File(subfolder.getParent(), pastainside.getNome_pastainside());
				            System.out.println("A pasta foi novamente criada e editada");
				            novaPasta.mkdir();
				            boolean renomeado = subfolder.renameTo(novaPasta);
				        }

					
		
					 model.addAttribute("pastainside",pastainsideRepository.save(pastainside));
				}
			}
			else {
				
				return "redirect:/index";
				
			}
			
			return "redirect:/pastas/inside-sub-folder"+"/"+ id_pastainside;
			
		}
		
		
		
		@RequestMapping(value="/apagarsubpasta/{id_pastainside}")
		public String ApargarS(@PathVariable("id_pastainside") Long id_pastainside,HttpServletRequest request, Model model, @Valid Pastainside pastainside ) throws IOException{
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					
					Iterable <Pastainside> kr=pastainsideRepository.fidById(pastainside.getId_pastainside());
					String submain = kr.iterator().next().getNome_pastainside();
					Long idsm = kr.iterator().next().getPasta_mae();
					
					
					Iterable <Pasta> pr=pastaRepository.fidById(idsm);
					
					File subfolder = new File(request.getServletContext().getRealPath("upload/documentos/"+pr.iterator().next().getNome_pasta() +"/"),kr.iterator().next().getNome_pastainside());
					//System.out.println(folder);

					
					subfolder.delete();
					
					pastainsideRepository.deleteById(id_pastainside);
					System.out.println(" Subpasta eliminado com sucesso!!!");
				}
			}
			else {
				
				return "redirect:/index";
				
			}		
			
			return "redirect:/main-dashboard";
		}
		
		@GetMapping("/pastas/inside-folder/{id_pasta}")
		public String InsideFolder (@PathVariable("id_pasta") Long id_pasta, Model model, HttpServletRequest request, Pasta pasta) {
			
			
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					model.addAttribute("id_vem_do_controller", ficheiroRepository.con(id_pasta));
					
				}
							}
			else {
				
				return "redirect:/index";
				
			}
			model.addAttribute("ddd", pastaRepository.caminho(id_pasta));
			
			model.addAttribute("pastainsidetodas", pastaRepository.esp(id_pasta));
			//System.out.println(pastaRepository.esp(id_pasta));
			//model.addAttribute("pastainsidetodas", pastainsideRepository.vertodos());
			model.addAttribute("ficheiros", ficheiroRepository.contemP(id_pasta));
			//System.out.println(ficheiroRepository.contemP(id_pasta));
			//ficheiroRepository.buscarPorNome(nome_ficheiro);
			

			

			return "/pastas/inside-folder";
		}
		
		
		
		
		
		
		@RequestMapping(value="/pastas/inside-sub-folder/{id_pastainside}")
		public String InsideSubFolder (@PathVariable("id_pastainside") Long id_pastainside ,Model model, HttpServletRequest request, Pastainside pastainside ) {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					
				}
			}
			else {
				
				return "redirect:/index";
				
			}
			
			//model.addAttribute("dd", pastainsideRepository.fidById(id_pastainside));
			model.addAttribute("dd", pastainsideRepository.esp(id_pastainside));
			//System.out.println(pastainsideRepository.esp(id_pastainside));
			model.addAttribute("ligados", ficheiroRepository.contem(id_pastainside));
			
			
			return "/pastas/inside-sub-folder";
		}
		
		
		@GetMapping("/pastas/main-pastas-do-ano/{ano_pasta}")
		public String MainPastasDoAno (Model model,@PathVariable("ano_pasta") String ano_pasta, HttpServletRequest request) {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				for(Admin user: adminRepository.verify(session.getAttribute("email"), session.getAttribute("palavrapasse"))) {
					
					model.addAttribute("nome", user.getNome());
					model.addAttribute("imagem", user.getImagem());
					
					model.addAttribute("ano_pasta", pastaRepository.porano(ano_pasta));
				}
							}
			else {
				
				return "redirect:/index";
				
			}
			
			return "/pastas/main-pastas-do-ano";
		}
}
