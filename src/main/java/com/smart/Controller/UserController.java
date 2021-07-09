package com.smart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private UserRepository userRepository;
	
	//method for adding common data to below methods
	@ModelAttribute
	public void addCommonData(Model m,Principal principal)
	{
		String userName=principal.getName();
		User user=userRepository.getUserByUserName(userName);
		System.out.println(user);
		m.addAttribute(user);

	}
	
	//dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal)
	{
		         model.addAttribute("title","Dashboard");
				 return "normal/user_dashboard";
	}
	
	//open Add_Contacts form handler
	@GetMapping("/addcontact")
	public String addContactForm(Model model )
	{
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact",new Contact());
		return "normal/add_contact_form"; 
	}
	
	@PostMapping("/store_contact")
	public String storeContact(@ModelAttribute Contact contact,@RequestParam("profile-image")MultipartFile file,Principal principal,HttpSession session)
	{
	
		try
		{
		String name=principal.getName();
		User user=this.userRepository.getUserByUserName(name);
		//processing and uploading file
		if(file.isEmpty())
		{
			//write on console
		}
		else
		{
			//save file to folder and save its name to database
			contact.setImage(file.getOriginalFilename());
			File saveImage = new ClassPathResource("static/image").getFile();
			Message ms=new Message("","");
			String uniqueName=ms.insertString(file.getOriginalFilename(),user.getId());
			Path path = Paths.get(saveImage.getAbsolutePath()+File.separator+uniqueName);
			Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
		}
		contact.setUser(user);
		user.getContacts().add(contact);
		this.userRepository.save(user);
		session.setAttribute("message",new Message(" Contact added Successfully !!","alert-success"));
		return "normal/add_contact_form";
		}
		catch(Exception e)
		{
         e.printStackTrace();
		}
		return "normal/add_contact_form";
		
	}

}
