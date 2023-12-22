package com.example.backend.controller;



import com.example.backend.exception.UserNotFoundException;
import com.example.backend.service.UserService;
import com.example.backend.utils.FileUpload;
import com.example.common.entity.Role;
import com.example.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping("/users")
	public String listFirst(Model model){
		return listByPage(1, model);
	}
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model){
		Page<User> p = service.listByPage(pageNum);
		List<User> list = p.getContent();

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", p.getTotalPages());
		model.addAttribute("totalItems", p.getTotalElements());
		model.addAttribute("listUsers", list);
		return "users";
	}
	@GetMapping("/users/new")
	public String newUser(Model model){
		List<Role> listRole = service.listRoles();
		User user = new User();
		user.setEnabled(false);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRole);
		model.addAttribute("pageTitle","Create New User");
		return "user_form";
	}

	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		if(!multipartFile.isEmpty()){
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User saveUser = service.save(user);
			String uploadDir = "user-photos/" + saveUser.getId();
			System.out.println(uploadDir);
			FileUpload.clear(uploadDir);
			FileUpload.saveFile(uploadDir,fileName,multipartFile);
		}else {
			if(user.getPhotos().isEmpty()) user.setPhotos(null);
			User saveUser = service.save(user);
		}

		redirectAttributes.addFlashAttribute("message","User đã được thêm thành công. ");
		return "redirect:/users";
	}
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name="id")Integer id,RedirectAttributes redirectAttributes, Model model){

		try{
			List<Role> listRole = service.listRoles();
			User user = service.get(id);
			model.addAttribute("user",user);
			model.addAttribute("listRoles", listRole);
			model.addAttribute("pageTitle","Update User by ID: " + id);
			return "user_form";
		}catch (UserNotFoundException ex){
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/users";
	}
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name="id")Integer id,RedirectAttributes redirectAttributes, Model model){
		try{
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "Thông tin tài khoản đã được xoá thành công");
		}catch (UserNotFoundException ex){
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/users";
	}
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabled(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes){
		service.updateUserEnabled(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		if(status=="enabled") {
			redirectAttributes.addFlashAttribute("message", "Tài khoản "+id+" đã được kích hoạt thành công");
		}
		else if(status=="disabled"){
			redirectAttributes.addFlashAttribute("message", "Tài khoản "+id+" đã được dừng hoạt động");
		}
		return "redirect:/users";
	}
}
